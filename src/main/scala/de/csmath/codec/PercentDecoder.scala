package de.csmath.codec

import scala.util._


/**
 *  This trait provides operations to decode collection of
 *  bytes which contain percent encoded equal signs as a byte sequence.
 */
trait PercentDecoder extends StreamDecoder {

    /**
     *  Validates and converts a traversable collection of bytes by decoding
     *  percent encoded equal signs.
     *  @param data         A traversable byte collection.
     *  @param allowedBytes A set of bytes allowed to be in the collection.
     *  @return             Success(col) iff the <em>data</em> contains only
     *                      valid bytes. The collection <em>col</em> contains
     *                      the decoded bytes.
     */
    protected def percentDecode(data: Traversable[Byte], allowedBytes: Set[Byte]) = {
        val allowed = allowedBytes union Set('%'.toByte, '3'.toByte,'D'.toByte, 'd'.toByte)
        if (data exists (x => x == '%'.toByte)) {
            checkedByteStream(validByte(allowed,_),data) map { s =>
                val urlString = (s map (_.toChar)).mkString
                "%3[Dd]".r.replaceAllIn(urlString, "=" ).toStream map (_.toByte)
            }
        } else Success(data)
    }


    /**
     *  This predicate tests if a given byte is element of the given set.
     *  @param valid The set of valid bytes
     *  @param b     The byte of question.
     *  @return      true iff <em>b</em> is element of <em>valid</em>.
     */
    protected def validByte(valid: Set[Byte], b: Byte) = valid contains b
}
