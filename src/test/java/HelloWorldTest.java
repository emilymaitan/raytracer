import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HelloWorldTest {

    @Before
    public void setUp() throws Exception {
        System.out.println("Test start!");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Test end!");
    }

    @Test
    public void greet() {
        String testGreet = "emilymaitan";
        String expectedGreet = "Hello World! And nice to meet you, emilymaitan!";
        assertEquals(expectedGreet, HelloWorld.greet(testGreet));
    }
}