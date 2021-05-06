fun main() {
    val str = "Hello"
    // this
    str.run {
        println("The receiver string length: $length")
//        println("The receiver string length: ${this.length}") // does the same
    }

    // it
    str.let {
        println("The receiver string's length is ${it.length}")
    }
    // https://kotlinlang.org/docs/scope-functions.html#context-object-this-or-it


    // Context object
    val numberList = mutableListOf<Double>()
    numberList.also { println("Populating the list") }
            .apply {
                add(2.71)
                add(3.14)
                add(1.0)
            }
            .also { println("Sorting the list") }
            .sort()

    // Lambda result
    val numbers = mutableListOf("one", "two", "three")
    val countEndsWithE = numbers.run {
        add("four")
        add("five")
        count { it.endsWith("e") }
    }
    println("There are $countEndsWithE elements that end with e.")
    // https://kotlinlang.org/docs/scope-functions.html#return-value
}