package de.csmath.codec

import scala.util._

trait PercentDecoder extends StreamDecoder {

    def percentDecode(data: Traversable[Byte], allowedBytes: Set[Byte]) = {
        val allowed = allowedBytes union Set('%'.toByte, '3'.toByte,'D'.toByte, 'd'.toByte)
        if (data exists (x => x == '%'.toByte)) {
            checkedByteStream(validByte(allowed,_),data) map { s =>
                val urlString = (s map (_.toChar)).mkString
                "%3[Dd]".r.replaceAllIn(urlString, "=" ).toStream map (_.toByte)
            }
        } else Success(data)
    }

    def validByte(valid: Set[Byte], b: Byte) = valid contains b
}
