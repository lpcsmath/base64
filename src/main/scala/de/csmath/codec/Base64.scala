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

    def decode(data: Traversable[Byte], enc: Codec.Value, size: Long): Try[Stream[Byte]] =
        decoder(enc).decode(data,enc,size)

    def decodeToString(data: Traversable[Byte], enc: Codec.Value, size: Long): Try[String] =
        decoder(enc).decodeToString(data,enc,size)

    def decodeToString(data: String, enc: Codec.Value, size: Long): Try[String] =
        decoder(enc).decodeToString(data,enc,size)


    lazy val base64Enc = new Base64Encoder()
    lazy val base64UrlEnc = new Base64UrlEncoder()
    lazy val base64FileEnc = new Base64FileEncoder()
    lazy val base64Dec = new Base64Decoder()
    //lazy val base64UrlDec = new Base64UrlDecoder()
    //lazy val base64FileDec = new Base64FileDecoder()

    def encoder(enc: Codec.Value) = enc match {
        case BASE64          => base64Enc
        case BASE64NOPAD     => base64Enc
        case BASE64URL       => base64UrlEnc
        case BASE64URLNOPAD  => base64UrlEnc
        case BASE64FILE      => base64FileEnc
        case BASE64FILENOPAD => base64FileEnc
    }

    def decoder(enc: Codec.Value) = enc match {
        case BASE64          => base64Dec
        case BASE64NOPAD     => base64Dec
        //case BASE64URL       => base64UrlDec
        //case BASE64URLNOPAD  => base64UrlDec
        //case BASE64FILE      => base64FileDec
        //case BASE64FILENOPAD => base64FileDec
    }


}
