package raytracer.math;

import raytracer.graphics.surfaces.Mesh;
import raytracer.graphics.trafo.Transformation;

/**
 * Generates Matrices for typical graphics applications.
 * Inspired by the source code of glMatrix: http://glmatrix.net/docs/mat4.js.html
 */
public class MatrixUtils {

    /**
     * Calculates a matrix that transforms a Vector4 from object to world space.
     * @param scale scale
     * @param rotation rotation
     * @param translation translation
     * @return matrix
     */
    public static Matrix objectToWorld(Vector3 scale, Vector3 rotation, Vector3 translation) {

        // calculate all matrices
        Matrix scaleMat = fromScale(scale);
        Matrix rotMat = fromRotation(rotation);
        Matrix transMat = fromTranslation(translation);

        // to avoid distortion, we must first Scale -> Rotate -> Translate
        // we assume objects "at origin", and must apply inverse order:
        Matrix result = rotMat.multiply(scaleMat);
        result = transMat.multiply(result);

        return result;
    }

    /**
     * Calculates a matrix that transforms a Vector4 from world to object space.
     * @param scale scale
     * @param rotation rotation
     * @param translation translation
     * @return matrix
     */
    public static Matrix worldToObject(Vector3 scale, Vector3 rotation, Vector3 translation) {
        Matrix s = fromScale(new Vector3(1.0/scale.getX(), 1.0/scale.getY(), 1.0/scale.getZ()));
        Matrix rX = fromRotationX(-rotation.getX());
        Matrix rY = fromRotationY(-rotation.getY());
        Matrix rZ = fromRotationZ(-rotation.getZ());
        Matrix t = fromTranslation(translation.multiply(-1));

        Matrix result = rZ.multiply(t);
        result = rY.multiply(result);
        result = rX.multiply(result);
        result = s.multiply(result);

        return result;
    }

    public static Vector3 transformPoint(Vector3 vector, Matrix matrix) {
        return new Vector3(matrix.multiply4x4(new Vector4(vector, 1)));
    }

    public static Vector3 transformDirection(Vector3 vector, Matrix matrix) {
        return new Vector3(matrix.multiply4x4(new Vector4(vector, 0)));
    }

    /**
     * Calculates a matrix that transforms a Vector4 from camera to world space.
     * @param rotation rotation
     * @param translation translation
     * @return matrix
     */
    public static Matrix cameraToWorld(Vector3 rotation, Vector3 translation) {
        Matrix rotMat = fromRotation(rotation);
        Matrix traMat = fromTranslation(translation);
        return traMat.multiply(rotMat);
    }

    /**
     * Returns a translation matrix from a translation vector.
     * The form is:
     * 1 0 0 tx
     * 0 1 0 ty
     * 0 0 1 tz
     * 0 0 0 1
     * @param translation tx ty tz
     * @return A 4D translation matrix.
     */
    public static Matrix fromTranslation(Vector3 translation) {
        Matrix result = Matrix.identity(4);

        result.set(1,4,translation.getX());
        result.set(2,4, translation.getY());
        result.set(3,4,translation.getZ());

        return result;
    }

    /**
     * Returns a scale matrix from a scale vector.
     * The form is:
     * sx 0  0  0
     * 0  sy 0  0
     * 0  0  sz 0
     * 0  0  0  1
     * @param scale sx sy sz
     * @return the scale matarix
     */
    public static Matrix fromScale(Vector3 scale) {
        Matrix result = Matrix.identity(4);

        result.set(1,1,scale.getX());
        result.set(2,2,scale.getY());
        result.set(3,3,scale.getZ());

        return result;
    }

    /**
     * Returns a complete rotation matrix.
     * @param rotation xRot yRot zRot
     * @return rotmat
     */
    public static Matrix fromRotation(Vector3 rotation) {
        Matrix rX = fromRotationX(rotation.getX());
        Matrix rY = fromRotationY(rotation.getY());
        Matrix rZ = fromRotationZ(rotation.getZ());

        Matrix result = rY.multiply(rZ);
        result = rX.multiply(result);

        return result;
    }

    /**
     * Returns a rotation matrix around the x-Axis.
     * Form:
     * 1        0       0       0
     * 0    cos(a)  -sin(a)     0
     * 0    sin(a)   cos(a)     0
     * 0        0       0       1
     *
     * @param alpha Degrees.
     * @return RotationX.
     */
    public static Matrix fromRotationX(double alpha) {
        double sinA = Math.sin(Math.toRadians(alpha));
        double cosA = Math.cos(Math.toRadians(alpha));

        return new Matrix(
                1,0,0,0,
                0,cosA,-sinA,0,
                0,sinA,cosA,0,
                0,0,0,1
        );
    }

    /**
     * Returns a rotation matrix around the y-Axis.
     * Form:
     *  cos(a)  0    sin(a)     0
     * 0        1       0       0
     * -sin(a)  0    cos(a)     0
     * 0        0       0       1
     *
     * @param alpha Degrees.
     * @return RotationY.
     */
    public static Matrix fromRotationY(double alpha) {
        double sinA = Math.sin(Math.toRadians(alpha));
        double cosA = Math.cos(Math.toRadians(alpha));

        return new Matrix(
                cosA,0,sinA,0,
                0,1,0,0,
                -sinA,0,cosA,0,
                0,0,0,1
        );
    }

    /**
     * Returns a rotation matrix around the z-Axis.
     * Form:
     *  cos(a)  -sin(a)     0       0
     *  sin(a)   cos(a)     0       0
     *      0       0       1       0
     *      0       0       0       1
     * @param alpha Degrees.
     * @return RotationZ.
     */
    public static Matrix fromRotationZ(double alpha) {
        double sinA = Math.sin(Math.toRadians(alpha));
        double cosA = Math.cos(Math.toRadians(alpha));

        return new Matrix(
                cosA, -sinA, 0, 0,
                sinA, cosA, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1
        );
    }

    public static void main(String[] args) {
        Transformation t = new Transformation(
                new Vector3(0.5,0.0,0.0), // translation
                new Vector3(1.0,1.0,1.0), // scale
                new Vector3(0.0,0.0,0.0)  // rotation
        );
        System.out.println(
                fromTranslation(new Vector3(3,3,3))
                        .multiply(fromScale(new Vector3(2,2,2))));
    }
}