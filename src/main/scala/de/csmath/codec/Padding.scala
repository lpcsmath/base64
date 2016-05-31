package de.csmath.codec


/**
 *  This trait provides a helper function to pad a seqence of bytes to a
 *  given size with appropriate pad symbols.
 */
trait Padding {
    def addPads(n: Int, needPad: Boolean): Seq[Byte]
}


/**
 *  This trait provides a helper function to pad a seqence of bytes to a
 *  given size with the pad symbols '='.
 */
trait CanonicalPadding extends Padding {

    /**
     *  Creates sequence of pad symbols.
     *  @param n       The number of needed pad symbols
     *  @param needPad <em>true</em> iff pads are needed.
     *  @return        A sequence of type <em>Byte</em> with the ASCII code of
     *                 the character '='.
     */
    override def addPads(n: Int, needPad: Boolean) =
        if (needPad) Seq() padTo (n, '='.toByte)
        else Seq()

}


/**
 *  This trait provides a helper function to pad a seqence of bytes to a
 *  given size with the percent encoded pad symbols '=' resulting in the byte
 *  codes of the string "%3D".
 */
trait UrlPadding extends Padding {

    /**
     *  Creates a sequence of <em>URL save</em> pad symbols.
     *  @param n       The number of needed pad symbols
     *  @param needPad <em>true</em> iff pads are needed.
     *  @return        A sequence of type <em>Byte</em> with the ASCII codes of
     *                 the string "%3D".
     */
    override def addPads(n: Int, needPad: Boolean) =
        if (!needPad) Seq()
        else Seq() padTo (n, Seq() ++ "%3D".getBytes ) flatten

}
