package ceejay.advent.util

interface Debuggable {
    val debugEnabled: Boolean

    fun debug(message: String = "") {
        if (debugEnabled) {
            println(message)
        }
    }

    fun debug(lazyMessage: () -> String) {
        if (debugEnabled) {
            println(lazyMessage())
        }
    }
}