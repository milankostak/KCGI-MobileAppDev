import kotlin.Float.Companion.NaN

fun main() {
    val numbers2 = listOf(1, 2, 3, 4, 5)
    val numbersMultiplied = numbers2.map { it * 2 }
    println(numbersMultiplied) // [2, 4, 6, 8, 10]

    val allMultiplied = numbers2.reduce { n1, n2 -> n1 * n2 } // result is Int
    println(allMultiplied) // 120

    println("reversed")
    println(numbers2.reversed())
    println("shuffled")
    println(numbers2.shuffled())
    println("shuffled sorted")
    println(numbers2.shuffled().sorted())
    println("minOrNull")
    println(numbers2.minOrNull() ?: NaN) // minOrNull can return null -> let's use elvis operator
    println("maxOrNull")
    println(numbers2.filter { it < 0 }.maxOrNull() ?: NaN)
    println("average")
    println(numbers2.average())

    // more complicated chaining
    val result = numbers2
            .map { it + 1 } // 2, 3, 4, 5, 6
            .filter { it > 3 } // 4, 5, 6
            .reversed() // 6, 5, 4
            .map { it + 10 } // 16, 15, 14
            .joinToString(separator = "|")
    println(result) // 16|15|14
}
