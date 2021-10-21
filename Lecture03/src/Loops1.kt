fun main() {
    println("1..3")
    for (i in 1..3) {
        print(i) // 1 2 3
    }
    println("\n")

    println("1 until 3")
    for (i in 1 until 3) {
        print(i) // 1 2
    }
    println("\n")

    println("6 downTo 0 step 2")
    for (i in 6 downTo 0 step 2) {
        print(i) // 6 4 2 0
    }

    // https://kotlinlang.org/docs/control-flow.html#for-loops
}
