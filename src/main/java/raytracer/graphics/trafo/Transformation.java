package raytracer.graphics.trafo;

import raytracer.math.*;

public class Transformation {

    /**
     * Moves an object by the vector [x,y,z].
     */
    private Vector3 translation;
    /**
     * Scales an object by x on the x-axis, y on the y-axis and z on the z-axis.
     */
    private Vector3 scale;
    /**
     * Rotates an object by x/y/z (theta) degrees around the specified axes.
     */
    private Vector3 rotationDegrees;

    private Matrix objToWorld; // pre-computed object-to-world matrix

    private void calculateObjToWorld () {
        this.objToWorld = MatrixUtils.objectToWorld(scale, rotationDegrees, translation);
    }

    public Transformation() {
        this.translation = new Vector3();
        this.scale = new Vector3(1,1,1);
        this.rotationDegrees = new Vector3();
        calculateObjToWorld();
    }

    public Transformation(Vector3 translation, Vector3 scale, Vector3 rotationDegrees) {
        this.translation        = translation       == null ? new Vector3() : translation;
        this.scale              = scale             == null ? new Vector3() : scale;
        this.rotationDegrees    = rotationDegrees   == null ? new Vector3() : rotationDegrees;

        // pre-compute the matrix from object-space to world
        calculateObjToWorld();
    }

    @Override
    public String toString() {
        return "raytracer.graphics.trafo.Transformation[" +
                "translation=" + translation +
                ", scale=" + scale +
                ", rotationDegrees=" + rotationDegrees +
                "\nobjToWorld:\n" + objToWorld +
                ']';
    }

    public Vector3 getTranslation() {
        return translation;
    }

    public Vector3 getScale() {
        return scale;
    }

    public Vector3 getRotationDegrees() {
        return rotationDegrees;
    }

    public void setTranslation(Vector3 translation) {
        this.translation = translation;
        calculateObjToWorld();
    }

    public void setScale(Vector3 scale) {
        this.scale = scale;
        calculateObjToWorld();
    }

    public void setRotationDegrees(Vector3 rotationDegrees) {
        this.rotationDegrees = rotationDegrees;
        calculateObjToWorld();
    }

    public void translateBy(Vector3 translation) {
        this.translation = this.translation.add(translation);
        calculateObjToWorld();
    }

    public void scaleBy(Vector3 scale) {
        this.scale = this.scale.add(scale);
        calculateObjToWorld();
    }

    public void rotateBy(Vector3 rotation) {
        this.rotationDegrees = this.rotationDegrees.add(rotation);
        calculateObjToWorld();
    }

    public Matrix getObjToWorld() {
        return objToWorld;
    }
}
