package me.h3xadecimal.baimagesplitter.utils.parts

enum class EnumImagePart(val displayName: String, val length: Int, val afterLength: Int, val canSplitAt: Boolean=false) {
    // region:MoeTalk
    MOE_HEADER("MoeTalk顶栏", 185, 65),
    MOE_VOICEOVER_SINGLE("MoeTalk单行旁白", 60, 33, true),
    MOE_SINGLE_MESSAGE_HEADER("MoeTalk单行顶部", 155, 15),
    MOE_IMAGE("MoeTalk差分图像", 500, 56, true),
    MOE_MESSAGE_HEADER("MoeTalk单行消息顶部", 28, 0),
    MOE_SL_MESSAGE("MoeTalk单行消息文字", 40, 15),
    MOE_MESSAGE_FOOTER("MoeTalk消息底部", 29, 15)
    // endregion
    ;

    companion object {
        @JvmStatic
        fun forName(name: String): EnumImagePart? {
            for (p in entries) {
                if (p.displayName.equals(name, true)) return p
            }
            return null
        }
    }
}