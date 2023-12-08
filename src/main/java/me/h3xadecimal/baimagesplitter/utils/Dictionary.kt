package me.h3xadecimal.baimagesplitter.utils

import kotlin.random.Random

object Dictionary {
    @JvmStatic
    private val dict = mutableListOf<Char>()

    init {
        for (i in 0x0000 until 0x007F) dict.add(i.toChar()) // 基本拉丁字母
        for (i in 0x31C0 until 0x31EF) dict.add(i.toChar()) // 中日韩笔画
        for (i in 0xF900 until 0xFAFF) dict.add(i.toChar()) // 中日韩兼容表意文字
    }

    @JvmStatic
    fun randomChar() = dict[Random.nextInt(dict.size)]

    @JvmStatic
    fun randomString(length: Int): String {
        val str = StringBuffer()
        for (i in 0 until length) str.append(randomChar())
        return str.toString()
    }
}