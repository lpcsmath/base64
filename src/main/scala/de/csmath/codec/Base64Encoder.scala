package de.csmath.codec;

import Codec._


/**
 *  The class Base64Encoder provides operations to encode a
 *  traversable collection of bytes.
 */
class Base64Encoder extends BaseNEncoder {

    /**
     *  The size of the elements of bytes which need to be encoded together.
     */
    val groupSize = 3

    /**
     *  The code alphabet.
     */
    override val chars = Vector() ++ (((65 to 90) map (_.toByte)) ++
                                    ((97 to 122) map (_.toByte)) ++
                                    ((48 to 57) map (_.toByte)) :+
                                    '+'.toByte :+ '/'.toByte )


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
        val (fill,Seq(a,b,c)) = x
        val idx = new Array[Int](4)
        idx(0) = (a >> 2)
        idx(1) = (((a & 3) << 4) ^ (b >> 4))
        idx(2) = (((b & 15) << 2) ^ (c >> 6))
        idx(3) = (c & 63)
        Seq.tabulate(4 - fill)( x => chars(idx(x)) ) ++ addPads(fill, needPad)
    }

}
