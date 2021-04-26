fun main() {
    // smart cast
    val string: String? = "Kotlin"
    if (string != null && string.isNotEmpty()) {
        println("String of length ${string.length}") // b is smart-casted to String type (without ?)
    } else {
        println("Null or empty string")
    }
    // https://kotlinlang.org/docs/null-safety.html#safe-calls


    // safe calls
    // this is an overkill but to demonstrate...
    val null3 = Null3(Null2(Null1("hello")))

    // null3.n3.n2.n1.length // not possible, must be checked
    val result = null3.n3?.n2?.n1?.length ?: -1 // if any is null then return -1
    println(result)
}

// ignore the concept of data classes for now
// consider them a normal class without any body
data class Null1(val n1: String?)
data class Null2(val n2: Null1?)
data class Null3(val n3: Null2?)
