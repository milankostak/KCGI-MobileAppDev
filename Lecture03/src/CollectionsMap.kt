fun main() {
    val numbersMap1 = mapOf("key1" to 1, "key2" to 2, "key3" to 3, "key4" to 1)
    println(numbersMap1) // {key1=1, key2=2, key3=3, key4=1}

    println("All keys: ${numbersMap1.keys}") // All keys: [key1, key2, key3, key4]
    println("All values: ${numbersMap1.values}") // All values: [1, 2, 3, 1]
    if ("key2" in numbersMap1) println("Value by key \"key2\": ${numbersMap1["key2"]}")
    if (1 in numbersMap1.values) println("The value 1 is in the map")
    if (numbersMap1.containsValue(1)) println("The value 1 is in the map") // same as previous

    val numbersMap2 = mutableMapOf("one" to 1, "two" to 2)
    numbersMap2.put("three", 3) // numbersMap2["three"] = 3
    numbersMap2["one"] = 11
    println(numbersMap2) // {one=11, two=2, three=3}

    // https://kotlinlang.org/docs/collections-overview.html#map
}
