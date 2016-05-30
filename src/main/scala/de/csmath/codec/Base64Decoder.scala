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

    override def bitdec(group: Seq[Byte]): Seq[Byte] = {
        val Seq(w,x,y,z) = group
        val a = ((w << 2) ^ (x >> 4 & 3)).toByte
        val b = if (y == pad) 0.toByte
                    else (((x & 15) << 4) ^ ((y >> 2) & 15)).toByte
        val c = if (z == pad) 0.toByte
                    else (((y & 3) << 6) ^ z).toByte
        Seq(a,b,c)
    }

}
