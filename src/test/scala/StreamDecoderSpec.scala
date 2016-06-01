import org.scalatest._
import de.csmath.codec._
import scala.util._


class StreamDecoderSpec extends FlatSpec with Matchers {

  "StreamDecoder" should "check allowed bytes" in {
      val list1 = List[Byte](1,2,3)
      val list2 = List[Byte](1,2,1,2)
      val allowed = Set[Byte](1,2)
      val sc = new StreamDecoder() {
          val pred = (x) => allowed contains x
          val s = checkedByteStream(pred,list1)
          val t = checkedByteStream(pred,list2)
      }

      sc.s.isFailure should === (true)
      sc.t.isSuccess should === (true)

      (sc.t: @unchecked) match {
          case Success(u) =>
            u.head should === (1)
            u.tail.head should === (2)
            u.tail.tail.head should === (1)
            u.tail.tail.tail.head should === (2)
            u.tail.tail.tail.tail should === (Stream.empty)
      }
  }

}
