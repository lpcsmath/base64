package de.csmath.codec

import scala.util._

/**
 *  The class BaseNEncoder is the abstract class for all base-N decoder classes.
 */
abstract class BaseNDecoder extends StreamDecoder {

    /**
     *  Mapping of the code alphabet to a value between 0 and M
     *  with M >= N.
     */
    val chars: Map[Byte,Byte]

    /**
     *  The number of bytes which need to be decoded together.
     */
    val groupSize: Int

    /**
     *  The number of bits of N, respectively log N as type <em>Double</em>.
     */
    val codeBitlen: Double

    /**
     *  The plain code number of the pad symbol, which should be an integer
     *  greater than N-1.
     */
    val pad: Int


    /**
     *  Stream of decoded bytes from a traversable collection.
     *  @param data      The stream or other kind of Traversable of encoded bytes.
     *  @param enc       The codec to use for decoding.
     *  @param plainSize The expected number of bytes after decoding.
     *  @return          Success(stream) where stream is the decoded byte stream,
     *                   or Failure(e) if <em>data</em> contains invalid bytes
     *                   or the size of the collection does not match the
     *                   expected number of decoded bytes <em>plainSize</em>.
     */
    def decode(data:Traversable[Byte], enc: Codec.Value, plainSize: Long): Try[Stream[Byte]] = {

        val size = Math.ceil(plainSize * 8 / codeBitlen).toLong
        val groups = checkedByteStream(allowedByte, data) map
                     (invertBytes(_)) map
                     (filteredStream(crlfByte, _)) map
                     (groupStream(groupSize,_))

        val decGroups = groups map { s =>
            countedGroups(s) map (decodeGroup(_,size))
        } flatMap (checkPrePadding(_))

        decGroups map (flatten(_))
    }


    /**
     *  Stream of decoded bytes from a traversable collection.
     *  @param data      The stream or other kind of Traversable of encoded bytes.
     *  @param enc       The codec to use for decoding.
     *  @return          Success(stream) where stream is the decoded byte stream,
     *                   or Failure(e) if <em>data</em> contains invalid bytes.
     */
    def decode(data:Traversable[Byte], enc: Codec.Value): Try[Stream[Byte]] =
        decode(data,enc,0)


    /**
     *  Decoded String from a traversable collection.
     *  @param data      The stream or other kind of Traversable of encoded bytes.
     *  @param enc       The codec to use for decoding.
     *  @return          Success(str) where str is the decoded <em>String</em>,
     *                   or Failure(e) if <em>data</em> contains invalid bytes.
     */
    def decodeToString(data:Traversable[Byte], enc: Codec.Value): Try[String] =
        decode(data,enc) map ( s => (s map (_.toChar)).mkString )


    /**
     *  Decoded String from a traversable collection.
     *  @param text      The encoded <em>String</em>.
     *  @param enc       The codec to use for decoding.
     *  @return          Success(str) where str is the decoded <em>String</em>,
     *                   or Failure(e) if <em>text</em> contains invalid bytes.
     */
    def decodeToString(text: String, enc: Codec.Value): Try[String] =
        decodeToString(text.getBytes,enc)


    /**
     *  Decoded String from a traversable collection.
     *  @param data The stream or other kind of Traversable of encoded bytes.
     *  @param enc  The codec to use for decoding.
     *  @param size The expected number of bytes after decoding.
     *  @return     Success(str) where str is the decoded <em>String</em>,
     *              or Failure(e) if <em>text</em> contains invalid bytes
     *              or the size of the <em>string</em> does not match the
     *              expected number of decoded bytes <em>size</em>.
     */
    def decodeToString(data:Traversable[Byte], enc: Codec.Value, size: Long): Try[String] =
        decode(data,enc,size) map ( s => (s map (_.toChar)).mkString )


    /**
     *  Decoded String from a traversable collection.
     *  @param text The encoded <em>String</em>.
     *  @param enc  The codec to use for decoding.
     *  @param size The expected number of bytes after decoding.
     *  @return     Success(str) where str is the decoded <em>String</em>,
     *              or Failure(e) if <em>text</em> contains invalid bytes
     *              or the size of the <em>string</em> does not match the
     *              expected number of decoded bytes <em>size</em>.
     */
    def decodeToString(text: String, enc: Codec.Value, size: Long): Try[String] =
        decodeToString(text.getBytes,enc,size)


