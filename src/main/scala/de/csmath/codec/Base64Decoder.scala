package de.csmath.codec

import scala.util._


/**
 *  The class Base64Decoder provides operations to decode a Base64 encoded
 *  collection of bytes.
 */
class Base64Decoder extends BaseNDecoder {

    /**
     *  Mapping of the code alphabet to a value between 0 and M
     *  with M >= N.
     */
    override val chars = Map() ++
            (Seq() ++ (((65 to 90) map (_.toByte)) ++
            ((97 to 122) map (_.toByte)) ++
            ((48 to 57) map (_.toByte)) :+
            '+'.toByte :+ '/'.toByte :+
            '='.toByte :+ '\n'.toByte :+
            '\r'.toByte ) zip ((0 to 100) map (_.toByte)))

    /**
     *  The number of bytes which need to be decoded together.
     */
    override val groupSize = 4

    /**
     *  The number of bits of 64, respectively log 64 as type <em>Double</em>.
     */
    override val codeBitlen = 6.0

    /**
     *  The plain code number of the pad symbol.
     */
    override val pad = 64


    /**
     *  Decoded sequence of bytes.
     *  @param group A seqence of group which needs to be encoded together,
                     where the byte value is in {0,...,N-1,pad}.
     *  @return      A seqence of decoded bytes.
     */
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
