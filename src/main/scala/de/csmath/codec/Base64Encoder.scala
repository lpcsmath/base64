package de.csmath.codec;

import Codec._


class Base64Encoder extends BaseNEncoder {

    val groupSize = 3
    val pad = 64

    override val chars = Vector() ++ (((65 to 90) map (_.toByte)) ++
                                    ((97 to 122) map (_.toByte)) ++
                                    ((48 to 57) map (_.toByte)) :+
                                    '+'.toByte :+ '/'.toByte :+ '='.toByte)

    /**
     * encodeTriple: (Array[Int],Boolean,Int) => Array[Char]
     */
    override def encodeGroup(x: (Int,Seq[Int]), needPad: Boolean) = {
        val (fill,Seq(a,b,c)) = x
        val r = (a >> 2)
        val s = (((a & 3) << 4) ^ (b >> 4))
        val t = (((b & 15) << 2) ^ (c >> 6))
        val u = (c & 63)
        if (fill == 0)
            Seq(chars(r), chars(s), chars(t), chars(u))
        else fill match {
            case 2 => Seq(chars(r), chars(s)) ++ addPads(2, needPad)
            case 1 => Seq(chars(r), chars(s), chars(t)) ++ addPads(1, needPad)
            case _ => Seq()
        }
    }

    def addPads(n: Int, needPad: Boolean) = n match {
        case 1 if needPad => Seq(chars(pad))
        case 2 if needPad => Seq(chars(pad),chars(pad))
        case _ => Seq()
    }

}
