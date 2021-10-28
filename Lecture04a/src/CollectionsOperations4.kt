fun main() {
    val cars = mutableListOf<Car>()
    cars.add(Car("Toyota", "white", 2021, electric = true))
    cars.add(Car("Ford", "red", 2019, 32_164.5))
    cars.add(Car("Seat", "blue", 2021))
    cars.add(Car("Volkswagen", "white", 2010, 157_324.0))
    cars.add(Car("Mitsubishi", "red", 2017, 36_713.4))
    cars.add(Car("Honda", "blue", 2018, 36_713.4))
    cars.add(Car("Ford", "red", 2015, 82_111.5))
    cars.add(Car("Volkswagen", "red", 2001, 382_142.1))
    cars.add(Car("Volkswagen", "white", 2017, 14680.3, true))
    cars.add(Car("Suzuki", "Red", 2012, 132_999.4))

    // get all electric cars
    val electricCars = cars.filter { it.electric }
    println("electric cars")
    println(electricCars)

    // get all new cars
    val newCars = cars.filter { it.odometer == 0.0 }
    println("new cars")
    println(newCars)

    // get all red cars made after 2015 sorted by brand
    val red2016Cars = cars
            .filter { it.color == "red" && it.year > 2015 }
            .sortedBy { it.brand }
    println("red cars made after 2015")
    println(red2016Cars)

    // get the oldest white car
    val oldestWhiteCar = cars
            .sortedByDescending { it.year }
            .lastOrNull { it.color == "white" }
//            .sortedBy { it.year }
//            .firstOrNull { it.color == "white" }
    println("oldest white car")
    println(oldestWhiteCar)

    // get all brands sorted without duplicates
    val brandsWithoutDuplicates = cars
            .map { it.brand.lowercase() }
            .distinct()
            .sorted()
    println(brandsWithoutDuplicates)
}
