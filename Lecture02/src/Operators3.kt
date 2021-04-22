fun main() {
    val a = 5
    val b = 6

    println(a == b) // false
    println(a != b) // true

    val d1 = Dog(1, "A")
    val d2 = Dog(1, "A")

    println(d1 == d2) // requires Dog to have "equals" function to work properly
}
