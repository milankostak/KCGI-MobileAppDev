fun main() {
    val numbers1 = setOf(1, 2, 3, 4)
    println("Number of elements: ${numbers1.size}")
    if (numbers1.contains(1)) println("1 is in the set")
    // https://kotlinlang.org/docs/collections-overview.html#set

    // LinkedHashSet is the default implementation and it preserves order

    val numbers2 = mutableSetOf(1, 2, 3, 45, 67, 16, 17, 18)
    numbers2.add(5) // added to the last position
    println(numbers2) // [1, 2, 3, 45, 67, 16, 17, 18, 5]

    // HashSet does not preserve order
    val numbers3 = hashSetOf(1, 2, 3, 45, 67, 16, 17, 18)
    println(numbers3) // [16, 1, 17, 2, 18, 3, 67, 45]
    numbers3.add(123) // MIGHT NOT be added at the last position, it is not predictable
    println(numbers3) // [16, 1, 17, 2, 18, 3, 67, 123, 45]
}
