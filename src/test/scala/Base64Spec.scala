import org.scalatest._
import de.csmath.codec.Base64._
import de.csmath.codec.Codec._


class Base64Spec extends FlatSpec with Matchers {

  "Base64" should "encode to BASE64" in {
      encode("", BASE64) should === ("")
      encode("f", BASE64) should === ("Zg==")
      encode("fo", BASE64) should === ("Zm8=")
      encode("foo", BASE64) should === ("Zm9v")
      encode("foob", BASE64) should === ("Zm9vYg==")
      encode("fooba", BASE64) should === ("Zm9vYmE=")
      encode("foobar", BASE64) should === ("Zm9vYmFy")
      encode(List[Byte](-1,-17), BASE64) should === ("/+8=")
  }

  "Base64" should "encode to BASE64URL" in {
      encode("", BASE64URL) should === ("")
      encode("f", BASE64URL) should === ("Zg%3B%3B")
      encode("fo", BASE64URL) should === ("Zm8%3B")
      encode("foo", BASE64URL) should === ("Zm9v")
      encode("foob", BASE64URL) should === ("Zm9vYg%3B%3B")
      encode("fooba", BASE64URL) should === ("Zm9vYmE%3B")
      encode(List[Byte](-1,-17), BASE64URL) should === ("_-8%3B")
  }

  "Base64" should "encode to BASE64NOPAD" in {
      encode("", BASE64NOPAD) should === ("")
      encode("f", BASE64NOPAD) should === ("Zg")
      encode("fo", BASE64NOPAD) should === ("Zm8")
      encode("foo", BASE64NOPAD) should === ("Zm9v")
      encode("foob", BASE64NOPAD) should === ("Zm9vYg")
      encode("fooba", BASE64NOPAD) should === ("Zm9vYmE")
      encode("foobar", BASE64NOPAD) should === ("Zm9vYmFy")
  }

  "Base64" should "encode to BASE64URLNOPAD" in {
      encode("", BASE64URLNOPAD) should === ("")
      encode("f", BASE64URLNOPAD) should === ("Zg")
      encode("fo", BASE64URLNOPAD) should === ("Zm8")
      encode("foo", BASE64URLNOPAD) should === ("Zm9v")
      encode("foob", BASE64URLNOPAD) should === ("Zm9vYg")
      encode("fooba", BASE64URLNOPAD) should === ("Zm9vYmE")
      encode("foobar", BASE64URLNOPAD) should === ("Zm9vYmFy")
      encode(List[Byte](-1,-17), BASE64URLNOPAD) should === ("_-8")
  }
}
