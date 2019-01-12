package raytracer.math;

import org.junit.Test;

import static org.junit.Assert.*;

public class Vector4Test {

    private static final Vector4 operand1 = new Vector4(1,1,1,1);
    private static final Vector4 operand2 = new Vector4(-3,0,0,3);

    private static final double DELTA = 0.01;

    @Test
    public void add() {
        assertEquals(new Vector4(-2, 1, 1, 4), operand1.add(operand2));
        assertEquals(new Vector4(-2, 1, 1, 4), operand2.add(operand1));
    }

    @Test
    public void multiply() {
        assertEquals(new Vector4(6,0,0,-6), operand2.multiply(-2));
    }

    @Test
    public void dot() {
        assertEquals(0, operand1.dot(operand2), 0);
    }

    @Test
    public void normalize() {
        Vector4 expected2 = new Vector4(-1/Math.sqrt(2),0,0,1/Math.sqrt(2));
        Vector4 actual2 = operand2.normalize();
        assertEquals(expected2.getA(), actual2.getA(), DELTA);
        assertEquals(expected2.getB(), actual2.getB(), DELTA);
        assertEquals(expected2.getC(), actual2.getC(), DELTA);
        assertEquals(expected2.getD(), actual2.getD(), DELTA);
        assertEquals(1, actual2.length(), DELTA);
    }

    @Test
    public void length() {
        assertEquals(Math.sqrt(18), operand2.length(), DELTA);
    }

    @Test
    public void equals() {
        assertEquals(operand1, new Vector4(1,1,1,1));
        assertNotSame(operand1, operand2);
    }
}