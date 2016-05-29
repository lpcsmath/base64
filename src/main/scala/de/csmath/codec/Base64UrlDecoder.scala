package de.csmath.codec;

import Codec._
import scala.util._


class Base64UrlDecoder extends Base64Decoder {

    override val chars = Map() ++
            (Seq() ++ (((65 to 90) map (_.toByte)) ++
            ((97 to 122) map (_.toByte)) ++
            ((48 to 57) map (_.toByte)) :+
            '-'.toByte :+ '_'.toByte :+
            '='.toByte :+ '\n'.toByte :+
            '\r'.toByte :+ '%'.toByte ) zip ((0 to 100) map (_.toByte)))

    override def decode(data:Traversable[Byte], enc: Codec.Value, codeSize: Long): Try[Stream[Byte]] = {
        val percdec = if (data exists (x => x == '%'.toByte)) {
            checkedByteStream(allowedByte,data) map { s =>
                var builder = StringBuilder.newBuilder appendAll (s map (_.toChar))
                builder.replaceAllLiterally("%3D","=").toStream map (_.toByte)
            }
        } else Success(data)
        percdec flatMap ( super.decode(_,enc,codeSize) )
    }

}
