package de.csmath.codec

trait StreamCodec {

    def intStream(t: Traversable[Byte]): Stream[Int] = {
        if (t.isEmpty) Stream.empty
        else {
            val item = if (t.head < 0) 256 + t.head else t.head.toInt
            item #:: intStream(t.tail)
        }
    }

    def byteStream(t: Traversable[Byte]): Stream[Byte] =
        if (t.isEmpty) Stream.empty
        else t.head #:: byteStream(t.tail)

    def groupStream(groupSize: Int, s: Stream[Int]): Stream[Seq[Int]] =
        groupStreamAux(s grouped groupSize)

    def groupStreamAux(i: Iterator[Stream[Int]]): Stream[Seq[Int]] =
        if (!i.hasNext) Stream.empty
        else i.next.toSeq #:: groupStreamAux(i)

    def groupBStream(groupSize: Int, s: Stream[Byte]): Stream[Seq[Byte]] =
        groupBStreamAux(s grouped groupSize)

    def groupBStreamAux(i: Iterator[Stream[Byte]]): Stream[Seq[Byte]] =
        if (!i.hasNext) Stream.empty
        else i.next.toSeq #:: groupBStreamAux(i)

    def filledStream(groupSize: Int, s:Stream[Seq[Int]]): Stream[(Int,Seq[Int])] =
        if (s.isEmpty) Stream.empty
        else {
            var item: Seq[Int] = s.head
            val fill = groupSize - item.length
            while (item.length < groupSize) {
                item = item :+ 0
            }
            (fill, item) #:: filledStream(groupSize, s.tail)
        }

    def flatten(s:Stream[Traversable[Byte]]): Stream[Byte] = {
        s map (byteStream) flatten
    }

}
