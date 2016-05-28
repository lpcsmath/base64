package de.csmath.codec



trait Padding {
    def addPads(n: Int, needPad: Boolean): Seq[Byte]
}

trait CanonicalPadding extends Padding {

    /** A sequence of pad symbols.
     *  @param n       The number of needed pad symbols
     *  @param needPad true iff pads are needed.
     */
    override def addPads(n: Int, needPad: Boolean) =
        if (needPad) Seq.tabulate(n)( x => '='.toByte)
        else Seq()

}

trait UrlPadding extends Padding {

    override def addPads(n: Int, needPad: Boolean) =
        if (!needPad) Seq()
        else
            Seq.tabulate(n)( x => Seq() ++ "%3D".getBytes ) flatten

}
