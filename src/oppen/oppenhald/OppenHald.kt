package oppen.oppenhald

import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB
import java.io.File
import java.io.FileNotFoundException
import javax.imageio.ImageIO
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    if(args.isEmpty()) {
        println("Enter a HaldCLUT level to generate an identity HaldCLUT, eg. OppenHald 4")
        println("or pass a HaldCLUT image and a target image to apply a filter, eg. OppenHald HaldClut4.png SomeImage.png")
        exitProcess(0)
    }
    if(args.size == 1){
         val arg = args[0]
        try {
            val level = Integer.parseInt(arg)
            OppenHald().generateIdentityCLUT(level)
        }catch (nfe: NumberFormatException){
            println("Enter a HaldCLUT level to generate an identity HaldCLUT, eg. FargRutnat 4")
            exitProcess(-1)
        }
    }else if(args.size == 2){
        val haldUri = args[0]
        val imageUri = args[1]
        OppenHald().applyHald(haldUri, imageUri)
    }
}

class OppenHald {

    fun applyHald(haldUri: String, imageUri: String){
        val haldFile = File(haldUri)
        val inputFile = File(imageUri)

        if(haldFile.exists()){
            if(inputFile.exists()){
                val haldImage = ImageIO.read(haldFile)
                val inputImage = ImageIO.read(inputFile)

                val oHaldImage = OppenImageImplem(haldImage)
                val oInputImage = OppenImageImplem(inputImage)

                HaldClut(oHaldImage, oInputImage).process()

                val outName = "${inputFile.nameWithoutExtension}_${haldFile.nameWithoutExtension}.png"

                println("Saving to $outName")
                val processedFile = File(inputFile.path.substring(0, inputFile.path.lastIndexOf("/") + 1),outName)
                ImageIO.write(oInputImage.image, "png", processedFile)
            }else{
                throw FileNotFoundException("Input image ${inputFile.name} does not exist")
            }
        }else{
            throw FileNotFoundException("HaldCLUT image ${haldFile.name} does not exist")
        }
    }

    fun generateIdentityCLUT(level: Int){
        val sideSize = level * level * level
        val target = BufferedImage(sideSize, sideSize, TYPE_INT_RGB)
        val oppenImage = OppenImageImplem(target)

        IdentityClut(oppenImage).process()

        val identityFile = File("IdentityHaldCLUT_Level$level.png")
        ImageIO.write(oppenImage.image, "png", identityFile)
    }

    class OppenImageImplem(val image: BufferedImage): OppenImage() {

        override var width: Int
            get() = image.width

            @Suppress("UNUSED_PARAMETER")
            set(value) {
                //unused'
            }

        override var height: Int
            get() = image.height

            @Suppress("UNUSED_PARAMETER")
            set(value) {
                //unused
            }

        override fun getPixel(x: Int, y: Int): Int {
            return image.getRGB(x, y)
        }

        override fun setPixel(x: Int, y: Int, colour: Int) {
            image.setRGB(x, y, colour)
        }
    }
}