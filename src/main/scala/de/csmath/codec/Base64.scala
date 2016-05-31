package de.csmath.codec;

import Codec._

import scala.util._

/**
 *  This object provides a set of operations to encode to (respectively
 *  decode from) Base64, according to RFC 4648.
 */
object Base64 {

    /**
     *  Encodes a traversable collection of type <em>Byte</em> to a Base64 encoded stream
     *  of type <em>Byte</em>.
     *  @param data The traversable collection, which shall be encoded.
     *  @param enc  The type of codec to use. (e.g. BASE64, BASE64URL, etc.)
     *  @return     A base64 encoded <em>Stream</em>.
     */
    def encode(data: Traversable[Byte], enc: Codec.Value = BASE64): Stream[Byte] =
        encoder(enc).encode(data,enc)


    /**
     *  Encodes a <em>String</em> to a Base64 encoded stream
     *  of type <em>Byte</em>.
     *  @param text The <em>String</em>, which shall be encoded.
     *  @param enc  The type of codec to use. (e.g. BASE64, BASE64URL, etc.)
     *  @return     A base64 encoded <em>Stream</em>.
     */
    def encode(text: String, enc: Codec.Value): Stream[Byte] =
        encoder(enc).encode(text.getBytes,enc)


    /**
     *  Encodes a <em>String</em> to a Base64 encoded <em>String</em>.
     *  @param text The <em>String</em>, which shall be encoded.
     *  @param enc  The type of codec to use. (e.g. BASE64, BASE64URL, etc.)
     *  @return     A base64 encoded <em>String</em>.
     */
    def encodeToString(text: String, enc: Codec.Value): String =
        encoder(enc).encodeToString(text, enc)


    /**
     *  Encodes a traversable collection of type <em>Byte</em> to a
     *  <em>Base64</em> encoded <em>String</em>.
     *  @param data The traversable collection, which shall be encoded.
     *  @param enc  The type of codec to use. (e.g. BASE64, BASE64URL, etc.)
     *  @return     A base64 encoded <em>String</em>.
     */
    def encodeToString(data: Traversable[Byte], enc: Codec.Value = BASE64): String =
        encoder(enc).encodeToString(data, enc)


    /**
     *  Decodes a <em>Base64</em> encoded traversable collection of type
     *  <em>Byte</em> to a plain stream of type <em>Byte</em> if the given
     *  collection is valid.
     *  @param data The traversable collection, which shall be decoded.
     *  @param enc  The type of codec to use. (e.g. BASE64, BASE64URL, etc.)
     *  @return     <em>Success(stream)</em> or <em>Failure(e)</em> where
     *              <em>stream</em> contains the decoded bytes and <em>e</em>
     *              is a <em>IllegalArgumentException</em> if the given
     *              collection is not valid.
     */
    def decode(data: Traversable[Byte], enc: Codec.Value = BASE64): Try[Stream[Byte]] =
        decoder(enc).decode(data,enc)


    /**
     *  Decodes a <em>Base64</em> encoded <em>String</em> to a plain stream
     *  of type <em>Byte</em> if the given collection is valid.
     *  @param text The <em>String</em>, which shall be decoded.
     *  @param enc  The type of codec to use. (e.g. BASE64, BASE64URL, etc.)
     *  @return     <em>Success(stream)</em> or <em>Failure(e)</em> where
     *              <em>stream</em> contains the decoded bytes and <em>e</em>
     *              is a <em>IllegalArgumentException</em> if the given
     *              collection is not valid.
     */
    def decode(text: String, enc: Codec.Value): Try[Stream[Byte]] =
        decoder(enc).decode(text.getBytes,enc)


    /**
     *  Decodes a <em>Base64</em> encoded traversable collection of type
     *  <em>Byte</em> to a plain <em>String</em> if the given
     *  collection is valid.
     *  @param data The traversable collection, which shall be decoded.
     *  @param enc  The type of codec to use. (e.g. BASE64, BASE64URL, etc.)
     *  @return     <em>Success(str)</em> or <em>Failure(e)</em> where
     *              <em>str</em> contains the decoded string and <em>e</em>
     *              is a <em>IllegalArgumentException</em> if the given
     *              collection is not valid.
     */
    def decodeToString(data: Traversable[Byte], enc: Codec.Value = BASE64): Try[String] =
        decoder(enc).decodeToString(data,enc)


    /**
     *  Decodes a <em>Base64</em> encoded <em>String</em> to a plain
     *  <em>String</em> if the given collection is valid.
     *  @param text The <em>String</em>, which shall be decoded.
     *  @param enc  The type of codec to use. (e.g. BASE64, BASE64URL, etc.)
     *  @return     <em>Success(str)</em> or <em>Failure(e)</em> where
     *              <em>str</em> contains the decoded string and <em>e</em>
     *              is a <em>IllegalArgumentException</em> if the given
     *              collection is not valid.
     */
    def decodeToString(text: String, enc: Codec.Value): Try[String] =
        decoder(enc).decodeToString(text,enc)


