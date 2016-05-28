package de.csmath.codec;

import Codec._


class Base32HexEncoder extends Base32Encoder {

    override val chars = Vector() ++ (((48 to 57) map (_.toByte)) ++
                                      ((65 to 86) map (_.toByte)) )

}
