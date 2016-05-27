package de.csmath.codec

object Codec extends Enumeration {
    val BASE64, BASE64NOPAD, BASE64URL, BASE64URLNOPAD,
        BASE64FILE, BASE64FILENOPAD = Value

    def needPads(v: Codec.Value) = v == BASE64 ||
                                   v == BASE64URL ||
                                   v == BASE64FILE
}
