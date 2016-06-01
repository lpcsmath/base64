package de.csmath.codec

import scala.util._


/**
 *  The class Base32HexDecoder provides operations to decode a Base32Hex encoded
 *  collection of bytes.
 */
class Base32HexDecoder extends Base32Decoder {

    /**
     *  Mapping of the code alphabet to a value between 0 and M
     *  with M >= N.
     */
    override val chars = Map() ++
            (Seq() ++ (((48 to 57) map (_.toByte)) ++
            ((65 to 86) map (_.toByte)) :+
            '='.toByte :+ '\n'.toByte :+
            '\r'.toByte ) zip ((0 to 100) map (_.toByte)))

}
