package de.csmath.codec;

import Codec._


object Base64Encoder {

    private val fill = 0
    private val pad = 64

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

        val needPads = enc == BASE64 || enc == BASE64URL
        val chars: Vector[Char] = if (enc == BASE64URL || enc == BASE64URLNOPAD)
                                     urlChars
                                     else canChars

        val triples = data grouped 3 map (_.toArray) map (b2i)

        val builder = StringBuilder.newBuilder

        for (t <- triples) {
            builder appendAll encodeTriple(t,needPads,chars)
        }
        
        if (enc == BASE64URL) percEncode(builder)

        builder.toString
    }

    private def percEncode(b: StringBuilder) {
        val len = b.length
        val pads = if ( len > 1 && (b charAt (len-2)) == '=') 2
                   else if ( len > 0 && (b charAt (len-1)) == '=') 1
                   else 0
        b replace (len - pads, len, "%3D"*pads)
    }

    /**
     * encodeTriple: (Array[Int],Boolean,Int) => Array[Char]
     */
    def encodeTriple(x: Array[Int], needPad: Boolean, chars:Vector[Char]) = {
        require(x.length == 4)
        val Array(fill,a,b,c) = x
        val r = (a >> 2)
        val s = (((a & 3) << 4) ^ (b >> 4))
        val t = if (fill == 2) pad
                   else (((b & 15) << 2) ^ (c >> 6))
        val u = if (fill > 0) pad
                   else (c & 63)
        val res = Array(chars(r),chars(s),chars(t),chars(u))
        if (fill == 0 || needPad)
            res
            else res take (4-fill)
    }

    /**
     * b2i: (Array[Byte]) => Array[Int]
     */
    private val b2i = (a: Array[Byte]) => {
       var ai = for (i <- a) yield if (i < 0) 256 + i else i.toInt
       while (ai.length < 3) ai = ai :+ fill
       (3-a.length) +: ai
    }

    /**
     * encode: (String,String) => String
     */
    def encode(s: String, enc: Codec.Value): String = encode(s.getBytes, enc)

}
