fun main() {
    val dog = Dog(4, "Sam")
    println(dog.getInfo())
    dog.incrementAge()
    println(dog.getInfo())
    println(dog.name) // getter called in the background
    println()

    val functions = Functions()
    functions.printString("Hello world!")

    functions.name = "other name" // setter called in the background
    functions.printInfo()
    functions.saveTextToFile("file content", "other filename.txt")
    functions.saveTextToFile("file content") // using default filename
    functions.saveTextToFile(content = "another content") // specifically using parameter name
}
