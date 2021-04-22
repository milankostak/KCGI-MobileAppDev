fun main() {
    val ben = Dog(3, "Ben")
    println(ben.name)
    println(ben.getInfo())
    ben.incrementAge()
    println(ben.getInfo())

    val rex = Dog(7, "Rex")
    println(rex.name)
}
