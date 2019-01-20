package raytracer.math;

import org.junit.Test;

import static org.junit.Assert.*;

public class MatrixUtilsTest {

    private static final Matrix id4 = Matrix.identity(4);

    private static final double angle = 45;
    private static final double sinA = Math.sin(Math.toRadians(angle));
    private static final double cosA = Math.cos(Math.toRadians(angle));

    private static final Matrix transMat = new Matrix(
            1,0,0,1,
            0,1,0,2,
            0,0,1,3,
            0,0,0,1);
    private static final Matrix scaleMat = new Matrix(
            4,0,0,0,
            0,5,0,0,
            0,0,6,0,
            0,0,0,1);
    private static final Matrix scaleTransMat = new Matrix(
            4,0,0,1,
            0,5,0,2,
            0,0,6,3,
            0,0,0,1);

    private static final Matrix rotXMat = new Matrix(
            1,0,0,0,
            0,cosA,-sinA,0,
            0,sinA,cosA,0,
            0,0,0,1
    );
    private static final Matrix rotYMat = new Matrix(
            cosA, 0, sinA, 0,
            0, 1, 0, 0,
            -sinA, 0, cosA, 0,
            0, 0, 0, 1
    );
    private static final Matrix rotZMat = new Matrix(
            cosA, -sinA, 0, 0,
            sinA, cosA, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1
    );

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
        assertEquals(transMat, MatrixUtils.cameraToWorld(new Vector3(),new Vector3(1,2,3)));
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

        assertEquals(
                rotXMat.multiply(rotYMat).multiply(rotZMat),
                MatrixUtils.fromRotation(new Vector3(angle, angle, angle))
        );
    }

    @Test
    public void fromRotationX() {
        // zero rotation
        assertEquals(id4, MatrixUtils.fromRotationX(0));

        assertEquals(rotXMat, MatrixUtils.fromRotationX(angle));
    }

    @Test
    public void fromRotationY() {
        // zero rotation
        assertEquals(id4, MatrixUtils.fromRotationY(0));

        assertEquals(rotYMat, MatrixUtils.fromRotationY(angle));
    }

    @Test
    public void fromRotationZ() {
        // zero rotation
        assertEquals(id4, MatrixUtils.fromRotationZ(0));

        assertEquals(rotZMat, MatrixUtils.fromRotationZ(angle));
    }
}