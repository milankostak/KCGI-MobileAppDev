fun main() {
    val collection = listOf(1, 3, 8, 12, 14, 16)

    for (item in collection) println(item)

    for (i in collection.indices) {
        println("$i - ${collection[i]}")
    }

    // https://kotlinlang.org/docs/control-flow.html#for-loops
}
