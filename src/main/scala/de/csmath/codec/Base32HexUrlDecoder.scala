package de.csmath.codec;

import Codec._
import scala.util._


/**
 *  The class Base32HexUrlDecoder provides operations to decode a
 *  traversable collection of bytes encoded with URL save symbols.
 */
class Base32HexUrlDecoder extends Base32HexDecoder with PercentDecoder {

    /**
     *  Stream of decoded bytes from a traversable collection.
     *  @param data      The stream or other kind of Traversable of encoded bytes.
     *  @param enc       The codec to use for decoding.
     *  @param plainSize The expected number of bytes after decoding.
     *  @return          Success(stream) where stream is the decoded byte stream,
     *                   or Failure(e) if <em>data</em> contains invalid bytes
     *                   or the size of the collection does not match the
     *                   expected number of decoded bytes <em>plainSize</em>.
     */
    override def decode(data:Traversable[Byte], enc: Codec.Value, codeSize: Long): Try[Stream[Byte]] =
        percentDecode(data, chars.keySet) flatMap ( super.decode(_,enc,codeSize) )

}
