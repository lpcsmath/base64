package de.csmath.codec

/**
 *  The class BaseNEncoder is the abstract class for all baseN endocer classes.
 */
abstract class BaseNEncoder extends StreamCodec with CanonicalPadding {

    /**
     *  The code alphabet.
     */
    protected val chars: Vector[Byte]


    /**
     *  The size of the elements of bytes which need to be encoded together.
     */
    protected val groupSize: Int


    /**
     *  A baseN encoded stream of Bytes.
     *  @param data The stream or other kind of Traversable of plain bytes.
     *  @param enc  The codec to use for encoding.
     *  @return     The baseN encoded stream of type Byte.
     */
    def encode(data: Traversable[Byte], enc: Codec.Value) = {
        val groups = filledStream(groupSize,groupStream(groupSize,intStream(data)))
        val charStream = groups map ( encodeGroup(_,Codec.needPads(enc)) )
        flatten(charStream) map (_.toByte)
    }


    /**
     *  Encodes a group of Integers. The number of elements of the groupis
     *  determined by the value of the field groupSize.
     *  @param group   The pair (fill, seq) with fill being the number of zero-elements
     *                 added to the group to get enough elements for encoding and seq
     *                 being the group of Integers to be encoded.
     *  @param needPad true if the filled zero-elements need to be encoded to
     *                 pad-elements and false if they can be omitted.
     *  @return        A sequence of endoded Bytes
     */
    def encodeGroup(group: (Int,Seq[Int]), needPad: Boolean): Traversable[Byte]


    /**
     *  A baseN encoded string.
     *  @param bytes The stream or other kind of Traversable of plain bytes.
     *  @param enc   The codec to use for encoding.
     *  @return      The baseN encoded string.
     */
    def encodeToString(bytes: Traversable[Byte], enc: Codec.Value): String =
        encode(bytes, enc) map (_.toChar) mkString


    /**
     *  A baseN encoded string.
     *  @param text The plain string to be encoded.
     *  @param enc  The codec to use for encoding.
     *  @return     The encoded string.
     */
    def encodeToString(text: String, enc: Codec.Value): String =
        encode(text.getBytes, enc) map (_.toChar) mkString


}
