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


    lazy val base32Enc = new Base32Encoder()
    lazy val base32UrlEnc = new Base32UrlEncoder()
    lazy val base32HexEnc = new Base32HexEncoder()
    lazy val base32HexUrlEnc = new Base32HexUrlEncoder()

    def encoder(enc: Codec.Value) = enc match {
        case BASE32          => base32Enc
        case BASE32HEX       => base32HexEnc
        case BASE32URL       => base32UrlEnc
        case BASE32HEXURL    => base32HexUrlEnc
        case BASE32NOPAD     => base32Enc
        case BASE32HEXNOPAD  => base32HexEnc
    }


}
