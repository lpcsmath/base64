package de.csmath.codec;

import Codec._


object Base64Encoder {

    private val fill = 0
    private val pad: Byte = 64

    private val canChars = Vector() ++ ((65 to 90) map (_.toChar)) ++
                                    ((97 to 122) map (_.toChar)) ++
                                    ((48 to 57) map (_.toChar)) :+
                                    '+' :+ '/' :+ '='
    private val urlChars = canChars patch (62,Array('-','_'),2)

    /**
     * encode: (Iterable[Byte],String) => String
     */
    def encode(data: Iterable[Byte], enc: Codec.Value) = {
        require(enc == BASE64 ||
                enc == BASE64NOPAD ||
                enc == BASE64URL ||
                enc == BASE64URLNOPAD)

        val padsNeeded = (3 - data.size % 3) % 3

        val needPads = enc == BASE64 || enc == BASE64URL
        val chars: Vector[Char] = if (enc == BASE64URL || enc == BASE64URLNOPAD)
                                     urlChars
                                     else canChars

        val triples = data grouped 3 map (_.toArray) map (b2i)

        val builder = StringBuilder.newBuilder

        for (t <- triples) {
            if (triples.hasNext)
                builder appendAll encodeTriple(t,needPads,0,chars)
            else
                builder appendAll encodeTriple(t,needPads,padsNeeded,chars)
        }
        if (enc == BASE64URL)
            builder replace (builder.length - padsNeeded, builder.length, "%3D"*padsNeeded)

        builder.toString
    }

    /**
     * encodeTriple: (Array[Int],Boolean,Int) => Array[Char]
     */
    def encodeTriple(x: Array[Int], needPad: Boolean, fill: Int, chars:Vector[Char]) = {
        require(x.length == 3)
        val Array(a,b,c) = x
        val r = (a >> 2).toByte
        val s = (((a & 3) << 4) ^ (b >> 4)).toByte
        val t = if (fill == 2) pad
                   else (((b & 15) << 2) ^ (c >> 6)).toByte
        val u = if (fill > 0) pad
                   else (c & 63).toByte
        val res = Array(chars(r),chars(s),chars(t),chars(u))
        if (needPad || fill == 0)
            res
            else res take (4-fill)
    }

    /**
     * b2i: (Array[Byte]) => Array[Int]
     */
    private val b2i = (a: Array[Byte]) => {
       var ai = for (i <- a) yield if (i < 0) 256 + i else i.toInt
       while (ai.length < 3) ai = ai :+ fill
       ai
    }

    /**
     * encode: (String,String) => String
     */
    def encode(s: String, enc: Codec.Value): String = encode(s.getBytes, enc)

}
