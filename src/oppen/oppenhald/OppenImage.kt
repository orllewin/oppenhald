package oppen.oppenhald

abstract class OppenImage {
    abstract var width: Int
    abstract var height: Int

    abstract fun getPixel(x: Int, y: Int): Int
    abstract fun setPixel(x: Int, y: Int, colour: Int)
}