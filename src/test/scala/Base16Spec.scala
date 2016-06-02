import org.scalatest._
import de.csmath.codec.Base16._
import de.csmath.codec.Codec._
import scala.util._


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

  "Base16" should "decode from BASE16" in {
      decodeToString("", BASE16) should === (Success(""))
      decodeToString("66", BASE16) should === (Success("f"))
      decodeToString("666F", BASE16) should === (Success("fo"))
      decodeToString("666F6F", BASE16) should === (Success("foo"))
      decodeToString("666F6F62", BASE16) should === (Success("foob"))
      decodeToString("666F6F6261", BASE16) should === (Success("fooba"))
      decodeToString("666F6F626172", BASE16) should === (Success("foobar"))
      decode("000102030405060708090A0B0C0D0E0F", BASE16) map (_.toList) should
                === (Success(List(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15)))
  }

  "Base16" should "decode from BASE16 with known size" in {
      decodeToString("", BASE16,0) should === (Success(""))
      decodeToString("66", BASE16,1) should === (Success("f"))
      decodeToString("666F", BASE16,2) should === (Success("fo"))
      decodeToString("666F6F", BASE16,3) should === (Success("foo"))
      decodeToString("666F6F62", BASE16,4) should === (Success("foob"))
      decodeToString("666F6F6261", BASE16,5) should === (Success("fooba"))
      decodeToString("666F6F626172", BASE16,6) should === (Success("foobar"))
      decode("000102030405060708090A0B0C0D0E0F", BASE16,16) map (_.toList) should
                === (Success(List(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15)))
  }

}
