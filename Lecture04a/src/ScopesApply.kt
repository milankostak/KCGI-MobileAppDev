fun main() {
    val car = Car("Peugeot", "green", 2014).apply {
        odometer = 1245.0
    }
    println(car)

    val car2 = Car("Peugeot", "green", 2014).also {
        it.odometer = 1245.0
    }
    println(car2)
}
