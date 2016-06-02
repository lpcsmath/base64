package de.csmath.codec

import scala.util._


/**
 *  The class Base16Decoder provides operations to decode a Base32 encoded
 *  collection of bytes.
 */
class Base16Decoder extends BaseNDecoder {

    /**
     *  Mapping of the code alphabet to a value between 0 and M
     *  with M >= N.
     */
     override val chars = Map() ++
             (Seq() ++ (((48 to 57) map (_.toByte)) ++
             ((65 to 70) map (_.toByte)) :+
             '\n'.toByte :+ '\r'.toByte ) zip ((0 to 100) map (_.toByte)))

    /**
     *  The number of bytes which need to be decoded together.
     */
    override val groupSize = 2

    /**
     *  The number of bits of 64, respectively log 64 as type <em>Double</em>.
     */
    override val codeBitlen = 4.0

    /**
     *  The plain code number of the pad symbol.
     */
    override val pad = 16


    /**
     *  Decoded sequence of bytes.
     *  @param group A seqence of group which needs to be encoded together,
                     where the byte value is in {0,...,N-1,pad}.
     *  @return      A seqence of decoded bytes.
     */
    override def bitdec(group: Seq[Byte]) = {
        val Seq(x,y) = group
        val a = ((x << 4) ^ (y & 15)).toByte
        Seq(a)
    }

}
