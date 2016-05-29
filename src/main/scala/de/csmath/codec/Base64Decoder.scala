package de.csmath.codec

import scala.util._


class Base64Decoder extends BaseNDecoder {

    override val chars = Map() ++
            (Seq() ++ (((65 to 90) map (_.toByte)) ++
            ((97 to 122) map (_.toByte)) ++
            ((48 to 57) map (_.toByte)) :+
            '+'.toByte :+ '/'.toByte :+
            '='.toByte :+ '\n'.toByte :+
            '\r'.toByte ) zip ((0 to 100) map (_.toByte)))

    override val groupSize = 4

    override val codeBitlen = 6.0

    override val pad = 64

    override def bitdec(num: Int, a: Byte, b: Byte) = num match {
        case 1 => ((a << 2) ^ (b >> 4 & 3)).toByte
        case _ if b == pad => 0.toByte
        case 2 => (((a & 15) << 4) ^ ((b >> 2) & 15)).toByte
        case 3 => (((a & 3) << 6) ^ b).toByte
        case _ => 0.toByte
    }

    def takeNonPads(numChars: Int): Int = numChars - 1

}
