fun main() {
    var f: String? = null
//    var f: String? = "abcd"

    val g = f ?: "f was empty"
    println(g)

    val length = f?.length ?: -1
    println(length)
}
