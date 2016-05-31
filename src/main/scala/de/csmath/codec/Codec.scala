package de.csmath.codec


/**
 *  Enumeration of all supported <em>BaseN</em> codecs.
 */
object Codec extends Enumeration {

    val BASE64, BASE64NOPAD, BASE64URL, BASE64URLNOPAD,
        BASE64FILE, BASE64FILENOPAD,
        BASE32, BASE32NOPAD, BASE32URL, BASE32URLNOPAD,
        BASE32HEX, BASE32HEXNOPAD, BASE32HEXURL,
        BASE16 = Value


    /**
     *  Determines, if the given codec needs pad symbols to pad a collection
     *  of bytes to a specific size.
     *  @param code The codec.
     *  @return     <em>true</em> iff the codec needs to be padded.
     */
    def needPads(code: Codec.Value) =
        code == BASE64 ||
        code == BASE64URL ||
        code == BASE64FILE ||
        code == BASE32 ||
        code == BASE32URL ||
        code == BASE32HEX ||
        code == BASE32HEXURL

}
