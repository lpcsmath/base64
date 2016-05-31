package de.csmath.codec

import scala.util._


class Base32HexDecoder extends Base32Decoder {

    override val chars = Map() ++
            (Seq() ++ (((48 to 57) map (_.toByte)) ++
            ((65 to 86) map (_.toByte)) :+
            '='.toByte :+ '\n'.toByte :+
            '\r'.toByte ) zip ((0 to 100) map (_.toByte)))

}
