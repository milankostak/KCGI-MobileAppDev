private const val FOO = "foo"

fun main() {
    // numbers
    val h: Byte = 127 // Byte.MAX_VALUE
    val i: Short = 32_767 // Short.MAX_VALUE
    val j: Int = 2_147_483_647 // Int.MAX_VALUE
    val k: Long = 9_223_372_036_854_775_807 // Long.MAX_VALUE

    val m: Float = 5F
//    val n: Float = 5 // won't work, 5 is integer
//    val o: Float = 5.0 // won't work, 5.0 is double

    val p: Double = 5.0
//    val q: Double = 5 // won't work, 5 is integer

    // correct ways
    val r = 7 // Int type inferred
    val s = 7F // Float type inferred
    val t = 7.0 // Double type inferred

    val u = Byte.MIN_VALUE
    println(u)
    val v = Short.MAX_VALUE
    println(v)

    println("Byte size: ${Byte.SIZE_BYTES}")
    println("Short size: ${Short.SIZE_BYTES}")
    println("Int size: ${Int.SIZE_BYTES}")
    println("Long size: ${Long.SIZE_BYTES}")
    println("Float size: ${Float.SIZE_BYTES}")
    println("Double size: ${Double.SIZE_BYTES}")

    println(FOO)
}
