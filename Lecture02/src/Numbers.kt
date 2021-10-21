fun main() {
    // numbers
    val h: Byte = 127 // Byte.MAX_VALUE (2^(2^3)-1)
    val i: Short = 32_767 // Short.MAX_VALUE (2^(2^4)-1)
    val j: Int = 2_147_483_647 // Int.MAX_VALUE (2^(2^5)-1)
    val k: Long = 9_223_372_036_854_775_807 // Long.MAX_VALUE (2^(2^6)-1)

    println(h)
    println(i)
    println(j)
    println(k)

    val m: Float = 5F
//    val n = 5.toFloat() // won't work, 5 is integer
//    val o: Float = 5.0 // won't work, 5.0 is double
    val p = 5.toFloat()

    val q: Double = 5.0
//    val r: Double = 5 // won't work, 5 is integer
    val s = 5.toDouble()

    // correct ways
    val t = 7 // Int type inferred
    val u = 7F // Float type inferred
    val v = 7.0 // Double type inferred

    val w = Byte.MIN_VALUE
    println(w)
    val x = Short.MAX_VALUE
    println(x)

    println("Byte size: ${Byte.SIZE_BYTES}")
    println("Short size: ${Short.SIZE_BYTES}")
    println("Int size: ${Int.SIZE_BYTES}")
    println("Long size: ${Long.SIZE_BYTES}")
    println("Float size: ${Float.SIZE_BYTES}")
    println("Double size: ${Double.SIZE_BYTES}")
}
