fun main() {
    val numbers = mutableListOf("one", "two", "three")
    val r = numbers.also { println("The list elements before adding new one: $it") }
        .add("four")
        .also { println(it) }
    println(numbers)
    // https://kotlinlang.org/docs/scope-functions.html#also
}
