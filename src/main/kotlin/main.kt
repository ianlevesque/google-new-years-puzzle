fun main(args: Array<String>) {
    val input = "endrtednedd:/os....cp.rnnn.rhhps/.tt\$sfeaiaaofd.ow.otooapa.asu./thhse"
//    val input = "nr\$aaagm"

    println("Solution: " + decrypt(input))
}

fun decrypt(input: String): String {
    val size = input.length
    val sortTable = Array(size) { CharArray(size) } // y,x arrangement

    // copy values into the last column
    input.forEachIndexed { index, char -> sortTable[index][size - 1] = char }

    for(solvingColumn in 0 until size) {
        println("$solvingColumn ==============")
        printTable(sortTable)

        // rotate it and sort again
        val rotated = sortTable.deepClone();
        rotated.forEach { row -> rotateArray(row) }
        printTable(rotated)
        val sorted = rotated.sortedBy { row -> row.concatToString().replace("\$", "\u007F") }.toTypedArray()
        printTable(sorted)

        sortTable.forEachIndexed { index, _ ->
            sortTable[index][solvingColumn] = sorted[index][solvingColumn]
        }
        printTable(sortTable)
    }

    return sortTable.last().concatToString().drop(1)
}

private fun Array<CharArray>.deepClone(): Array<CharArray> {
    return Array(this.size) { index -> this[index].clone() }
}

fun printTable(sortTable: Array<CharArray>) {
    sortTable.forEach { row -> println(row.concatToString().replace("\u0000", " ")) }
    println("-".repeat(sortTable[0].size))
}

fun rotateArray(array: CharArray) {
    val last = array.last()
    for(i in (array.size - 1).downTo(1)) {
        array[i] = array[i - 1]
    }
    array[0] = last
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
