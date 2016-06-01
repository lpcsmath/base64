package de.csmath.codec;

import Codec._


/**
 *  The class Base64FileDecoder provides operations to decode a
 *  traversable collection of bytes encoded with file system save symbols.
 */
class Base64FileDecoder extends Base64Decoder {

    /**
     *  Mapping of the code alphabet to a value between 0 and M
     *  with M >= N.
     */
    override val chars = Map() ++
            (Seq() ++ (((65 to 90) map (_.toByte)) ++
            ((97 to 122) map (_.toByte)) ++
            ((48 to 57) map (_.toByte)) :+
            '-'.toByte :+ '_'.toByte :+
            '='.toByte :+ '\n'.toByte :+
            '\r'.toByte ) zip ((0 to 100) map (_.toByte)))

}
