import org.scalatest._
import de.csmath.codec.Base32._
import de.csmath.codec.Codec._

import scala.util._


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

  "Base32" should "encode to BASE32NOPAD" in {
      encodeToString("", BASE32NOPAD) should === ("")
      encodeToString("f", BASE32NOPAD) should === ("MY")
      encodeToString("fo", BASE32NOPAD) should === ("MZXQ")
      encodeToString("foo", BASE32NOPAD) should === ("MZXW6")
      encodeToString("foob", BASE32NOPAD) should === ("MZXW6YQ")
      encodeToString("fooba", BASE32NOPAD) should === ("MZXW6YTB")
      encodeToString("foobar", BASE32NOPAD) should === ("MZXW6YTBOI")
  }

  "Base32" should "encode to BASE32URL" in {
      encodeToString("", BASE32URL) should === ("")
      encodeToString("f", BASE32URL) should === ("MY%3D%3D%3D%3D%3D%3D")
      encodeToString("fo", BASE32URL) should === ("MZXQ%3D%3D%3D%3D")
      encodeToString("foo", BASE32URL) should === ("MZXW6%3D%3D%3D")
      encodeToString("foob", BASE32URL) should === ("MZXW6YQ%3D")
      encodeToString("fooba", BASE32URL) should === ("MZXW6YTB")
      encodeToString("foobar", BASE32URL) should === ("MZXW6YTBOI%3D%3D%3D%3D%3D%3D")
  }

  "Base32" should "encode to BASE32HEX" in {
      encodeToString("", BASE32HEX) should === ("")
      encodeToString("f", BASE32HEX) should === ("CO======")
      encodeToString("fo", BASE32HEX) should === ("CPNG====")
      encodeToString("foo", BASE32HEX) should === ("CPNMU===")
      encodeToString("foob", BASE32HEX) should === ("CPNMUOG=")
      encodeToString("fooba", BASE32HEX) should === ("CPNMUOJ1")
      encodeToString("foobar", BASE32HEX) should === ("CPNMUOJ1E8======")
  }

  "Base32" should "encode to BASE32HEXNOPAD" in {
      encodeToString("", BASE32HEXNOPAD) should === ("")
      encodeToString("f", BASE32HEXNOPAD) should === ("CO")
      encodeToString("fo", BASE32HEXNOPAD) should === ("CPNG")
      encodeToString("foo", BASE32HEXNOPAD) should === ("CPNMU")
      encodeToString("foob", BASE32HEXNOPAD) should === ("CPNMUOG")
      encodeToString("fooba", BASE32HEXNOPAD) should === ("CPNMUOJ1")
      encodeToString("foobar", BASE32HEXNOPAD) should === ("CPNMUOJ1E8")
  }

  "Base32" should "encode to BASE32HEXURL" in {
      encodeToString("", BASE32HEXURL) should === ("")
      encodeToString("f", BASE32HEXURL) should === ("CO%3D%3D%3D%3D%3D%3D")
      encodeToString("fo", BASE32HEXURL) should === ("CPNG%3D%3D%3D%3D")
      encodeToString("foo", BASE32HEXURL) should === ("CPNMU%3D%3D%3D")
      encodeToString("foob", BASE32HEXURL) should === ("CPNMUOG%3D")
      encodeToString("fooba", BASE32HEXURL) should === ("CPNMUOJ1")
      encodeToString("foobar", BASE32HEXURL) should === ("CPNMUOJ1E8%3D%3D%3D%3D%3D%3D")
  }

  "Base32" should "decode from BASE32" in {
      decodeToString("", BASE32) should === (Success(""))
      decodeToString("MY======", BASE32) should === (Success("f"))
      decodeToString("MZXQ====", BASE32) should === (Success("fo"))
      decodeToString("MZXW6===", BASE32) should === (Success("foo"))
      decodeToString("MZXW6YQ=", BASE32) should === (Success("foob"))
      decodeToString("MZXW6YTB", BASE32) should === (Success("fooba"))
      decodeToString("MZXW6YTBOI======", BASE32) should === (Success("foobar"))
  }

  "Base32" should "decode from BASE32 w/o padding" in {
      decodeToString("", BASE32,0) should === (Success(""))
      decodeToString("MY", BASE32,1) should === (Success("f"))
      decodeToString("MZXQ", BASE32,2) should === (Success("fo"))
      decodeToString("MZXW6", BASE32,3) should === (Success("foo"))
      decodeToString("MZXW6YQ", BASE32,4) should === (Success("foob"))
      decodeToString("MZXW6YTB", BASE32,5) should === (Success("fooba"))
      decodeToString("MZXW6YTBOI", BASE32,6) should === (Success("foobar"))
  }

}
