class Dog(private var age: Int, val name: String) {

    fun incrementAge() {
        age++
    }

    fun getInfo(): String {
        return "age: $age, name: $name"
    }

}
