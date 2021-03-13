fun main() {
    var string: String? = "test"
//    var string: String? = null

    val length = string?.run {
        println("get length of $this")
        length
    } ?: 0
    println(length)

    val string2 = string?.apply {
        println("get length of $this")
        length // does not makes sense here
    } ?: ""
    println(string2)
}
