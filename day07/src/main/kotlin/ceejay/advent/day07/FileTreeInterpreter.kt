package ceejay.advent.day07

class FileTreeInterpreter {
    private val root: Directory = Directory(name = "/", parent = null)
    private var currentDirectory = root

    fun interpret(source: String): Directory {
        val lines = source.lineSequence()
            .filter { it.isNotBlank() }
            .toCollection(ArrayDeque())

        while (lines.isNotEmpty()) {
            val line = lines.removeFirst()

            require(line.startsWith("$")) { "Current line is not a command: $line" }

            val command = line.substringAfter("$ ")

            if (command.startsWith("ls")) {
                consumeList(lines)
            } else if (command.startsWith("cd")) {
                changeDirectory(command.substringAfter("cd "))
            } else {
                throw IllegalArgumentException("cannot interpret command: $line")
            }
        }
        return root
    }

    private fun changeDirectory(target: String) {
        currentDirectory = when (target) {
            "/" -> root
            ".." -> currentDirectory.parent
                ?: throw IllegalStateException("current directory $currentDirectory has no parent")

            else -> getOrCreateDirectory(target)
        }
    }

    private fun getOrCreateDirectory(target: String): Directory {
        return currentDirectory.children.getOrElse(target) {
            Directory(
                name = target,
                parent = currentDirectory
            )
        }
    }

    private fun consumeList(lines: MutableList<String>) {
        var current = lines.firstOrNull()
        while (current != null && !current.startsWith("$")) {
            lines.removeFirst()
            if (current.startsWith("dir")) {
                consumeDir(current)
            } else {
                consumeFile(current)
            }
            current = lines.firstOrNull()
        }
    }

    private fun consumeFile(item: String) {
        val size = item.trim().substringBefore(" ")
        currentDirectory.addFileSized(size.toInt())
    }

    private fun consumeDir(item: String) {
        val name = item.substringAfter("dir ")
        if (name !in currentDirectory.children) {
            currentDirectory.children[name] = Directory(name = name, parent = currentDirectory)
        }
    }
}