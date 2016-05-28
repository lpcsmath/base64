package de.csmath.codec;

import Codec._


class Base64Encoder extends BaseNEncoder {

    val groupSize = 3

    override val chars = Vector() ++ (((65 to 90) map (_.toByte)) ++
                                    ((97 to 122) map (_.toByte)) ++
                                    ((48 to 57) map (_.toByte)) :+
                                    '+'.toByte :+ '/'.toByte )

    /**
     * encodeTriple: (Array[Int],Boolean,Int) => Array[Char]
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
