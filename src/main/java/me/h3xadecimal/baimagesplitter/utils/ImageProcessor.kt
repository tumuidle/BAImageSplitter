package me.h3xadecimal.baimagesplitter.utils

import org.apache.logging.log4j.LogManager
import java.awt.AlphaComposite
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Font
import java.awt.Image
import java.awt.Point
import java.awt.image.BufferedImage
import java.util.Random
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

        val oComp = graphics.composite
        graphics.composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)

        for (l in locations) {
            logger.info("绘制水印 (${l.x}, ${l.y})")
            graphics.drawImage(scaled, l.x, l.y
            ) { img, infoflags, x, y, width, height ->
                false
            }
        }

        graphics.composite = oComp
        graphics.dispose()
        logger.info("绘制完成")
        return img
    }

    @JvmStatic
    fun rdColor(): Color {
        val rd = Random()
        return rdColor(rd.nextInt(255))
    }

    @JvmStatic
    fun rdColor(alpha: Int): Color {
        val rd = Random()
        return Color(rd.nextInt(255), rd.nextInt(255), rd.nextInt(255), alpha)
    }

    @JvmStatic
    fun createRandomLineObfuscation(img: BufferedImage) {
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
            gr.stroke = BasicStroke(rd.nextFloat(5f))
            gr.drawLine(x1, x2, y1, y2)
            gr.drawLine(x3, y3, x3, y3)
        }
    }

    @JvmStatic
    fun createDotObfuscation(img: BufferedImage) {
        val gr = img.createGraphics()
        gr.stroke = BasicStroke(1f)

        for (x in 0 until img.width) {
            for (y in 0 until img.height) {
                if (y % 2 == 0 && x % 2 == 0) {
                    gr.color = rdColor(255)
                    gr.drawLine(x, y, x, y)
                }
            }
        }

        gr.dispose()
    }

    @JvmStatic
    fun createUnicodeObfuscation(img: BufferedImage) {
        val gr = img.createGraphics()
        val font = Font("Arial", Font.PLAIN, 12)

        gr.font = font

        var currentY = gr.fontMetrics.height
        var currentX = 0

        while (currentY < img.height) {
            logger.info("Drawing on Y=$currentY")
            while (currentX < img.width) {
                val ch = Dictionary.randomChar()
                gr.color = rdColor(200)
                gr.drawString(ch.toString(), currentX, currentY)
                currentX += gr.fontMetrics.charWidth(ch)
            }
            currentX = 0
            currentY += gr.fontMetrics.height
        }
    }
}