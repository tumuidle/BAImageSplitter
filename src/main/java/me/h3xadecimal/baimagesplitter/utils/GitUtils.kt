package me.h3xadecimal.baimagesplitter.utils

import me.h3xadecimal.baimagesplitter.Main
import java.util.*

object GitUtils {
    val prop = Properties()

    fun getBranch() = prop.getProperty("git.branch")

    fun getAbbrev() = prop.getProperty("git.commit.abbrev")

    fun getVersion() = prop.getProperty("git.build.version")

    fun getFullInfo() = "${getVersion()} git-${getBranch()}-${getAbbrev()}"

    init {
        prop.load(Main::class.java.classLoader.getResourceAsStream("git.properties"))
    }
}