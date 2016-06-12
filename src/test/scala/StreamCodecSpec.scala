import org.scalatest._
import de.csmath.codec._


class StreamCodecSpec extends FlatSpec with Matchers {

  "StreamCodec" should "create a ByteStream" in {
      val list = List[Byte](1,2,3)
      val sc = new StreamCodec() {
          val s = byteStream(list)
      }

      sc.s.head should === (1)
      sc.s.tail.head should === (2)
      sc.s.tail.tail.head should === (3)
      sc.s.tail.tail.tail should === (Stream.empty)
  }

  "StreamCodec" should "create a groupStream" in {
      val list = List[Byte](1,2,3)
      val sc = new StreamCodec() {
          val s = byteStream(list)
          val g = groupStream(2,s)
      }

      sc.g.head should === (Array(1,2))
      sc.g.tail.head should === (Array(3))
      sc.g.tail.tail should === (Stream.empty)
  }

  "StreamCodec" should "create a filledStream" in {
      val list = List[Byte](1,2,3)
      val sc = new StreamCodec() {
          val s = byteStream(list) map (_.toInt)
          val g = groupStream(2,s)
          val f = filledStream(2,g)
      }

      sc.f.head._1 should === (0)
      sc.f.head._2 should === (Array(1,2))
      sc.f.tail.head._1 should === (1)
      sc.f.tail.head._2 should === (Array(3,0))
      sc.f.tail.tail should === (Stream.empty)
  }

}
