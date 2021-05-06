fun main() {
    val numbers = mutableListOf("one", "two", "three", "four")
    val sortedNumbers = numbers.sorted()
    println(sortedNumbers)
    println(numbers == sortedNumbers)  // false

    numbers.sort()
    println(numbers)
    println(numbers == sortedNumbers)  // true
}
