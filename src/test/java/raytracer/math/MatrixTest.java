package raytracer.math;

import org.junit.Test;

import static org.junit.Assert.*;

public class MatrixTest {

    private static final double[][] data1 = { { 1, 0, 1 }, { 0, 0, 0 }, { 1, 1, 1} };
    private static final double[][] data2 = { { -1, 0, 0 }, { 1, 2, 3 }, { -1, -2, -3} };
    private static final Matrix operand1 = new Matrix(data1);
    private static final Matrix operand2 = new Matrix(data2);

    private static final double DELTA = 0.01;

    @Test
    public void add() {
        double[][] expected = { { 0, 0, 1 }, { 1, 2, 3 }, { 0, -1, -2} };
        assertEquals(new Matrix(expected), operand1.add(operand2));
    }

    @Test
    public void multiply() {
        double[][] expected2 = { { 2, 0, 0 }, { -2, -4, -6 }, { 2, 4, 6} }; // * -2
        assertEquals(new Matrix(expected2), operand2.multiply(-2));

        double[][] expected = { { -1, 0, 0 }, { 0, 0, 0 }, { -1, -2, -3} };
        assertEquals(new Matrix(expected), operand1.multiply(operand2));
    }

    @Test
    public void transpose() {
        double[][] expected = { { -1, 1, -1 }, { 0, 2, -2 }, { 0, 3, -3} };
        assertEquals(new Matrix(expected), operand2.transpose());
    }

    @Test
    public void at() {
        assertEquals(3, operand2.at(2,3), 0);
    }

    @Test
    public void identity() {
        double[][] id4 = { { 1,0,0,0 }, { 0,1,0,0 }, { 0,0,1,0 },{ 0,0,0,1 } };
        assertEquals(new Matrix(id4), Matrix.identity(4));
    }

    @Test
    public void determinant() {
        double[][] mat4 = { { 2,0,0,0 }, { 0,2,0,0 }, { 0,0,2,0 },{ 0,0,0,2 } };

        try {
            assertEquals(0, operand2.determinant(), 0);
            assertEquals(16, new Matrix(mat4).determinant(), 0);
        } catch (RaytracerMathException e) {
            e.printStackTrace();
            fail("Determinant: Should not throw exception!");
        }
    }

    @Test
    public void equals() {
        double[][] testData = { { 1, 0, 1 }, { 0, 0, 0 }, { 1, 1, 1} };
        assertEquals(operand1, new Matrix(data1));
        assertEquals(operand1, new Matrix(testData));
        assertNotSame(operand1, operand2);
    }
}