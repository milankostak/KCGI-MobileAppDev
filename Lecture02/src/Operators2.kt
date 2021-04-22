fun main() {
    var a = 5
    println(a++) // 5
    println(a) // 6
    println(++a) // 7
    println(a) // 7

    println()

    var b = 4
    println(b--) // 4
    println(b) // 3
    println(--b) // 2
    println(b) // 2

    println()

    var c = 3
    val d = ++c * c++
    println(d) // ?
    println(c)
    var e = 3
    val f = e++ * ++e
    println(f) // ?
    println(e)
}
