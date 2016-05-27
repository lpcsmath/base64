package de.csmath.codec;

import Codec._


class Base64UrlEncoder extends Base64FileEncoder {

    override def addPads(n: Int, needPad: Boolean) = n match {
        case 1 if needPad => "%3D".getBytes
        case 2 if needPad => "%3D%3D".getBytes
        case _ => Seq()
    }

}
