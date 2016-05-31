# base64

### Implementation of RFC 4648 in Scala.

under construction especially Base32 and Base16.

#### Base64

To encode a traversable collection of bytes one uses the encode method of the
object Base64.

> import de.csmath.codec.Base64._  
>
> encode(List\[Byte\](1,2,3,4))         // Stream(65,?)  
> encode(List\[Byte\](1,2,3,4)).toList  // List(65, 81, 73, 68, 66, 65, 61, 61)  
> encodeToString(List\[Byte\](1,2,3,4)) // "AQIDBA=="

The encoding can be controlled by providing the needed codec.

> import de.csmath.codec.Base64._  
> import de.csmath.codec.Codec._
>
> encodeToString(List\[Byte\](-1,-17)) // "/+8="  
> encodeToString(List\[Byte\](-1,-17),BASE64URL) // "\_-8%3D"

To decode a given Base64 encoded collection of bytes one uses the decode method
of the object Base64.

> import de.csmath.codec.Base64._  
> import de.csmath.codec.Codec._
>
> decode("AQIDBA==".getBytes) // Success(Stream(1,?))  
> decode("AQIDBA==".getBytes) map (\_.toList) // Success(List(1,2,3,4))  
> decode("AQIDBA",BASE64NOPAD,4) map (\_.toList) // Success(List(1,2,3,4))
