package de.csmath.codec;

import Codec._
import scala.util._


class Base64UrlDecoder extends Base64FileDecoder with PercentDecoder {


    override def decode(data:Traversable[Byte], enc: Codec.Value, codeSize: Long): Try[Stream[Byte]] = {
        decodeAndApply( data, chars.keySet, super.decode(_,enc,codeSize) )
    }

}
