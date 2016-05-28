package de.csmath.codec;

import Codec._


class Base16Encoder extends BaseNEncoder {

    val groupSize = 1

    override val chars = Vector() ++ (((48 to 57) map (_.toByte)) ++
                                      ((65 to 70) map (_.toByte)) )
    /**
     * encodeTriple: (Array[Int],Boolean,Int) => Array[Char]
     */
    override def encodeGroup(x: (Int,Seq[Int]), needPad: Boolean) = {
        val (fill,Seq(a)) = x
        val u = a >> 4
        val v = a & 15
        Seq(chars(u),chars(v))
    }

}
