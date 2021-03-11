fun main() {
    val x = 5
    val y = -1

    if (x > 0 && y > 0) { // AND
        println("both positive")
    } else {
        println("at least one is negative or zero")
    }

    if (x > 0 || y > 0) { // OR
        println("at least one is positive")
    }

    if (!(x > 0)) { // NOT
        println("x is negative or zero")
    }
}
