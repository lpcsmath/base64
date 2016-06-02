# base64

### Implementation of RFC 4648 in Scala.

This Package provides operations to encode arbitrary collections of bytes to a
Base64, Base32 or Base16 code (respectively decode from it). Base on RFC 4648
the following codes are distinguished:

- **Base64** with variations for file system and URL save symbols, as well as
percent encoding of pad symbols,
- **Base32** with optional percent encoding of pad symbols,
- **Base32Hex** which is an extension of the hexadecimal number representation,
- **Base16** as the hexadecimal number representation.

See also the comments in file Codec.scala for details.

#### Base64

To encode a traversable collection of bytes one uses the encode method of the
object Base64.

```
import de.csmath.codec.Base64._  

encode(List[Byte](1,2,3,4))         // Stream(65,?)  
encode(List[Byte](1,2,3,4)).toList  // List(65, 81, 73, 68, 66, 65, 61, 61)  
encodeToString(List[Byte](1,2,3,4)) // "AQIDBA=="
```

The encoding can be controlled by providing the needed codec. The following
examples show the difference between Base64 and Base64Url.

```
import de.csmath.codec.Base64._  
import de.csmath.codec.Codec._

encodeToString(List[Byte](-1,-17))           // "/+8="  
encodeToString(List[Byte](-1,-17),BASE64URL) // "_-8%3D"
```

To decode a given Base64 encoded collection of bytes one uses the decode method
of the object Base64.

```
import de.csmath.codec.Base64._  
import de.csmath.codec.Codec._

decode("AQIDBA==".getBytes)                    // Success(Stream(1,?))  
decode("AQIDBA==".getBytes) map (_.toList)     // Success(List(1,2,3,4))  
decode("AQIDBA",BASE64NOPAD,4) map (_.toList)  // Success(List(1,2,3,4))
```

### Base32 and Base32Hex

The following examples show the difference between Base32 and Base32Hex.
One can observe Base32 as being the default (if a byte collection is provided).

```
import de.csmath.codec.Base32._
import de.csmath.codec.Codec._

encodeToString("foobar".getBytes)           // "MZXW6YTBOI======"
encodeToString("foobar".getBytes,BASE32HEX) // "CPNMUOJ1E8======"
```

### Base16

There is only one way to encode to Base16, because it uses already file system
and URL save symbols and does not need to be padded. The following example
shows the encoding of an non terminating stream.

```
import de.csmath.codec.Base16._

val inf = encode(Stream.from(0) map (_.toByte))
inf take 10 map (_.toChar) toList               // List(0, 0, 0, 1, 0, 2, 0, 3, 0, 4)
```

### Exceptions

The decoding operations perform several checks to validate the given data.
The following code examples show the effect of decoding invalid data.

```
import de.csmath.codec.Base64._

decode("Z".getBytes)        // Failure(de.csmath.codec.RejectException: invalid length)
decode("Zg=g".getBytes)     // Failure(de.csmath.codec.RejectException: wrong placed pad symbols)
decode("Z;==".getBytes)     // Failure(de.csmath.codec.RejectException: data contains illegal bytes)
decode("Zg==Zg==".getBytes) // Failure(de.csmath.codec.RejectException: illegal concatenation)
```
