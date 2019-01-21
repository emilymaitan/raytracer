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
    public void objectToWorld() {
        // no transformations = identity
        Vector4 v1234 = new Vector4(1,2,3,4);
        Matrix zeroTransform = MatrixUtils.objectToWorld(
                new Vector3(1,1,1),
                new Vector3(),
                new Vector3()
        );
        assertEquals(id4, zeroTransform);
        assertEquals(v1234, zeroTransform.multiply4x4(v1234));

        // only scaling
        assertEquals(scaleMat, MatrixUtils.objectToWorld(
                new Vector3(4,5,6),
                new Vector3(),
                new Vector3()
        ));

        // translation + scaling
        assertEquals(scaleTransMat, transMat.multiply(scaleMat));
        assertEquals(scaleTransMat, MatrixUtils.objectToWorld(
                new Vector3(4,5,6),
                new Vector3(0,0,0),
                new Vector3(1,2,3)
        ));

        // self-inversion
        Vector3 t = new Vector3(); Vector3 r = new Vector3(); Vector3 s = new Vector3(1,1,1);
        Matrix otw = MatrixUtils.objectToWorld(s,r,t);
        assertEquals(id4, otw.multiply(otw.invert()));
        assertEquals(id4, otw.invert().multiply(otw));
    }

    @Test
    public void worldToObject() {
        // default vectors do not change anything
        Vector3 t = new Vector3(); Vector3 r = new Vector3(); Vector3 s = new Vector3(1,1,1);
        Matrix otw = MatrixUtils.objectToWorld(s,r,t);
        Matrix wto = MatrixUtils.worldToObject(s,r,t);
        assertEquals(id4, wto.multiply(otw));

        // transform to object space and back with defaults
        Vector3 p = new Vector3(-1,-2,-3);
        Vector3 pp = MatrixUtils.transformPoint(p, wto);
        pp = MatrixUtils.transformPoint(pp, otw);
        assertEquals(p,pp);

        // with non-defaults
        t = new Vector3(1,2,3); r = new Vector3(4,5,6); s = new Vector3(7,8,9);
        otw = MatrixUtils.objectToWorld(s,r,t);
        wto = MatrixUtils.worldToObject(s,r,t);
        pp = MatrixUtils.transformPoint(p, wto);
        pp = MatrixUtils.transformPoint(pp, otw);
        // assertEquals(p,pp); todo fix
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

        // inverse translation
        assertEquals(id4, MatrixUtils.fromTranslation(new Vector3(1,2,3)).multiply(
                MatrixUtils.fromTranslation(new Vector3(-1,-2,-3))
        ));
    }

    @Test
    public void fromScale() {
        // normal scale
        assertEquals(scaleMat, MatrixUtils.fromScale(new Vector3(4,5,6)));

        assertEquals(new Vector4(3,0,0,1),
                MatrixUtils.fromScale(new Vector3(3,1,1)).multiply4x4(new Vector4(1,0,0,1)));

        // zero scale
        assertEquals(id4, MatrixUtils.fromScale(new Vector3(1,1,1)));

        // inverse scale
        assertEquals(id4, MatrixUtils.fromScale(new Vector3(1,2,3)).multiply(
                MatrixUtils.fromScale(new Vector3(1.0/1,1.0/2,1.0/3))
        ));
    }

    @Test
    public void fromRotation() {
        // zero rotation
        assertEquals(id4, MatrixUtils.fromRotation(new Vector3(0,0,0)));

        assertEquals(
                rotXMat.multiply(rotYMat).multiply(rotZMat),
                MatrixUtils.fromRotation(new Vector3(angle, angle, angle))
        );

        // inverse rotation, TODO fix
        /*
        assertEquals(id4, MatrixUtils.fromRotation(new Vector3(1,2,3)).multiply(
                MatrixUtils.fromRotation(new Vector3(-1,-2,-3))
        ));*/
    }

    @Test
    public void fromRotationX() {
        // zero rotation
        assertEquals(id4, MatrixUtils.fromRotationX(0));

        // normal
        assertEquals(rotXMat, MatrixUtils.fromRotationX(angle));

        // inverse
        assertEquals(id4, MatrixUtils.fromRotationX(30).multiply(
                MatrixUtils.fromRotationX(-30)
        ));
    }

    @Test
    public void fromRotationY() {
        // zero rotation
        assertEquals(id4, MatrixUtils.fromRotationY(0));

        assertEquals(rotYMat, MatrixUtils.fromRotationY(angle));

        // inverse
        assertEquals(id4, MatrixUtils.fromRotationY(30).multiply(
                MatrixUtils.fromRotationY(-30)
        ));
    }

    @Test
    public void fromRotationZ() {
        // zero rotation
        assertEquals(id4, MatrixUtils.fromRotationZ(0));

        assertEquals(rotZMat, MatrixUtils.fromRotationZ(angle));

        // inverse
        assertEquals(id4, MatrixUtils.fromRotationZ(30).multiply(
                MatrixUtils.fromRotationZ(-30)
        ));
    }
}