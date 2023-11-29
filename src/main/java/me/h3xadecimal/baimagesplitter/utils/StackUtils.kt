package me.h3xadecimal.baimagesplitter.utils

object StackUtils {
    @JvmStatic val walker = StackWalker.getInstance(
        setOf(
            StackWalker.Option.RETAIN_CLASS_REFERENCE,
            StackWalker.Option.SHOW_REFLECT_FRAMES
        )
    )

    fun getCaller() {

    }
}