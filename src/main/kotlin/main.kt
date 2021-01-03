fun main(args: Array<String>) {
    val input = "endrtednedd:/os....cp.rnnn.rhhps/.tt\$sfeaiaaofd.ow.otooapa.asu./thhse"
//    val input = "nr\$aaagm"

    println("Solution: " + crackPassword(input))
}

fun crackPassword(input: String) = testString("", input.replace("\$", ""), input)

fun testString(assembled: String, remaining: String, required: String): String? {
    val tested = mutableSetOf<Char>()

    if (remaining.isEmpty()) {
//        println("Testing: $assembled")
        return if (required == encrypt(assembled)) assembled else null
    }

    var index = 0
    for (c in remaining) {
        if (!tested.contains(c)) {
            val solution =
                testString(assembled + c, remaining.removeRange(index, index + 1), required)
            if (solution != null) {
                return solution
            }
        } else {
            tested.add(c)
        }

        index++
    }

    return null
}

fun encrypt(input: String): String {
    val initial = "$input$"
    val rotations = rotationsOf(initial)
    return sorted(rotations)
        .map { s -> s.last() }
        .joinToString("")
}

fun sorted(rotations: Array<String>) = rotations
    .sortedWith { s1, s2 -> s1.replace("\$", "\u007F").compareTo(s2) }
    .toTypedArray()

fun rotationsOf(initial: String): Array<String> {
    val rotations = mutableListOf<String>()
    var current = initial
    do {
        rotations.add(current)
        current = current.drop(1) + current.first()
    } while (current != initial)

    return rotations.toTypedArray()
}
