package de.csmath.codec

abstract class BaseNEncoder extends StreamCodec {

    protected val chars: Vector[Byte]

    protected val groupSize: Int

    /**
     * encode: (Traversable[Byte],String) => Stream[Byte]
     */
    def encode(data: Traversable[Byte], enc: Codec.Value) = {

        val needPads = Codec.needPads(enc)

        val group = filledStream(groupSize,groupStream(groupSize,intStream(data)))
        val charStream = group map ( encodeGroup(_,needPads))

        flatten(charStream) map (_.toByte)
    }

    def encodeGroup(x: (Int,Seq[Int]), needPad: Boolean): Traversable[Byte]

    def encodeToString(bytes: Traversable[Byte], enc: Codec.Value): String =
        encode(bytes, enc) map (_.toChar) mkString

    def encodeToString(text: String, enc: Codec.Value): String =
        encode(text.getBytes, enc) map (_.toChar) mkString


}
