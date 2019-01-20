package raytracer.math;

import org.junit.Test;

import static org.junit.Assert.*;

public class MatrixUtilsTest {

    public static final Matrix id4 = Matrix.identity(4);

    public static final Matrix transMat = new Matrix(
            1,0,0,1,
            0,1,0,2,
            0,0,1,3,
            0,0,0,1);
    public static final Matrix scaleMat = new Matrix(
            4,0,0,0,
            0,5,0,0,
            0,0,6,0,
            0,0,0,1);
    public static final Matrix scaleTransMat = new Matrix(
            4,0,0,1,
            0,5,0,2,
            0,0,6,3,
            0,0,0,1);

    @Test
    public void objectToWorldspace() {
        assertEquals(scaleTransMat, transMat.multiply(scaleMat));
        assertEquals(scaleTransMat, MatrixUtils.ObjectToWorldspace(
                new Vector3(4,5,6),
                new Vector3(0,0,0),
                new Vector3(1,2,3)
        ));
    }

    @Test
    public void cameraToWorld() {
    }

    @Test
    public void fromTranslation() {
        // normal translation
        assertEquals(transMat, MatrixUtils.fromTranslation(new Vector3(1,2,3)));

        // zero translation
        assertEquals(id4, MatrixUtils.fromTranslation(new Vector3(0,0,0)));
    }

    @Test
    public void fromScale() {
        // normal scale
        assertEquals(scaleMat, MatrixUtils.fromScale(new Vector3(4,5,6)));

        // zero scale
        assertEquals(id4, MatrixUtils.fromScale(new Vector3(1,1,1)));
    }

    @Test
    public void fromRotation() {
        // zero rotation
        assertEquals(id4, MatrixUtils.fromRotation(new Vector3(0,0,0)));
    }

    @Test
    public void fromRotationX() {
    }

    @Test
    public void fromRotationY() {
    }

    @Test
    public void fromRotationZ() {
    }
}