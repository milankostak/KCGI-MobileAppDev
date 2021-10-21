fun main() {
    val numbers1 = setOf(1, 12, 15, 17, 25)
    println("Number of elements: ${numbers1.size}")
    if (numbers1.contains(1)) println("1 is in the set")
    // https://kotlinlang.org/docs/collections-overview.html#set

    println(numbers1) // [1, 12, 15, 17, 25]
    // LinkedHashSet is the default implementation, and it preserves order
    println()

    val numbers2 = mutableSetOf(1, 12, 15, 17, 25)
    numbers2.add(5) // added to the last position
    numbers2.add(15) // does not add it (it would be a duplicate)
    println(numbers2) // [1, 12, 15, 17, 25, 5]
    println()

    // HashSet does not preserve order
    val numbers3 = hashSetOf(1, 12, 15, 17, 25)
    println(numbers3) // [1, 17, 25, 12, 15] - different order!
    numbers3.add(25) // is not added
    numbers3.add(5) // MIGHT NOT be added at the last position, it is not predictable
    println(numbers3) // [1, 17, 25, 12, 5, 15]
}
