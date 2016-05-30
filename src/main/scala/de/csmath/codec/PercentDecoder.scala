package de.csmath.codec

import scala.util._

trait PercentDecoder extends StreamDecoder {

    def decodeAndApply(data: Traversable[Byte], allowedBytes: Set[Byte],
                       f: (Traversable[Byte] => Try[Stream[Byte]])) =
    {
        val allowed = allowedBytes union Set('%'.toByte, '3'.toByte,'D'.toByte, 'd'.toByte)
        val percdec = if (data exists (x => x == '%'.toByte)) {
            checkedByteStream(validByte(allowed,_),data) map { s =>
                val urlString = (s map (_.toChar)).mkString
                "%3[Dd]".r.replaceAllIn(urlString, "=" ).toStream map (_.toByte)
            }
        } else Success(data)

        percdec flatMap ( f )
    }

    def validByte(valid: Set[Byte], b: Byte) = valid contains b
}
