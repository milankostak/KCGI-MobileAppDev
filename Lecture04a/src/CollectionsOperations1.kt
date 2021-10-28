fun main() {
    val numbers1 = listOf("one", "two", "three", "four")
    numbers1.filter { it.length > 3 }  // nothing happens with `numbers1`, result is lost
    println("numbers are still $numbers1")

//    val longerThan3 = numbers1.filter { s -> s.length > 3 }
    val longerThan3 = numbers1.filter { it.length > 3 } // result is stored in `longerThan3`
    println("numbers longer than 3 chars are $longerThan3")
    // https://kotlinlang.org/docs/collection-operations.html#common-operations
}
