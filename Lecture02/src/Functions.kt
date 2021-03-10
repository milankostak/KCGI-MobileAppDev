class Functions {

    private val height = 0
    var name = ""

    fun printString(someString: String) {
        println(someString)
    }

    private fun getInfo(): String {
        return "height: $height, name: $name"
    }

    internal fun printInfo() {
        println(getInfo())
    }

    fun saveTextToFile(content: String, filename: String = "default_file.csv") {
        // ...
    }

}
