package de.csmath.codec;

import Codec._


object Base64Encoder {

    private val fill: Byte = 0
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

        val triples = data grouped 3 map (_.toArray) map (a2t)

        val builder = StringBuilder.newBuilder

        for (t <- triples) {
            if (triples.hasNext)
                builder appendAll encodeTriple(t,needPads,0,chars)
            else
                builder appendAll encodeTriple(t,needPads,padsNeeded,chars)
        }
        if (enc == BASE64URL) padsNeeded match {
            case 2 => builder replace (builder.length - 2, builder.length, "%3B%3B")
            case 1 => builder replace (builder.length - 1, builder.length, "%3B")
            case _ =>  
        }
        builder.toString
    }

    /**
     * encodeTriple: ((Byte,Byte,Byte),Boolean,Int) => Array[Char]
     */
    def encodeTriple(x: (Byte,Byte,Byte), needPad: Boolean, fill: Int, chars:Vector[Char]) = {
        val (a,b,c) = x
        val ia: Int = if (a < 0) 256 + a else a
        val ib: Int = if (b < 0) 256 + b else b
        val ic: Int = if (c < 0) 256 + c else c
        val r = (ia >> 2).toByte
        val s = (((ia & 3) << 4) ^ (ib >> 4)).toByte
        val t = if (fill == 2) pad
                   else (((ib & 15) << 2) ^ (ic >> 6)).toByte
        val u = if (fill > 0) pad
                   else (ic & 63).toByte
        if (needPad)
            Array(chars(r),chars(s),chars(t),chars(u))
        else fill match {
            case 2 => Array(chars(r),chars(s))
            case 1 => Array(chars(r),chars(s),chars(t))
            case 0 => Array(chars(r),chars(s),chars(t),chars(u))
        }
    }

    /**
     * l2t: (Array[Byte]) => Tuple3[Array]
     */
    private val a2t = (a: Array[Byte]) => a.length match {
       case 1 => (a(0), fill, fill)
       case 2 => (a(0), a(1), fill)
       case 3 => (a(0), a(1), a(2))
    }

    /**
     * encode: (String,String) => String
     */
    def encode(s: String, enc: Codec.Value): String = encode(s.getBytes, enc)

}
