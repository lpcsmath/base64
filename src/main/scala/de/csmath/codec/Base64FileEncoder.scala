package de.csmath.codec;

import Codec._


class Base64FileEncoder extends Base64Encoder {


    override val chars = Vector() ++ (((65 to 90) map (_.toByte)) ++
                                    ((97 to 122) map (_.toByte)) ++
                                    ((48 to 57) map (_.toByte)) :+
                                    '-'.toByte :+ '_'.toByte)


}
