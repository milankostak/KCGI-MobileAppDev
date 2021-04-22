class Functions {

    private val height = 0
    var name = ""

    fun printString(someString: String) {
        println(someString)
    }

    private fun getInfo(): String {
        return "height: $height, name: $name"
    }

    fun printInfo() {
        println(getInfo())
    }

    fun saveTextToFile(content: String, filename: String = "default_file.csv") {
        // ...
    }

}

fun main() {
    val a = Functions()
    a.name = "George"
    a.printInfo()
    a.printString("something")
    a.saveTextToFile("content", "some_other_file.txt")
}
