fun main() {
    val a = 5
    val b = 7

    if (a > b) {
        println("a > b")
    } else if (a < b) {
        println("b > a")
    } else {
        println("a is equal to b")
    }

    // can be converted to this:
    when {
        a > b -> {
            println("a > b")
        }
        a < b -> {
            println("b > a")
        }
        else -> {
            println("a is equal to b")
        }
    }

    // and can be simplified to this:
    when {
        a > b -> println("a > b")
        a < b -> println("b > a")
        else -> println("a is equal to b")
    }
}
