fun main() {
    var a = "hello"
//    a = null // a cannot be assigned null
//    a = getNullString()

    var b: String? = "hello"
//    b = null // b is of type String? and it can be null
//    b = getNullString()

    println(a.length) // ok to do that
//    println(b.length) // NOT ok to do that, compilation error
    // necessary to check
    println(b?.length) // if b is null then it does not ask for its length
    println(b!!.length) // DANGEROUS operation, if b is null then exception is thrown
}

fun getNullString(): String? {
    return null
}
