package de.csmath.codec;


object Base64 {

    /**
     * encode: (Iterable[Byte],String) => String
     */
    def encode(data: Iterable[Byte], enc: Codec.Value) = Base64Encoder.encode(data, enc)

    /**
     * encode: (String,String) => String
     */
    def encode(s: String, enc: Codec.Value): String = Base64Encoder.encode(s, enc)

}
