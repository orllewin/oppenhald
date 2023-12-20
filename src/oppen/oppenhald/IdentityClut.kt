package oppen.oppenhald

import kotlin.math.pow
import kotlin.math.roundToInt

class IdentityClut(private val image: OppenImage){

    private var level: Int = 0
    private var cubeSize: Int = 0

    init {
        if(image.width == image.height){
            level = cubicRoot(image.width)
            cubeSize = level * level
        }else{
            throw ArithmeticException("HaldCLUT Image must be square")
        }
    }

    fun process(){
        val rows = level * level * level
        var y = 0
        while(y < rows){
            val b = y / level
            var x = 0
            for (g in 0 until cubeSize) {
                for (r in 0 until cubeSize) {
                    val red = 255 * r / (cubeSize - 1)
                    val green = 255 * g / (cubeSize - 1)
                    val blue = 255 * b / (cubeSize - 1)
                    val rgb = rgb(red, green, blue)
                    image.setPixel(x, y, rgb)
                    x++
                    if (x==rows) {
                        x = 0
                        y++
                    }
                }
            }
        }
    }

    private fun rgb(red: Int, green: Int, blue: Int): Int {
        val r = red shl 16 and 0x00FF0000
        val g = green shl 8 and 0x0000FF00
        val b = blue and 0x000000FF
        return -0x1000000 or r or g or b
    }

    private fun cubicRoot(edgeSize:Int):Int {
        return edgeSize.toDouble().pow(1/3.toDouble()).roundToInt()
    }
}