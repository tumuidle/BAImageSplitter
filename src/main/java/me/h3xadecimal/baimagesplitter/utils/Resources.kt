package me.h3xadecimal.baimagesplitter.utils

import me.h3xadecimal.baimagesplitter.Main
import java.awt.image.BufferedImage
import java.io.InputStream
import javax.imageio.ImageIO

object Resources {
    @JvmStatic val icon: BufferedImage

    @JvmStatic
    fun getResource(name: String): InputStream = Main::class.java.classLoader.getResourceAsStream(name)

    init {
        icon = ImageIO.read(getResource("icon.png"))
    }
}