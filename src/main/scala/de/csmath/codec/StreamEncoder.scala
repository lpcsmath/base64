package de.csmath.codec

import scala.util._


/**
 *  This trait provides stream operations for byte oriented encoders.
 */
trait StreamEncoder extends StreamCodec {

    /**
     *  Creates an <em>Int</em> stream out of a byte stream where each
     *  integer is non negative.
     *  @param t A traversable collection of bytes.
     *  @return  A stream of non negative integers.
     */
    protected def intStream(t: Traversable[Byte]): Stream[Int] = {
        if (t.isEmpty) Stream.empty
        else {
            val item = if (t.head < 0) 256 + t.head else t.head.toInt
            item #:: intStream(t.tail)
        }
    }

}
