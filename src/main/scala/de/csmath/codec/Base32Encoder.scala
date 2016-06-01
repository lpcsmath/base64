package de.csmath.codec;

import Codec._


/**
 *  The class Base32Encoder provides operations to encode a
 *  traversable collection of bytes.
 */
class Base32Encoder extends BaseNEncoder {

    /**
     *  The size of the elements of bytes which need to be encoded together.
     */
     val groupSize = 5

    /**
     *  The code alphabet.
     */
    override val chars = Vector() ++ (((65 to 90) map (_.toByte)) ++
                                      ((50 to 55) map (_.toByte)) )


    /**
     *  Encodes a group of Integers. The number of elements of the groupis
     *  determined by the value of the field groupSize.
     *  @param x       The pair (fill,seq) with fill being the number of zero-elements
     *                 added to the group to get enough elements for encoding and seq
     *                 being the group of Integers to be encoded.
     *  @param needPad true if the filled zero-elements need to be encoded to
     *                 pad-elements and false if they can be omitted.
     *  @return        A sequence of endoded Bytes
     */
    override def encodeGroup(x: (Int,Seq[Int]), needPad: Boolean) = {
        val (fill,Seq(a,b,c,d,e)) = x
        val idx = new Array[Int](8)
        idx(0) = (a >> 3)
        idx(1) = ((a & 7) << 2) ^ (b >> 6)
        idx(2) = (b >> 1) & 31
        idx(3) = ((b & 1) << 4) ^ (c >> 4)
        idx(4) = ((c & 15) << 1) ^ (d >> 7)
        idx(5) = (d >> 2) & 31
        idx(6) = ((d & 3) << 3) ^ (e >> 5)
        idx(7) = e & 31
        val padFill = fill * 8 / 5
        Seq.tabulate(8 - padFill)( x => chars(idx(x)) ) ++ addPads(padFill, needPad)
    }

}
