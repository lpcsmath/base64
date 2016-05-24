import org.scalatest._
import de.csmath.codec.Base64._

class Base64Spec extends FlatSpec with Matchers {
  "Base64" should "encode to base64" in {
      encode("", "") should === ("")
      encode("f", "") should === ("Zg==")
      encode("fo", "") should === ("Zm8=")
      encode("foo", "") should === ("Zm9v")
      encode("foob", "") should === ("Zm9vYg==")
      encode("fooba", "") should === ("Zm9vYmE=")
      encode("foobar", "") should === ("Zm9vYmFy")
  }
}
