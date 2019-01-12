package raytracer.math;

import org.junit.Test;

import static org.junit.Assert.*;

public class Vector3Test {

    private static final Vector3 operand1 = new Vector3(1,1,1);
    private static final Vector3 operand2 = new Vector3(1,2,3);
    private static final Vector3 operand3 = new Vector3(-3, 0, 3);
    private static final Vector3 operand0 = new Vector3(0,0,0);

    private static final double DELTA = 0.01;

    @Test
    public void add() {
        assertEquals(new Vector3(2,3,4), operand1.add(operand2));
        assertEquals(new Vector3(2,3,4), operand2.add(operand1));
        assertEquals(new Vector3(0,0,0), operand1.add(new Vector3(-1,-1,-1)));
        assertEquals(new Vector3(-2, 1, 4), operand1.add(operand3));
    }

    @Test
    public void subtract() {
        assertEquals(new Vector3(0,-1,-2), operand1.subtract(operand2));
        assertEquals(new Vector3(4, 1, -2), operand1.subtract(operand3));
    }

    @Test
    public void multiply() {
        assertEquals(new Vector3(3,6,9), operand2.multiply(3));
        assertEquals(new Vector3(3,0,-3), operand3.multiply(-1));
    }

    @Test
    public void divide() {
        assertEquals(new Vector3(-1, 0, 1), operand3.divide(3.0));
        assertEquals(operand3.divide(3), operand3.multiply(1.0/3.0));
    }

    @Test
    public void dot() {
        assertEquals(6.0, operand1.dot(operand2), 0);
        assertEquals(6.0, operand2.dot(operand1), 0);
    }

    @Test
    public void cross() {
        assertEquals(new Vector3(3,-6,3), operand1.cross(operand3));
        assertEquals(new Vector3(-3,6,-3), operand3.cross(operand1));
    }

    @Test
    public void normalize() {
        Vector3 expected1 = new Vector3(1/Math.sqrt(3),1/Math.sqrt(3),1/Math.sqrt(3));
        Vector3 result1 = operand1.normalize();
        assertEquals(expected1.getX(), result1.getX(), DELTA);
        assertEquals(expected1.getY(), result1.getY(), DELTA);
        assertEquals(expected1.getZ(), result1.getZ(), DELTA);
        assertEquals(1, result1.length(), DELTA);

        Vector3 expected3 = new Vector3(-1/Math.sqrt(2), 0, 1/Math.sqrt(2));
        Vector3 result3 = operand3.normalize();
        assertEquals(expected3.getX(), result3.getX(), DELTA);
        assertEquals(expected3.getY(), result3.getY(), DELTA);
        assertEquals(expected3.getZ(), result3.getZ(), DELTA);
        assertEquals(1, result3.length(), DELTA);
    }

    @Test
    public void length() {
        assertEquals(Math.sqrt(3), operand1.length(), DELTA);
        assertEquals(Math.sqrt(14), operand2.length(), DELTA);
        assertEquals(0, operand0.length(), DELTA);
    }

    @Test
    public void equals() {
        assertEquals(operand1, new Vector3(1,1,1));
        assertNotSame(operand1, operand2);
    }
}