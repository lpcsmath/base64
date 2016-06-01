package de.csmath.codec;

import Codec._


/**
 *  The class Base32HexEncoder provides operations to encode a
 *  traversable collection of bytes.
 */
class Base32HexEncoder extends Base32Encoder {

    /**
     *  The code alphabet.
     */
    override val chars = Vector() ++ (((48 to 57) map (_.toByte)) ++
                                      ((65 to 86) map (_.toByte)) )

}
