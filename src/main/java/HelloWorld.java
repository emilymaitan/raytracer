/**
 * Every world starts with a hello!
 */
@Deprecated
public class HelloWorld {

    private static final String HELLO_MESSAGE = "Hello World!";

    public static String greet(String name) {
        return HELLO_MESSAGE + " And nice to meet you, " + name + "!";
    }

    public static void main (String[] args) {
        if (args.length == 0) {
            System.out.println(HelloWorld.greet("Emily"));
        } else {
            for (String name : args) {
                System.out.println(HelloWorld.greet(name));
            }
        } // endif

    } // endmain

}

