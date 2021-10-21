fun main() {
    val numbers1 = listOf("one", "two", "three", "four")
    println("Number of elements: ${numbers1.size}")
    println("Third element: ${numbers1.get(2)}")
    println("Fourth element: ${numbers1[3]}")
    println("Index of element \"two\" is ${numbers1.indexOf("two")}")
//    numbers1.add("five") // cannot add elements, the list is immutable
//    numbers1[0] = "zero" // cannot change any element
    println()

    val numbers2 = mutableListOf(1, 2, 3, 4)
    println(numbers2) // [1, 2, 3, 4]
    numbers2.add(5)
    println(numbers2) // [1, 2, 3, 4, 5]
    numbers2.removeAt(1)
    println(numbers2) // [1, 3, 4, 5]
    numbers2[0] = 0
    println(numbers2) // [0, 3, 4, 5]
    // https://kotlinlang.org/docs/collections-overview.html#list
}
