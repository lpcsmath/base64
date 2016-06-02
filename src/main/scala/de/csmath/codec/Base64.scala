package de.csmath.codec;

import scala.util._
import Codec._


/**
 *  This object provides a set of operations to encode to (respectively
 *  decode from) Base64, according to RFC 4648.
 */
object Base64 extends BaseN {

    /**
     *  Encodes a traversable collection of type <em>Byte</em> to a Base64
     *  encoded stream of type <em>Byte</em>.
     *  @param data The traversable collection, which shall be encoded.
     *  @param enc  The type of codec to use. (e.g. BASE64, BASE64URL, etc.)
     *  @return     A base64 encoded <em>Stream</em>.
     */
    override def encode(data: Traversable[Byte], enc: Codec.Value = BASE64): Stream[Byte] =
        encoder(enc).encode(data,enc)


    /**
     *  Encodes a traversable collection of type <em>Byte</em> to a
     *  <em>Base64</em> encoded <em>String</em>.
     *  @param data The traversable collection, which shall be encoded.
     *  @param enc  The type of codec to use. (e.g. BASE64, BASE64URL, etc.)
     *  @return     A base64 encoded <em>String</em>.
     */
    override def encodeToString(data: Traversable[Byte], enc: Codec.Value = BASE64): String =
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
    override def decode(data: Traversable[Byte], enc: Codec.Value = BASE64): Try[Stream[Byte]] =
        decoder(enc).decode(data,enc)


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
    override def decodeToString(data: Traversable[Byte], enc: Codec.Value = BASE64): Try[String] =
        decoder(enc).decodeToString(data,enc)


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
        case BASE64URLNOPAD  => base64FileEnc
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
        case BASE64URLNOPAD  => base64FileDec
        case BASE64FILE      => base64FileDec
        case BASE64FILENOPAD => base64FileDec
    }

}
