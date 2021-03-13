fun main() {
    val car = Car("Peugeot", "green", 2014).apply {
        odometer = 1245.0
    }
    println(car)
}
