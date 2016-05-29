import org.scalatest._
import de.csmath.codec.Base64._
import de.csmath.codec.Codec._
import scala.util._


class Base64Spec extends FlatSpec with Matchers {

  "Base64" should "encode to BASE64" in {
      encodeToString("", BASE64) should === ("")
      encodeToString("f", BASE64) should === ("Zg==")
      encodeToString("fo", BASE64) should === ("Zm8=")
      encodeToString("foo", BASE64) should === ("Zm9v")
      encodeToString("foob", BASE64) should === ("Zm9vYg==")
      encodeToString("fooba", BASE64) should === ("Zm9vYmE=")
      encodeToString("foobar", BASE64) should === ("Zm9vYmFy")
      encodeToString(List[Byte](-1,-17), BASE64) should === ("/+8=")
  }

  "Base64" should "encode to BASE64FILE" in {
      encodeToString("", BASE64FILE) should === ("")
      encodeToString("f", BASE64FILE) should === ("Zg==")
      encodeToString("fo", BASE64FILE) should === ("Zm8=")
      encodeToString("foo", BASE64FILE) should === ("Zm9v")
      encodeToString("foob", BASE64FILE) should === ("Zm9vYg==")
      encodeToString("fooba", BASE64FILE) should === ("Zm9vYmE=")
      encodeToString("foobar", BASE64FILE) should === ("Zm9vYmFy")
      encodeToString(List[Byte](-1,-17), BASE64FILE) should === ("_-8=")
  }

  "Base64" should "encode to BASE64URL" in {
      encodeToString("", BASE64URL) should === ("")
      encodeToString("f", BASE64URL) should === ("Zg%3D%3D")
      encodeToString("fo", BASE64URL) should === ("Zm8%3D")
      encodeToString("foo", BASE64URL) should === ("Zm9v")
      encodeToString("foob", BASE64URL) should === ("Zm9vYg%3D%3D")
      encodeToString("fooba", BASE64URL) should === ("Zm9vYmE%3D")
      encodeToString(List[Byte](-1,-17), BASE64URL) should === ("_-8%3D")
  }

  "Base64" should "encode to BASE64NOPAD" in {
      encodeToString("", BASE64NOPAD) should === ("")
      encodeToString("f", BASE64NOPAD) should === ("Zg")
      encodeToString("fo", BASE64NOPAD) should === ("Zm8")
      encodeToString("foo", BASE64NOPAD) should === ("Zm9v")
      encodeToString("foob", BASE64NOPAD) should === ("Zm9vYg")
      encodeToString("fooba", BASE64NOPAD) should === ("Zm9vYmE")
      encodeToString("foobar", BASE64NOPAD) should === ("Zm9vYmFy")
  }

  "Base64" should "encode to BASE64FILENOPAD" in {
      encodeToString("", BASE64FILENOPAD) should === ("")
      encodeToString("f", BASE64FILENOPAD) should === ("Zg")
      encodeToString("fo", BASE64FILENOPAD) should === ("Zm8")
      encodeToString("foo", BASE64FILENOPAD) should === ("Zm9v")
      encodeToString("foob", BASE64FILENOPAD) should === ("Zm9vYg")
      encodeToString("fooba", BASE64FILENOPAD) should === ("Zm9vYmE")
      encodeToString("foobar", BASE64FILENOPAD) should === ("Zm9vYmFy")
      encodeToString(List[Byte](-1,-17), BASE64FILENOPAD) should === ("_-8")
  }

  "Base64" should "encode to BASE64URLNOPAD" in {
      encodeToString("", BASE64URLNOPAD) should === ("")
      encodeToString("f", BASE64URLNOPAD) should === ("Zg")
      encodeToString("fo", BASE64URLNOPAD) should === ("Zm8")
      encodeToString("foo", BASE64URLNOPAD) should === ("Zm9v")
      encodeToString("foob", BASE64URLNOPAD) should === ("Zm9vYg")
      encodeToString("fooba", BASE64URLNOPAD) should === ("Zm9vYmE")
      encodeToString("foobar", BASE64URLNOPAD) should === ("Zm9vYmFy")
      encodeToString(List[Byte](-1,-17), BASE64URLNOPAD) should === ("_-8")
  }

  "Base64" should "decode to BASE64" in {
      decodeToString("", BASE64) should === (Success(""))
      decodeToString("Zg==", BASE64) should === (Success("f"))
      decodeToString("Zm8=", BASE64) should === (Success("fo"))
      decodeToString("Zm9v", BASE64) should === (Success("foo"))
      decodeToString("Zm9vYg==", BASE64) should === (Success("foob"))
      decodeToString("Zm9vYmE=", BASE64) should === (Success("fooba"))
      decodeToString("Zm9vYmFy", BASE64) should === (Success("foobar"))
      decode("/+8=".getBytes, BASE64) should === (Success(Seq(-1,-17)))
  }

}
