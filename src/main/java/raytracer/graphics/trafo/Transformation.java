package raytracer.graphics.trafo;

import raytracer.math.Vector3;

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

    public Transformation(Vector3 translation, Vector3 scale, Vector3 rotationDegrees) {
        this.translation = translation;
        this.scale = scale;
        this.rotationDegrees = rotationDegrees;
    }

    @Override
    public String toString() {
        return "raytracer.graphics.trafo.Transformation[" +
                "translation=" + translation +
                ", scale=" + scale +
                ", rotationDegrees=" + rotationDegrees +
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
}
