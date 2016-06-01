package de.csmath.codec;

import scala.util._
import Codec._


/**
 *  This object provides a set of operations to encode to (respectively
 *  decode from) Base32, according to RFC 4648.
 */
object Base32 extends BaseN {

    /**
     *  Encodes a traversable collection of type <em>Byte</em> to a Base32
     *  encoded stream of type <em>Byte</em>.
     *  @param data The traversable collection, which shall be encoded.
     *  @param enc  The type of codec to use. (e.g. BASE32, BASE32URL, etc.)
     *  @return     A base32 encoded <em>Stream</em>.
     */
    override def encode(data: Traversable[Byte], enc: Codec.Value = BASE32): Stream[Byte] =
        encoder(enc).encode(data,enc)


    /**
     *  Encodes a traversable collection of type <em>Byte</em> to a
     *  <em>Base32</em> encoded <em>String</em>.
     *  @param data The traversable collection, which shall be encoded.
     *  @param enc  The type of codec to use. (e.g. BASE32, BASE32URL, etc.)
     *  @return     A base32 encoded <em>String</em>.
     */
    override def encodeToString(data: Traversable[Byte], enc: Codec.Value = BASE32): String =
        encoder(enc).encodeToString(data, enc)


    /**
     *  Decodes a <em>Base32</em> encoded traversable collection of type
     *  <em>Byte</em> to a plain stream of type <em>Byte</em> if the given
     *  collection is valid.
     *  @param data The traversable collection, which shall be decoded.
     *  @param enc  The type of codec to use. (e.g. BAS32, BAS32URL, etc.)
     *  @return     <em>Success(stream)</em> or <em>Failure(e)</em> where
     *              <em>stream</em> contains the decoded bytes and <em>e</em>
     *              is a <em>IllegalArgumentException</em> if the given
     *              collection is not valid.
     */
    override def decode(data: Traversable[Byte], enc: Codec.Value = BASE32): Try[Stream[Byte]] =
        decoder(enc).decode(data,enc)


    /**
     *  Decodes a <em>Base32</em> encoded traversable collection of type
     *  <em>Byte</em> to a plain <em>String</em> if the given
     *  collection is valid.
     *  @param data The traversable collection, which shall be decoded.
     *  @param enc  The type of codec to use. (e.g. BASE32, BASE32URL, etc.)
     *  @return     <em>Success(str)</em> or <em>Failure(e)</em> where
     *              <em>str</em> contains the decoded string and <em>e</em>
     *              is a <em>IllegalArgumentException</em> if the given
     *              collection is not valid.
     */
    override def decodeToString(data: Traversable[Byte], enc: Codec.Value = BASE32): Try[String] =
        decoder(enc).decodeToString(data,enc)


    /**
     *  An instance of a <em>Base32Encoder</em>.
     */
    lazy val base32Enc = new Base32Encoder()

    /**
     *  An instance of a <em>Base32UrlEncoder</em>.
     */
    lazy val base32UrlEnc = new Base32UrlEncoder()

    /**
     *  An instance of a <em>Base32HexEncoder</em>.
     */
    lazy val base32HexEnc = new Base32HexEncoder()

    /**
     *  An instance of a <em>Base32HexUrlEncoder</em>.
     */
    lazy val base32HexUrlEnc = new Base32HexUrlEncoder()

    /**
     *  An instance of a <em>Base32Decoder</em>.
     */
    lazy val base32Dec = new Base32Decoder()

    /**
     *  An instance of a <em>Base32UrlDecoder</em>.
     */
    lazy val base32UrlDec = new Base32UrlDecoder()

    /**
     *  An instance of a <em>Base32UrlDecoder</em>.
     */
    lazy val base32HexDec = new Base32HexDecoder()

    /**
     *  An instance of a <em>Base32UrlDecoder</em>.
     */
    lazy val base32HexUrlDec = new Base32HexUrlDecoder()


    /**
     *  Chooses the appropriate encoder instance according to the given codec.
     *  @param enc The Codec.
     *  @return    An encoder instance.
     */
    def encoder(enc: Codec.Value) = enc match {
        case BASE32          => base32Enc
        case BASE32HEX       => base32HexEnc
        case BASE32URL       => base32UrlEnc
        case BASE32HEXURL    => base32HexUrlEnc
        case BASE32NOPAD     => base32Enc
        case BASE32HEXNOPAD  => base32HexEnc
    }


    /**
     *  Chooses the appropriate decoder instance according to the given codec.
     *  @param enc The Codec.
     *  @return    An decoder instance.
     */
    def decoder(enc: Codec.Value) = enc match {
        case BASE32          => base32Dec
        case BASE32URL       => base32UrlDec
        case BASE32HEX       => base32HexDec
        case BASE32HEXURL    => base32HexUrlDec
    }

}
