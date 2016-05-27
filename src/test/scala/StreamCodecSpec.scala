import org.scalatest._
import de.csmath.codec._


class StreamCodecSpec extends FlatSpec with Matchers {

  "StreamCodec" should "create a ByteStream" in {
      val list = List[Byte](1,2,3)
      val sc = new StreamCodec() { }

      val s = sc.byteStream(list)

      s.head should === (1)
      s.tail.head should === (2)
      s.tail.tail.head should === (3)
      s.tail.tail.tail should === (Stream.empty)
  }

  "StreamCodec" should "create a IntStream" in {
      val list = List[Byte](1,2,3)
      val sc = new StreamCodec() { }

      val s = sc.intStream(list)

      s.head should === (1)
      s.tail.head should === (2)
      s.tail.tail.head should === (3)
      s.tail.tail.tail should === (Stream.empty)
  }

  "StreamCodec" should "create a groupStream" in {
      val list = List[Byte](1,2,3)
      val sc = new StreamCodec() { }

      val s = sc.intStream(list)
      val g = sc.groupStream(2,s)

      g.head should === (Array(1,2))
      g.tail.head should === (Array(3))
      g.tail.tail should === (Stream.empty)
  }

  "StreamCodec" should "create a filledStream" in {
      val list = List[Byte](1,2,3)
      val sc = new StreamCodec() { }

      val s = sc.intStream(list)
      val g = sc.groupStream(2,s)
      val f = sc.filledStream(2,g)

      f.head._1 should === (0)
      f.head._2 should === (Array(1,2))
      f.tail.head._1 should === (1)
      f.tail.head._2 should === (Array(3,0))
      f.tail.tail should === (Stream.empty)
  }

  "StreamCodec" should "flatten a filledStream" in {
      val list = List[Byte](1,2,3)
      val sc = new StreamCodec() { }

      val s = sc.byteStream(list)
      val g = sc.groupBStream(2,s)
      val f = sc.flatten(g)

      f.head should === (1)
      f.tail.head should === (2)
      f.tail.tail.head should === (3)
      f.tail.tail.tail should === (Stream.empty)
  }

}