    /**
     *  Decodes a <em>Base64</em> encoded traversable collection of type
     *  <em>Byte</em> without padding to a plain stream of type <em>Byte</em>
     *  if the given collection is valid.
     *  @param data The traversable collection, which shall be decoded.
     *  @param enc  The type of codec to use. (e.g. BASE64, BASE64URL, etc.)
     *  @param size The number of bytes to decode.
     *  @return     <em>Success(stream)</em> or <em>Failure(e)</em> where
     *              <em>stream</em> contains the decoded bytes and <em>e</em>
     *              is a <em>IllegalArgumentException</em> if the given
     *              collection is not valid.
     */
    def decode(data: Traversable[Byte], enc: Codec.Value, size: Long): Try[Stream[Byte]] =
        decoder(enc).decode(data,enc,size)


    /**
     *  Decodes a <em>Base64</em> encoded <em>String</em> without padding
     *  to a plain stream of type <em>Byte</em> if the given collection is valid.
     *  @param text The <em>String</em>, which shall be decoded.
     *  @param enc  The type of codec to use. (e.g. BASE64, BASE64URL, etc.)
     *  @param size The number of bytes to decode.
     *  @return     <em>Success(stream)</em> or <em>Failure(e)</em> where
     *              <em>stream</em> contains the decoded bytes and <em>e</em>
     *              is a <em>IllegalArgumentException</em> if the given
     *              collection is not valid.
     */
    def decode(text: String, enc: Codec.Value, size: Long): Try[Stream[Byte]] =
        decoder(enc).decode(text.getBytes,enc,size)


    /**
     *  Decodes a <em>Base64</em> encoded traversable collection of type
     *  <em>Byte</em> without padding to a plain <em>String</em> if the given
     *  collection is valid.
     *  @param data The traversable collection, which shall be decoded.
     *  @param enc  The type of codec to use. (e.g. BASE64, BASE64URL, etc.)
     *  @param size The number of bytes to decode.
     *  @return     <em>Success(str)</em> or <em>Failure(e)</em> where
     *              <em>str</em> contains the decoded string and <em>e</em>
     *              is a <em>IllegalArgumentException</em> if the given
     *              collection is not valid.
     */
    def decodeToString(data: Traversable[Byte], enc: Codec.Value, size: Long): Try[String] =
        decoder(enc).decodeToString(data,enc,size)


    /**
     *  Decodes a <em>Base64</em> encoded <em>String</em> without padding
     *  to a plain <em>String</em> if the given collection is valid.
     *  @param text The <em>String</em>, which shall be decoded.
     *  @param enc  The type of codec to use. (e.g. BASE64, BASE64URL, etc.)
     *  @param size The number of bytes to decode.
     *  @return     <em>Success(str)</em> or <em>Failure(e)</em> where
     *              <em>str</em> contains the decoded string and <em>e</em>
     *              is a <em>IllegalArgumentException</em> if the given
     *              collection is not valid.
     */
    def decodeToString(text: String, enc: Codec.Value, size: Long): Try[String] =
        decoder(enc).decodeToString(text,enc,size)


    /**
     *  An instance of a <em>Base64Encoder</em>.
     */
    lazy val base64Enc = new Base64Encoder()

    /**
     *  An instance of a <em>Base64UrlEncoder</em>.
     */
    lazy val base64UrlEnc = new Base64UrlEncoder()

    /**
     *  An instance of a <em>Base6File4Encoder</em>.
     */
    lazy val base64FileEnc = new Base64FileEncoder()

    /**
     *  An instance of a <em>Base64Decoder</em>.
     */
    lazy val base64Dec = new Base64Decoder()

    /**
     *  An instance of a <em>Base64UrlDecoder</em>.
     */
    lazy val base64UrlDec = new Base64UrlDecoder()

    /**
     *  An instance of a <em>Base64FileDecoder</em>.
     */
    lazy val base64FileDec = new Base64FileDecoder()


    /**
     *  Chooses the appropriate encoder instance according to the given codec.
     *  @param enc The Codec.
     *  @return    An encoder instance.
     */
    def encoder(enc: Codec.Value) = enc match {
        case BASE64          => base64Enc
        case BASE64NOPAD     => base64Enc
        case BASE64URL       => base64UrlEnc
        case BASE64URLNOPAD  => base64UrlEnc
        case BASE64FILE      => base64FileEnc
        case BASE64FILENOPAD => base64FileEnc
    }


    /**
     *  Chooses the appropriate decoder instance according to the given codec.
     *  @param enc The Codec.
     *  @return    An decoder instance.
     */
    def decoder(enc: Codec.Value) = enc match {
        case BASE64          => base64Dec
        case BASE64NOPAD     => base64Dec
        case BASE64URL       => base64UrlDec
        case BASE64URLNOPAD  => base64UrlDec
        case BASE64FILE      => base64FileDec
        case BASE64FILENOPAD => base64FileDec
    }


}
