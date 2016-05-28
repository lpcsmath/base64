package de.csmath.codec



trait Padding {
    def addPads(n: Int, needPad: Boolean): Seq[Byte]
}

trait CanonicalPadding extends Padding {

    /** A sequence of pad symbols.
     *  @param n       The number of needed pad symbols
     *  @param needPad true iff pads are needed.
     *  @return        A sequence of type Byte with the ASCII code of '='.
     */
    override def addPads(n: Int, needPad: Boolean) =
        if (needPad) Seq.tabulate(n)( x => '='.toByte)
        else Seq()

}

trait UrlPadding extends Padding {

    /** A sequence of URL save pad symbols.
     *  @param n       The number of needed pad symbols
     *  @param needPad true iff pads are needed.
     *  @return        A sequence of type Byte with the ASCII codes of "%3D".
     */
    override def addPads(n: Int, needPad: Boolean) =
        if (!needPad) Seq()
        else
            Seq.tabulate(n)( x => Seq() ++ "%3D".getBytes ) flatten

}
