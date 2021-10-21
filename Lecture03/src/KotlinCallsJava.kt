class KotlinCallsJava {

    fun printString(string: String) {
        println("Printing in Kotlin class")
        println(string)
    }

    fun callingJava() {
        val javaCallsKotlin = JavaCallsKotlin()
        javaCallsKotlin.printString("Hello from Kotlin code!")
    }

}

fun main() {
     KotlinCallsJava().callingJava()
}
