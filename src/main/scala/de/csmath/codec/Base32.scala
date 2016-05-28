package de.csmath.codec;

import Codec._


object Base32 {

    /**
     * encode: (Iterable[Byte],String) => Stream[Byte]
     */
    def encode(data: Traversable[Byte], enc: Codec.Value): Stream[Byte] =
        encoder(enc).encode(data,enc)


    /**
     * encode: (String,String) => String
     */
    def encodeToString(s: String, enc: Codec.Value): String =
        encoder(enc).encodeToString(s, enc)

    /**
     * encode: (Iterable[Byte],String) => String
     */
    def encodeToString(data: Traversable[Byte], enc: Codec.Value): String =
        encoder(enc).encodeToString(data, enc)

    def encoder(enc: Codec.Value) = enc match {
        case BASE32          => new Base32Encoder()
        case BASE32HEX       => new Base32HexEncoder()
        case BASE32URL       => new Base32UrlEncoder()
        case BASE32HEXURL    => new Base32HexUrlEncoder()
        case BASE32NOPAD     => new Base32Encoder()
        case BASE32HEXNOPAD  => new Base32HexEncoder()
    }


}
