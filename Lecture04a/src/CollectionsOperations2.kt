fun main() {
    val numbers2 = listOf(1, 2, 3, 4, 5)
    val numbersMultiplied = numbers2.map { it * 2 }
    println(numbersMultiplied) // [2, 4, 6, 8, 10]

    val allMultiplied = numbers2.reduce { n1, n2 -> n1 * n2 } // result is Int
    println(allMultiplied) // 120

    println(numbers2.reversed())
    println(numbers2.shuffled())
    println(numbers2.shuffled().sorted())
    println(numbers2.minOrNull() ?: -1) // minOrNull can return null -> let's use elvis operator
    println(numbers2.maxOrNull() ?: -1)
    println(numbers2.average())

    // more complicated chaining
    val result = numbers2
            .map { it + 1 }
            .filter { it > 3 }
            .reversed()
            .map { it + 10 }
            .joinToString(separator = "|")
    println(result) // 16|15|14
}
