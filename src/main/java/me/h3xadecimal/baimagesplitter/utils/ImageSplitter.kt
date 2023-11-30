package me.h3xadecimal.baimagesplitter.utils

import org.apache.logging.log4j.LogManager
import java.awt.image.BufferedImage
import kotlin.math.max
import kotlin.math.min

object ImageSplitter {
    @JvmStatic private val logger = LogManager.getLogger("Splitter")

    @JvmStatic
    fun splitSimple(img: BufferedImage, count: Int): Array<BufferedImage> {
        var current = 0
        var size = img.height / count

        val images = mutableListOf<BufferedImage>()

        logger.info("Source Image: ${img.width}*${img.height}")
        logger.info("Height per subImage: $size")
        while (current <= img.height) {
            val y = min(img.height, current)
            val height= if (current+size > img.height) {
                logger.info("Next Y value is bigger than height")
                img.height-current
            } else size

            logger.info("Splitting from (0,$y) to (${img.width}, ${current+height}), height=$height")
            images.add(img.getSubimage(0, y, img.width, height))
            current += size
        }

        return images.toTypedArray()
    }

    @JvmStatic
    fun splitManual(img: BufferedImage, points: List<Int>): Array<BufferedImage> {
        var current = 0
        var images = mutableListOf<BufferedImage>()

        logger.info("Source Image: ${img.width}*${img.height}")
        for (p in points) {
            val y = min(img.height, current)
//            val height= if (current+p > img.height) {
//                logger.info("Next Y value is bigger than height")
//                img.height-current
//            } else p-current
            val height = p-current

            logger.info("Splitting from (0,$y) to (${img.width}, ${current+height}), height=$height")
            images.add(img.getSubimage(0, y, img.width, height))
            current = p
        }

        return images.toTypedArray()
    }
}