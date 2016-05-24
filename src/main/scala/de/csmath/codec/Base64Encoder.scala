package de.csmath.codec;


object Base64Encoder {

    private val fill: Byte = 0
    private val fillPad: Byte = 64

    private val chars = Array( ((65 to 90) map (_.toChar)).toArray,
                       ((97 to 122) map (_.toChar)).toArray,
                       ((48 to 57) map (_.toChar)).toArray,
                       Array('+','/','=')).flatten

    /**
     * encode: (Iterable[Byte],String) => String
     */
    def encode(data: Iterable[Byte], enc: String) = {
        val padsNeeded = (3 - data.size % 3) % 3

        val triples = data grouped 3 map ( x => l2t(x.toArray))

        val builder = StringBuilder.newBuilder

        for (t <- triples) {
            if (triples.hasNext)
                builder appendAll encodeTriple(t,false,0)
            else
                builder appendAll encodeTriple(t,true,padsNeeded)
        }
        builder.toString
    }

    /**
     * encodeTriple: ((Byte,Byte,Byte),Boolean,Int) => Array[Char]
     */
    def encodeTriple(x: (Byte,Byte,Byte), pad: Boolean, fill: Int) = {
        val (a,b,c) = x
        val ia: Int = if (a < 0) 256 + a else a
        val ib: Int = if (b < 0) 256 + b else b
        val ic: Int = if (c < 0) 256 + c else c
        val r = (ia >> 2).toByte
        val s = (((ia & 3) << 4) ^ (ib >> 4)).toByte
        val t = if (pad && fill == 2) fillPad
                   else (((ib & 15) << 2) ^ (ic >> 6)).toByte
        val u = if (pad && fill > 0) fillPad
                   else (ic & 63).toByte
        Array(chars(r),chars(s),chars(t),chars(u))
    }

    /**
     * l2t: (Array[Byte]) => Tuple3[Array]
     */
    private def l2t(a: Array[Byte]) = a.length match {
       case 1 => (a(0), fill, fill)
       case 2 => (a(0), a(1), fill)
       case 3 => (a(0), a(1), a(2))
    }

    /**
     * encode: (String,String) => String
     */
    def encode(s: String, enc: String): String = encode(s.getBytes, enc)

}
