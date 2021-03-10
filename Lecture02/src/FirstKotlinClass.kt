fun main() {
    // documentation
    // single line comment
    /*
     multi line comment
     */

    // semicolons
    println("Hello world!");println("Hello world!")
    println("Hello world!")

    // constants and variables
    val a = 5
    val b = "hello"
    var c = 2
    c += a
    val d = emptyList<String>()

    // constants and variables with type declaration
    val e: Int
    e = 10
    var f: String
    f = "hello"
    f = "world"
    val g: Long = 50

    // outputs and formatting
    println(a)
    println(b)
    //println(b + " " + c)
    println("$b $c")
    println("length of \"$b\" is ${b.length}")
    println("length of '$f' is ${f.length}")
}
