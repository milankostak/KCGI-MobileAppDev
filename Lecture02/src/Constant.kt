private const val FOO = "foo"

class Constant {

    companion object {
        const val SIZE = 100
        private const val DEFAULT_NAME = "John"
    }

}

fun main() {
    println(FOO)
    println(Constant.SIZE)
//    println(Constant.DEFAULT_NAME) // is private
}
