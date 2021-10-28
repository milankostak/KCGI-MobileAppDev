fun main() {
    var string: String? = "test"
//    var string: String? = null

    val length = string?.run {
        println("getting the length of '$this' with run")
        length
    } ?: 0
    println(length)

    val string2 = string?.apply {
        println("getting the length of '$this' with apply")
        length // does not make sense here
    } ?: ""
    println(string2)
}
