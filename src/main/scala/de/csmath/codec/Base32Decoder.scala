package de.csmath.codec

import scala.util._


/**
 *  The class Base32Decoder provides operations to decode a Base32 encoded
 *  collection of bytes.
 */
class Base32Decoder extends BaseNDecoder {

    /**
     *  Mapping of the code alphabet to a value between 0 and M
     *  with M >= N.
     */
    override val chars = Map() ++
            (Seq() ++ (((65 to 90) map (_.toByte)) ++
            ((50 to 55) map (_.toByte)) :+
            '='.toByte :+ '\n'.toByte :+
            '\r'.toByte ) zip ((0 to 100) map (_.toByte)))

    /**
     *  The number of bytes which need to be decoded together.
     */
    override val groupSize = 8

    /**
     *  The number of bits of 64, respectively log 64 as type <em>Double</em>.
     */
    override val codeBitlen = 5.0

    /**
     *  The plain code number of the pad symbol.
     */
    override val pad = 32


    /**
     *  Decoded sequence of bytes.
     *  @param group A seqence of group which needs to be encoded together,
                     where the byte value is in {0,...,N-1,pad}.
     *  @return      A seqence of decoded bytes.
     */
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
