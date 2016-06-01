package de.csmath.codec

import scala.util._

/** The class BaseNEncoder is the abstract class for all base-N endocer classes.
 */
abstract class BaseNDecoder extends StreamDecoder {

    val chars: Map[Byte,Byte]

    val groupSize: Int
    val codeBitlen: Double
    val pad: Int

    def decode(data:Traversable[Byte], enc: Codec.Value, codeSize: Long): Try[Stream[Byte]] = {

        val size = Math.ceil(codeSize * 8 / codeBitlen).toLong
        val groups = checkedByteStream(allowedByte, data) map
                     (invertBytes(_)) map
                     (filteredStream(crlfByte, _)) map
                     (groupStream(groupSize,_))

        val decGroups = groups map { s =>
            countedGroups(s) map (decodeGroup(_,true,size))
        } flatMap (checkPrePadding(_))

        decGroups map (flatten(_))
    }

    def decode(data:Traversable[Byte], enc: Codec.Value): Try[Stream[Byte]] =
        decode(data,enc,0)

    def decodeToString(data:Traversable[Byte], enc: Codec.Value): Try[String] =
        decode(data,enc) map ( s => (s map (_.toChar)).mkString )

    def decodeToString(data: String, enc: Codec.Value): Try[String] =
        decodeToString(data.getBytes,enc)

    def decodeToString(data:Traversable[Byte], enc: Codec.Value, size: Long): Try[String] =
        decode(data,enc,size) map ( s => (s map (_.toChar)).mkString )

    def decodeToString(data: String, enc: Codec.Value, size: Long): Try[String] =
        decodeToString(data.getBytes,enc,size)

    protected def bitdec(group: Seq[Byte]): Seq[Byte]

    protected def countedGroups(s: Stream[Seq[Byte]]): Stream[(Int,Seq[Byte])] =
         Stream.from(0,groupSize) zip s

    protected def takeNonPads(group: Seq[Byte], numChars: Int): Seq[Byte] =
        group take (numChars * codeBitlen / 8).toInt

    protected def decodeGroup(data: (Int,Seq[Byte]), inclPads: Boolean, size: Long): (Boolean,Try[Seq[Byte]]) = {
        val (num,x) = data
        val len = x.length
        if (!checkLength(num,len, size))
            return (false,Failure(new IllegalArgumentException("Length Error")))
        val (numNonPads,flag,intOk) = checkIntPadding(len,x)
        if (!intOk)
            return (false,Failure(new IllegalArgumentException("Padding Error")))
        val last = flag || (size > 0) && (num.toLong * groupSize + len == size)

        val res = bitdec(x.padTo(groupSize, pad.toByte))
        val numBytes = if (numNonPads < len) numNonPads else len
        (last,Success(takeNonPads(res,numBytes)))
    }

    protected def checkLength(num: Int, len: Int, size: Long) =
        (len > 1) &&
        (size != 0 || len == groupSize) &&
        (size == 0 || len == groupSize || (size  == num + len)) &&
        (size == 0 || num < size - groupSize || (size  == num + len))

    /** Checks the internal Padding, e.g. "A3=3" is not allowed.
     */
    protected def checkIntPadding(len: Int, x: Seq[Byte]): (Int,Boolean,Boolean) = {
        val last = x exists { y => y == pad }
        var idx = groupSize
        val ok = if (last) {
            idx = x indexOf pad
            val padRest = x drop idx
            (len == groupSize && (padRest forall {y => y == pad}))
        }
        else true

        (idx,last,ok)
    }

    protected def allowedByte(b: Byte) = chars.keySet contains b

    protected def crlfByte(b: Byte) = b <= pad

    protected def invertBytes(s: Stream[Byte]): Stream[Byte] =
        if (s.isEmpty) Stream.empty
        else chars get s.head match {
            case Some(x) => x #:: invertBytes(s.tail)
            case _ => Stream.empty
        }

    protected def checkPrePadding(s: Stream[(Boolean,Try[Seq[Byte]])]): Try[Stream[Seq[Byte]]] =
        if (s.isEmpty) Success(Stream.empty)
        else s.head match {
            case (false,Success(x)) =>
                checkPrePadding(s.tail) map ( xs => x #:: xs )
            case (true,Success(x)) if s.tail.isEmpty =>
                Success( x #:: Stream.empty )
            case (_,Failure(x)) =>
                Failure(x)
            case x =>
                Failure(new IllegalArgumentException("Data contains illegal bytes."))
        }

}
