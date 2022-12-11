package ceejay.advent.day07


data class Directory(
    val name: String,
    val parent: Directory?,
    val children: MutableMap<String, Directory> = mutableMapOf(),
) {
    private var fileSize: Int = 0

    operator fun contains(directoryName: String): Boolean {
        if (directoryName == name) {
            return true
        }

        return directoryName in children
                || children.values.any { directoryName in it }
    }

    fun totalSize(): Int {
        return fileSize + (children.values.sumOf { it.totalSize() })
    }

    fun addFileSized(size: Int) {
        fileSize += size
    }

    override fun toString(): String {
        return "- $name (total size: ${totalSize()})" + if (children.isNotEmpty()) {
            "\n" + children.values
                .joinToString("\n")
                .prependIndent("  ")
        } else {
            ""
        }
    }
}