package de.csmath.codec

import scala.util._


/**
 *  This abstract class provides a set of operations to encode to (respectively
 *  decode from) Base64, Base32 or Base16, according to RFC 4648.
 */
abstract class BaseN {

    /**
     *  Encodes a traversable collection of type <em>Byte</em> to a BaseN
     *  encoded stream of type <em>Byte</em>.
     *  @param data The traversable collection, which shall be encoded.
     *  @param enc  The type of codec to use. (e.g. BASE64, BASE64URL, etc.)
     *  @return     A baseN encoded <em>Stream</em>.
     */
    def encode(data: Traversable[Byte], enc: Codec.Value): Stream[Byte] =
        encoder(enc).encode(data,enc)


    /**
     *  Encodes a <em>String</em> to a BaseN encoded stream
     *  of type <em>Byte</em>.
     *  @param text The <em>String</em>, which shall be encoded.
     *  @param enc  The type of codec to use. (e.g. BASE64, BASE64URL, etc.)
     *  @return     A baseN encoded <em>Stream</em>.
     */
    def encode(text: String, enc: Codec.Value): Stream[Byte] =
        encoder(enc).encode(text.getBytes,enc)


    /**
     *  Encodes a <em>String</em> to a BaseN encoded <em>String</em>.
     *  @param text The <em>String</em>, which shall be encoded.
     *  @param enc  The type of codec to use. (e.g. BASE64, BASE64URL, etc.)
     *  @return     A baseN encoded <em>String</em>.
     */
    def encodeToString(text: String, enc: Codec.Value): String =
        encoder(enc).encodeToString(text, enc)


    /**
     *  Encodes a traversable collection of type <em>Byte</em> to a
     *  <em>BaseN</em> encoded <em>String</em>.
     *  @param data The traversable collection, which shall be encoded.
     *  @param enc  The type of codec to use. (e.g. BASE64, BASE64URL, etc.)
     *  @return     A baseN encoded <em>String</em>.
     */
    def encodeToString(data: Traversable[Byte], enc: Codec.Value): String =
        encoder(enc).encodeToString(data, enc)


    /**
     *  Decodes a <em>BaseN</em> encoded traversable collection of type
     *  <em>Byte</em> to a plain stream of type <em>Byte</em> if the given
     *  collection is valid.
     *  @param data The traversable collection, which shall be decoded.
     *  @param enc  The type of codec to use. (e.g. BASE64, BASE64URL, etc.)
     *  @return     <em>Success(stream)</em> or <em>Failure(e)</em> where
     *              <em>stream</em> contains the decoded bytes and <em>e</em>
     *              is a <em>IllegalArgumentException</em> if the given
     *              collection is not valid.
     */
    def decode(data: Traversable[Byte], enc: Codec.Value): Try[Stream[Byte]] =
        decoder(enc).decode(data,enc)


    /**
     *  Decodes a <em>BaseN</em> encoded <em>String</em> to a plain stream
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
     *  Decodes a <em>BaseN</em> encoded traversable collection of type
     *  <em>Byte</em> to a plain <em>String</em> if the given
     *  collection is valid.
     *  @param data The traversable collection, which shall be decoded.
     *  @param enc  The type of codec to use. (e.g. BASE64, BASE64URL, etc.)
     *  @return     <em>Success(str)</em> or <em>Failure(e)</em> where
     *              <em>str</em> contains the decoded string and <em>e</em>
     *              is a <em>IllegalArgumentException</em> if the given
     *              collection is not valid.
     */
    def decodeToString(data: Traversable[Byte], enc: Codec.Value): Try[String] =
        decoder(enc).decodeToString(data,enc)


    /**
     *  Decodes a <em>BaseN</em> encoded <em>String</em> to a plain
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
     *  Decodes a <em>BaseN</em> encoded traversable collection of type
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
     *  Decodes a <em>BaseN</em> encoded <em>String</em> without padding
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
     *  Decodes a <em>BaseN</em> encoded traversable collection of type
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
     *  Decodes a <em>BaseN</em> encoded <em>String</em> without padding
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
     *  Chooses the appropriate encoder instance according to the given codec.
     *  @param enc The Codec.
     *  @return    An encoder instance.
     */
    def encoder(enc: Codec.Value): BaseNEncoder


    /**
     *  Chooses the appropriate decoder instance according to the given codec.
     *  @param enc The Codec.
     *  @return    An decoder instance.
     */
    def decoder(enc: Codec.Value): BaseNDecoder

}