    /**
     *  Abstract method to decode a group of bytes.
     *  @param group A seqence of group which needs to be encoded together,
                     where the byte value is in {0,...,N-1,pad}.
     *  @return      A seqence of decoded bytes.
     */
    protected def bitdec(group: Seq[Byte]): Seq[Byte]


    /**
     *  A stream of pairs (n,seq) with n as the number of already consumed
     *  bytes and seq as a byte group.
     *  @param s A stream of byte seqences.
     *  @return  A stream of pairs (n,seq) with n being the number of already
     *           consumed bytes and seq being a byte group.
     */
    protected def countedGroups(s: Stream[Seq[Byte]]): Stream[(Int,Seq[Byte])] =
         Stream.from(0,groupSize) zip s


    /**
     *  This helper function returns a prefix of a byte seqence.
     *  @param group    A byte sequence.
     *  @param numChars The number of encoded bytes (w/o pad symbols)
     *  @return         A prefix of <em>group</em> with the calculated
     *                  length regarding the decoded bytes.
     */
    protected def takeNonPads(group: Seq[Byte], numChars: Int): Seq[Byte] =
        group take (numChars * codeBitlen / 8).toInt


    /**
     *  A pair (last,group) with last = true if the method identified the
     *  group as the last group of encoded bytes.
     *  @param data A pair (n,seq) with n being the number of already decoded
     *              bytes and seq being a seqence of bytes.
     *  @param size The number of expected bytes before decoding.
     *  @return     A pair (last,group) with last = true if the method identified
     *              <em>seq</em> as the last group of encoded bytes and
     *              <em>group</em> as the decoded group of bytes.
     */
    protected def decodeGroup(data: (Int,Seq[Byte]), size: Long): (Boolean,Try[Seq[Byte]]) = {
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


    /**
     *  This predicate validates the length of the encoded byte collection.
     *  @param num  The number of already consumed bytes.
     *  @param len  The length of the current encoded byte group.
     *  @param size The number of bytes of the encoded byte collection.
     *  @return     True if <em>len</em> has the expected value.
     */
    protected def checkLength(num: Int, len: Int, size: Long) =
        (len > 1) &&
        (size != 0 || len == groupSize) &&
        (size == 0 || len == groupSize || (size  == num + len)) &&
        (size == 0 || num < size - groupSize || (size  == num + len))


    /**
     *  Checks the internal Padding (e.g. "A3=3" is not allowed) and returns
     *  a triple (idx,last,ok) where idx is the index of the first pad, last is
     *  true if the sequence contains pad symbols and ok is true if the padding
     *  is valid.
     *  @param len The length of the given seqence.
     *  @param x   A sequence of encoded bytes.
     *  @return    (idx,last,ok) where
     *             idx is the index of the first pad or idx = groupSize,
     *             last is true iff the sequence contains pad symbols and
     *             ok is true iff the padding is valid.
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


    /**
     *  This predicate tests if a byte is element of the code alphabet.
     *  @param b A byte to be validated.
     *  @return  true iff b is element of the code alphabet.
     */
    protected def allowedByte(b: Byte) = chars.keySet contains b


    /**
     *  This predicate tests if a byte is element of {0,...,N,pad}.
     *  @param b A byte to be validated.
     *  @return  true iff b is element of {0,...,N,pad}.
     */
    protected def crlfByte(b: Byte) = b <= pad


    /**
     *  An byte stream with each byte being element of {0,...,M}, where M <= N.
     *  @param s An encoded byte stream.
     *  @return  A stream with each byte being element of {0,...,M},
     *           where M <= N.
     */
    protected def invertBytes(s: Stream[Byte]): Stream[Byte] =
        if (s.isEmpty) Stream.empty
        else chars get s.head match {
            case Some(x) => x #:: invertBytes(s.tail)
            case _ => Stream.empty
        }


    /**
     *  A validated stream of byte sequences such that the stream is not longer
     *  than expected.
     *  @param s A stream of pairs (last,Try(seq)) with
     *           last = true iff seq is the expected last sequence and
     *           seq being a decoded byte sequence.
     *  @return  Success(stream) with
     *           stream being a stream of byte sequences or
     *           Failure(e) if the validation failed of Try(seq) is a Failure.
     */
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
