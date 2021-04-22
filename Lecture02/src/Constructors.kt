class Constructors(val parameter1: Int,
                   var parameter2: String,
                   private val parameter3: Int) {

    constructor(parameter1: Int) : this(parameter1, "", 0)

}

fun main() {
    val a = Constructors(5)
    println(a.parameter1)
}
