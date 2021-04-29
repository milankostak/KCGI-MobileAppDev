fun main() {
    val a = 5
    val b = 8

    var max1 = a
    if (a < b) max1 = b
    println(max1)

    // With else
    val max2: Int
    if (a > b) {
        max2 = a
    } else {
        max2 = b
    }
    println(max2)

    // As expression
    val max3 = if (a > b) a else b
    println(max3)

    // cannot use ternary operator - does not exist in Kotlin
//    val max4 = (a < b) ? b : a

    // https://kotlinlang.org/docs/control-flow.html#if-expression
}
