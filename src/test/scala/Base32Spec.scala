import org.scalatest._
import de.csmath.codec.Base32._
import de.csmath.codec.Codec._


class Base32Spec extends FlatSpec with Matchers {

  "Base32" should "encode to BASE32" in {
      encodeToString("", BASE32) should === ("")
      encodeToString("f", BASE32) should === ("MY======")
      encodeToString("fo", BASE32) should === ("MZXQ====")
      encodeToString("foo", BASE32) should === ("MZXW6===")
      encodeToString("foob", BASE32) should === ("MZXW6YQ=")
      encodeToString("fooba", BASE32) should === ("MZXW6YTB")
      encodeToString("foobar", BASE32) should === ("MZXW6YTBOI======")
  }

}
