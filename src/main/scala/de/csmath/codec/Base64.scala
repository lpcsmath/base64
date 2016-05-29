package de.csmath.codec;

import Codec._

import scala.util._


object Base64 {

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

    def decode(data: Traversable[Byte], enc: Codec.Value): Try[Stream[Byte]] =
        decoder(enc).decode(data,enc)

    def decodeToString(data: Traversable[Byte], enc: Codec.Value): Try[String] =
        decoder(enc).decodeToString(data,enc)

    def decodeToString(data: String, enc: Codec.Value): Try[String] =
        decoder(enc).decodeToString(data,enc)

    def encoder(enc: Codec.Value) = enc match {
        case BASE64          => new Base64Encoder()
        case BASE64NOPAD     => new Base64Encoder()
        case BASE64URL       => new Base64UrlEncoder()
        case BASE64URLNOPAD  => new Base64UrlEncoder()
        case BASE64FILE      => new Base64FileEncoder()
        case BASE64FILENOPAD => new Base64FileEncoder()
    }

    def decoder(enc: Codec.Value) = enc match {
        case BASE64          => new Base64Decoder()
        //case BASE64NOPAD     => new Base64Decoder()
        //case BASE64URL       => new Base64UrlDecoder()
        //case BASE64URLNOPAD  => new Base64UrlDecoder()
        //case BASE64FILE      => new Base64FileDecoder()
        //case BASE64FILENOPAD => new Base64FileDecoder()
    }


}
