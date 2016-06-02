package de.csmath.codec;

import scala.util._
import Codec._


/**
 *  This object provides a set of operations to encode to (respectively
 *  decode from) Base16, according to RFC 4648.
 */
object Base16 extends BaseN {

    /**
     *  Encodes a traversable collection of type <em>Byte</em> to a Base16
     *  encoded stream of type <em>Byte</em>.
     *  @param data The traversable collection, which shall be encoded.
     *  @param enc  The type of codec to use.
     *  @return     A base16 encoded <em>Stream</em>.
     */
    override def encode(data: Traversable[Byte], enc: Codec.Value = BASE16): Stream[Byte] =
        encoder(enc).encode(data,enc)


    /**
     *  Encodes a traversable collection of type <em>Byte</em> to a
     *  <em>Base16</em> encoded <em>String</em>.
     *  @param data The traversable collection, which shall be encoded.
     *  @param enc  The type of codec to use.
     *  @return     A base16 encoded <em>String</em>.
     */
    override def encodeToString(data: Traversable[Byte], enc: Codec.Value = BASE16): String =
        encoder(enc).encodeToString(data, enc)


    /**
     *  An instance of a <em>Base16Encoder</em>.
     */
    lazy val base16Enc = new Base16Encoder()

    /**
     *  An instance of a <em>Base16Decoder</em>.
     */
    lazy val base16Dec = new Base16Decoder()


    /**
     *  Chooses the appropriate encoder instance according to the given codec.
     *  @param enc The Codec.
     *  @return    An encoder instance.
     */
    def encoder(enc: Codec.Value) = enc match {
        case BASE16          => base16Enc
    }


    /**
     *  Chooses the appropriate decoder instance according to the given codec.
     *  @param enc The Codec.
     *  @return    An decoder instance.
     */
    def decoder(enc: Codec.Value) = base16Dec

}
