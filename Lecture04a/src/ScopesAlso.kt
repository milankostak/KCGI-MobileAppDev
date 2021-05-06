fun main() {
    val numbers = mutableListOf("one", "two", "three")
    numbers.also { println("The list elements before adding new one: $it") }
           .add("four")
    println(numbers)
    // https://kotlinlang.org/docs/scope-functions.html#also
}
