import org.scalatest._
import de.csmath.codec._


class StreamEncoderSpec extends FlatSpec with Matchers {

  "StreamEncoder" should "create a IntStream" in {
      val list = List[Byte](1,2,3)
      val sc = new StreamEncoder() {
          val s = intStream(list)
      }

      sc.s.head should === (1)
      sc.s.tail.head should === (2)
      sc.s.tail.tail.head should === (3)
      sc.s.tail.tail.tail should === (Stream.empty)
  }


}
