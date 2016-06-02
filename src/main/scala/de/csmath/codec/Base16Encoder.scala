package de.csmath.codec;

import Codec._


/**
 *  The class Base16Encoder provides operations to encode a
 *  traversable collection of bytes.
 */
class Base16Encoder extends BaseNEncoder {

    /**
     *  The size of the elements of bytes which need to be encoded together.
     */
    val groupSize = 1

    /**
     *  The code alphabet.
     */
    override val chars = Vector() ++ (((48 to 57) map (_.toByte)) ++
                                      ((65 to 70) map (_.toByte)) )


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
        val (fill,Seq(a)) = x
        val u = a >> 4
        val v = a & 15
        Seq(chars(u),chars(v))
    }

}
