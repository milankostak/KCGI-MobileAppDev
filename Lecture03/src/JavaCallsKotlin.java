public class JavaCallsKotlin {

    public void printString(String string) {
        System.out.println("Printing in Java class");
        System.out.println(string);
    }

    private void callingKotlin() {
        final KotlinCallsJava kotlinCallsJava = new KotlinCallsJava();
        kotlinCallsJava.printString("Hello from Java code!");
    }

    public static void main(String[] args) {
        new JavaCallsKotlin().callingKotlin();
    }

}
