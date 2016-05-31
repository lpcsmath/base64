package de.csmath.codec;

import Codec._
import scala.util._


class Base32UrlDecoder extends Base32Decoder with PercentDecoder {


    override def decode(data:Traversable[Byte], enc: Codec.Value, codeSize: Long): Try[Stream[Byte]] =
        percentDecode(data, chars.keySet) flatMap ( super.decode(_,enc,codeSize) )

}
