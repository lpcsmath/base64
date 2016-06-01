package de.csmath.codec

import scala.util._

trait StreamEncoder extends StreamCodec {

    protected def intStream(t: Traversable[Byte]): Stream[Int] = {
        if (t.isEmpty) Stream.empty
        else {
            val item = if (t.head < 0) 256 + t.head else t.head.toInt
            item #:: intStream(t.tail)
        }
    }

}
