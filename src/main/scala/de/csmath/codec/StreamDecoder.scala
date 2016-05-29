package de.csmath.codec

import scala.util._

trait StreamDecoder {

    def checkedByteStream(pred: (Byte => Boolean), data: Traversable[Byte]): Try[Stream[Byte]] =
        if (data.isEmpty) Success(Stream.empty)
        else
            if (pred(data.head))
                checkedByteStream(pred,data.tail) map ( s => data.head #:: s )
            else
                Failure( new IllegalArgumentException("Data contains illegal bytes."))


    def filteredStream(pred: (Byte => Boolean), s:Stream[Byte]) =
        s filter pred
}
