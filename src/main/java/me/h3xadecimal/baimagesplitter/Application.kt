package me.h3xadecimal.baimagesplitter

import com.formdev.flatlaf.intellijthemes.FlatArcDarkIJTheme
import me.h3xadecimal.baimagesplitter.ui.windows.UiMain
import me.h3xadecimal.baimagesplitter.utils.GitUtils
import me.h3xadecimal.baimagesplitter.utils.StackUtils
import org.apache.logging.log4j.LogManager
import java.io.File
import kotlin.system.exitProcess

object Application {
    val logger = LogManager.getLogger("Main")
    val runningDir = File(System.getProperty("user.dir"))

    lateinit var uiMain: UiMain
        private set

    @JvmStatic
    fun run() {
        try {
            logger.info("BAImageSplitter ${GitUtils.getFullInfo()}")

            logger.info("载入界面中")
            FlatArcDarkIJTheme.setup()
            uiMain = UiMain()
            uiMain.isVisible = true
        } catch (t: Throwable) {
            logger.error("初始化出错", t)
            exit(1)
        }
    }

    fun exit(code: Int) {
        logger.info("${StackUtils.walker.callerClass.name} 正在退出程序，代码为 $code")
        exitProcess(code)
    }
}