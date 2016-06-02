package de.csmath.codec

import scala.util._


/**
 *  This trait provides stream operations for byte oriented decoders.
 */
trait StreamDecoder extends StreamCodec {

    /**
     *  Creates a stream of bytes inside a <em>Success</em> constructor if each
     *  of the items of the given traversable collection fullfill the given
     *  predicate.
     *  @param pred The predicate to ensure a certain property of each byte.
     *  @param data The traversable collection of bytes.
     *  @return     <em>Success(stream)</em> with <em>stream</em> being a
     *              stream of bytes where <em>pred</em> holds for each item
     *              or <em>Failure(e)</em> with <em>e</em> being an
     *              IllegalArgumentException.
     */
    protected def checkedByteStream(pred: (Byte => Boolean), data: Traversable[Byte]): Try[Stream[Byte]] =
        if (data.isEmpty) Success(Stream.empty)
        else
            if (pred(data.head))
                checkedByteStream(pred,data.tail) map ( s => data.head #:: s )
            else
                Failure( new RejectException("data contains illegal bytes"))


    /**
     *  This is small helper function to filter a byte stream according
     *  to a given predicate.
     *  @param pred The predicate to ensure a certain property of each byte.
     *  @param s    The stream to be filtered.
     *  @return     The remaining bytes that fullfill the predicate.
     */
    protected def filteredStream(pred: (Byte => Boolean), s:Stream[Byte]) =
        s filter pred

}
