package me.h3xadecimal.baimagesplitter.utils

import org.apache.logging.log4j.LogManager
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics
import java.awt.Image
import java.awt.Point
import java.awt.image.BufferedImage
import java.awt.image.ImageObserver
import java.io.ByteArrayOutputStream
import java.util.Random
import javax.imageio.ImageIO
import kotlin.math.log
import kotlin.math.min

object ImageProcessor {
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

    @JvmStatic
    fun createWaterMark(content: BufferedImage, gap: Int, img: BufferedImage): BufferedImage {
        logger.info("构建图形 ${img.width} * ${img.height}")
        val graphics = img.createGraphics()

        val scale: Double = content.width.toDouble()/img.width.toDouble()
        logger.info("缩放水印图像 $scale (${content.width} / ${img.width})")
        val scaleWidth = (content.width*scale).toInt()
        val scaled = content.getScaledInstance(scaleWidth, (content.height*scale).toInt(), Image.SCALE_DEFAULT)

        val locations = mutableListOf<Point>()
        var current = 0
        while (current < img.height) {
            current += scaleWidth + 100
            if (current > img.height) break
            locations.add(Point(0, current))
        }
        logger.info("已创建 ${locations.size} 个水印点")

        for (l in locations) {
            logger.info("绘制水印 (${l.x}, ${l.y})")
            graphics.drawImage(scaled, l.x, l.y
            ) { img, infoflags, x, y, width, height ->
                false
            }
        }

        graphics.dispose()
        logger.info("绘制完成")
        return img
    }

    @JvmStatic
    fun rdColor(): Color {
        val rd = Random()
        return Color(rd.nextInt(255), rd.nextInt(255), rd.nextInt(255), rd.nextInt(255))
    }

    @JvmStatic
    fun createObfuscation(img: BufferedImage) {
        val count = img.height/30
        val gr = img.createGraphics()

        for (i in 0 until count) {
            val rd = Random()
            val rd2 = Random()
            val rd3 = Random()

            val x1 = rd.nextInt(0, img.width)
            val x2 = rd2.nextInt(0, img.width)
            val y1 = rd.nextInt(0, img.height)
            val y2 = rd.nextInt(0, img.height)

            val x3 = rd3.nextInt(img.width)
            val y3 = rd3.nextInt(img.height)

            gr.color = rdColor()
            gr.stroke = BasicStroke(rd.nextFloat(10f))
            gr.drawLine(x1, x2, y1, y2)
            gr.drawLine(x3, y3, x3, y3)
        }
    }
}