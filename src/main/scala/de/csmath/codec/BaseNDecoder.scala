package de.csmath.codec

import scala.util._

/** The class BaseNEncoder is the abstract class for all base-N endocer classes.
 */
abstract class BaseNDecoder extends StreamCodec with StreamDecoder {

    val chars: Map[Byte,Byte]

    val groupSize: Int
    val codeBitlen: Double
    val pad: Int

    def decode(data:Traversable[Byte], enc: Codec.Value, codeSize: Long): Try[Stream[Byte]] = {

        val size = Math.ceil(codeSize * 8 / codeBitlen).toLong
        val s = checkedByteStream(allowedByte, data)
        val groups = s map { x =>
            invertBytes(filteredStream(crlfByte, x))
        } map (groupBStream(groupSize,_))

        val decGroups = groups map { s =>
            Stream.from(0,groupSize) zip s map (decodeGroup(_,true,size))
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

    def bitdec(num: Int, a: Byte, b: Byte): Byte

    def takeNonPads(numChars: Int): Int

    def decodeGroup(data: (Int,Seq[Byte]), inclPads: Boolean, size: Long): (Boolean,Try[Seq[Byte]]) = {
        val (num,x) = data
        val len = x.length
        if (!checkLength(num,len, size))
            return (false,Failure(new IllegalArgumentException("Length Error")))
        val (numNonPads,flag,intOk) = checkIntPadding(len,x)
        if (!intOk)
            return (false,Failure(new IllegalArgumentException("Padding Error")))
        val last = flag || (size > 0) && (num.toLong * groupSize + len == size)

        val res = ((x zip x.tail) zip (1 to 8)) map { x =>
            val ((a,b),i) = x
            bitdec(i,a,b)
        }
        (last,Success(res take takeNonPads(numNonPads)))
    }

    def checkLength(num: Int, len: Int, size: Long) =
        (len > 1) &&
        (size != 0 || len == 4) &&
        (size == 0 || len == 4 || (size  == num + len))

    /** Checks the internal Padding, e.g. "A3=3" is not allowed.
     */
    def checkIntPadding(len: Int, x: Seq[Byte]): (Int,Boolean,Boolean) = {
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

    protected def crlfByte(b: Byte) = !( b == 10 || b == 13)

    def invertBytes(s: Stream[Byte]): Stream[Byte] =
        if (s.isEmpty) Stream.empty
        else chars get s.head match {
            case Some(x) => x #:: invertBytes(s.tail)
            case _ => Stream.empty
        }

    def checkPrePadding(s: Stream[(Boolean,Try[Seq[Byte]])]): Try[Stream[Seq[Byte]]] =
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
