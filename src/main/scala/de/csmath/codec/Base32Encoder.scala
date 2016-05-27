package de.csmath.codec;

import Codec._


class Base32Encoder extends BaseNEncoder {

    val groupSize = 5
    val pad = 32

    override val chars = Vector() ++ (((65 to 90) map (_.toByte)) ++
                                      ((50 to 55) map (_.toByte)) :+
                                      '='.toByte)

    /**
     * encodeTriple: (Array[Int],Boolean,Int) => Array[Char]
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
