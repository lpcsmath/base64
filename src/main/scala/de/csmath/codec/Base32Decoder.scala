package de.csmath.codec

import scala.util._


class Base32Decoder extends BaseNDecoder {

    override val chars = Map() ++
            (Seq() ++ (((65 to 90) map (_.toByte)) ++
            ((50 to 55) map (_.toByte)) :+
            '='.toByte :+ '\n'.toByte :+
            '\r'.toByte ) zip ((0 to 100) map (_.toByte)))

    override val groupSize = 8

    override val codeBitlen = 5.0

    override val pad = 32

    override def bitdec(group: Seq[Byte]) = {
        val Seq(s,t,u,v,w,x,y,z) = group
        val a = ((s << 3) ^ ((t >> 2) & 7)).toByte
        val b = if (u == pad) 0.toByte
                    else (((t & 3) << 6) ^ (u << 1) ^ ((v >> 4) & 1)).toByte
        val c = if (v == pad) 0.toByte
                    else (((v & 15) << 4) ^ ((w >> 1) & 15)).toByte
        val d = if (w == pad) 0.toByte
                    else (((w & 1) << 7) ^ (x << 2) ^ ((y >> 3) & 3)).toByte
        val e = if (y == pad) 0.toByte
                    else (((y & 7) << 5) ^ z).toByte
        Seq(a,b,c,d,e)
    }

}
