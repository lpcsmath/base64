package de.csmath.codec


/**
 *  Enumeration of all supported <em>BaseN</em> codecs.
 */
object Codec extends Enumeration {

    /**
     *  The standard Base64 encoding with padding.
     */
    val BASE64 = Value

    /**
     *  The standard Base64 encoding without padding.
     */
    val BASE64NOPAD = Value

    /**
     *  The file system and URL save Base64 encoding
     *  with padding where the symbols '/' and '+'
     *  are replaced by '_' and '-'.
     */
    val BASE64FILE = Value

    /**
     *  Like [[BASE64FILE]], but without padding.
     */
    val BASE64FILENOPAD = Value

    /**
     *  Like [[BASE64FILE]], but with percent encoding
     *  of the pad symbols.
     */
    val BASE64URL = Value

    /**
     *  Like [[BASE64FILENOPAD]].
     */
    val BASE64URLNOPAD = Value

    /**
     *  The standard Base32 encoding with padding.
     */
    val BASE32 = Value

    /**
     *  The standard Base32 encoding without padding.
     */
    val BASE32NOPAD = Value

    /**
     *  Like [[BASE32]], but with percent encoding
     *  of the pad symbols.
     */
    val BASE32URL = Value

    /**
     *  The Base32 encoding derived by extending the
     *  hexadecimal number representation with padding.
     */
    val BASE32HEX = Value

    /**
     * Like [[BASE32HEX]], but without padding.
     */
    val BASE32HEXNOPAD = Value

    /**
     *  Like [[BASE32HEX]], but with percent encoding
     *  of the pad symbols.
     */
    val BASE32HEXURL = Value

    /**
     *  The standard Base16 encoding.
     */
    val BASE16 = Value


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
