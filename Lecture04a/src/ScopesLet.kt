fun main() {
    val numbers1 = mutableListOf("one", "two", "three", "four", "five")
    val resultList = numbers1.map { it.length }.filter { it > 3 }
    println("printing without let")
    println(resultList)

    val numbers2 = mutableListOf("one", "two", "three", "four", "five")
    val result1 = numbers2.map { it.length }.filter { it > 3 }.let {
        println("printing inside let (1)")
        println(it)
        // and more function calls if needed
    }
    // does not return anything
    // https://kotlinlang.org/docs/scope-functions.html#let


    val result2 = numbers2
            .map { it.length }
            .filter { it > 3 }
            .let {
                println("printing inside let (2)")
                println(it)
                it
            }
    // this example also returns the resulting list

    numbers2.lastOrNull { it == "seven" }?.let {
        // if any string was returned then print it
        println("printing inside let (3)")
        println(it)
    }
}
