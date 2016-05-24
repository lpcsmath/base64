package de.csmath.codec;


object Base64 {

    /**
     * encode: (Iterable[Byte],String) => String
     */
    def encode(data: Iterable[Byte], enc: String) = Base64Encoder.encode(data, enc)

    /**
     * encode: (String,String) => String
     */
    def encode(s: String, enc: String): String = Base64Encoder.encode(s, enc)

}
