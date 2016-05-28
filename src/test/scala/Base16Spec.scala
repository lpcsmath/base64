import org.scalatest._
import de.csmath.codec.Base16._
import de.csmath.codec.Codec._


class Base16Spec extends FlatSpec with Matchers {

  "Base16" should "encode to BASE16" in {
      encodeToString("", BASE16) should === ("")
      encodeToString("f", BASE16) should === ("66")
      encodeToString("fo", BASE16) should === ("666F")
      encodeToString("foo", BASE16) should === ("666F6F")
      encodeToString("foob", BASE16) should === ("666F6F62")
      encodeToString("fooba", BASE16) should === ("666F6F6261")
      encodeToString("foobar", BASE16) should === ("666F6F626172")
      encodeToString((0 to 15) map (_.toByte), BASE16) should
                === ("000102030405060708090A0B0C0D0E0F")
  }

}
