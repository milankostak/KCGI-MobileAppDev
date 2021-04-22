class GettersSetters {

    var description = "empty"
        get() {
            return if (field == "empty") "" else field
        }
        set(value) {
            field = if (value.isEmpty()) "empty" else value
        }

}

fun main() {
    val a = GettersSetters()
    println(a.description)

    a.description = ""
    println(a.description)
}
