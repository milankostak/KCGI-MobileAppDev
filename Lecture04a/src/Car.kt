data class Car(
    val brand: String,
    val color: String,
    val year: Int,
    var odometer: Double = 0.0, // default is a new car
    val electric: Boolean = false
)
