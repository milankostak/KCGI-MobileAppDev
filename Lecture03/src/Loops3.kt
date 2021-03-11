fun main() {
    var x = 5

    while (x > 0) {
        println(x)
        x--
    }
    println()

    do {
        val y = Math.random()
        println(y)
    } while (y < 0.8) // y is visible here
}
