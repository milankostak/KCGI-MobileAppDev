fun main() {
    var f: String? = null
//    var f: String? = "abcd"

    val g = f ?: "f is null"
    println(g)

    val length = f?.length ?: -1
    println(length)
}
