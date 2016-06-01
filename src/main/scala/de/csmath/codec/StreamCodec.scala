package de.csmath.codec

import scala.language.postfixOps


/**
 *  This trait provides stream operations for byte oriented codecs.
 */
trait StreamCodec {

    /**
     *  Creates a byte stream from a traversable collection of type <em>Byte</em>.
     *  @param t The traversable collection of type <em>Byte</em>
     *  @return  A <em>Stream</em> of type <em>Byte</em>.
     */
    protected def byteStream(t: Traversable[Byte]): Stream[Byte] =
        if (t.isEmpty) Stream.empty
        else t.head #:: byteStream(t.tail)


    /**
     *  Creates a stream of sequences with a specified maximal length.
     *  @param groupSize The maximal length of the sequence.
     *  @return          A <em>Stream</em> of sequences.
     */
    protected def groupStream[T](groupSize: Int, s: Stream[T]): Stream[Seq[T]] =
        groupStreamAux(s grouped groupSize)


    /**
     *  The helper method to [[groupStream]].
     *  @param i The iterator of the group.
     *  @return  A <em>Stream</em> of sequences.
     */
    protected def groupStreamAux[T](i: Iterator[Stream[T]]): Stream[Seq[T]] =
        if (!i.hasNext) Stream.empty
        else i.next.toSeq #:: groupStreamAux(i)


    /**
     *  Creates a tuple (fill, group) stream from a group stream where all
     *  groups have the maximal length and <em>fill</em> is the number of 0s
     *  added to each group to expand it to the given group size.
     *  @param groupSize The needed length of the sequence.
     *  @return          A <em>Stream</em> of pairs (fill, group). With
     *                   <em>fill</em> being the number of added 0s and
     *                   group a sequence of length <em>groupSize</em>.
     */
    protected def filledStream(groupSize: Int, s:Stream[Seq[Int]]): Stream[(Int,Seq[Int])] =
        s map ( seq => (groupSize - seq.length, seq padTo (groupSize,0)))


    /**
     *  Small helper method to flatten a stream of traversable collections of
     *  type <em>Byte</em>.
     *  @param s The stream of traversable collections.
     *  @return  A flattened <em>Stream</em> of type <em>Byte</em>.
     */
    protected def flatten(s:Stream[Traversable[Byte]]): Stream[Byte] = {
        s map (byteStream) flatten
    }

}
