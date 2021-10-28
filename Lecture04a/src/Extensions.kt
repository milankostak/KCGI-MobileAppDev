fun List<String>.getLongestString(): String {
    return this.maxByOrNull { it.length } ?: ""
}

fun Int?.addFive(): Int {
    return this?.plus(5) ?: 5
}

fun main() {
    val list = listOf("red", "green", "blue")
    val longestString = list.getLongestString()
    println(longestString)

    val number = 17
//    val number: Int? = null
    println(number.addFive())
    println(number)
    println(number.addFive().addFive().addFive())
}
