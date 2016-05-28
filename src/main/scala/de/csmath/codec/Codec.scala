package de.csmath.codec

object Codec extends Enumeration {
    val BASE64, BASE64NOPAD, BASE64URL, BASE64URLNOPAD,
        BASE64FILE, BASE64FILENOPAD,
        BASE32, BASE32NOPAD, BASE32URL, BASE32URLNOPAD,
        BASE32HEX, BASE32HEXNOPAD, BASE32HEXURL,
        BASE16 = Value

    def needPads(v: Codec.Value) = v == BASE64 ||
                                   v == BASE64URL ||
                                   v == BASE64FILE ||
                                   v == BASE32 ||
                                   v == BASE32URL ||
                                   v == BASE32HEX ||
                                   v == BASE32HEXURL ||
                                   v == BASE16
}
