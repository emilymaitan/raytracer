package raytracer.graphics;

import raytracer.image.ColorRGB;
import raytracer.math.Vector3;

import java.awt.Color;

// RAY: r(t) = o + t*d, t > 0
/**
 * Mathematically represents a ray r(t) = origin + t*direction, t > 0.
 * Every ray has an origin point o, which is where the ray is shot from,
 * and a (normalized) direction d where it is headed.
 */
public class Ray {

    private Vector3 originPoint;
    private Vector3 direction;

    /**
     * Encodes a ray direction as a color.
     * @param a Half of the dimension of the image plane.
     * @return Color representation of the ray.
     */
    public Color toColor(double a) {
        Vector3 colorVector = direction.normalize();
        double red = (colorVector.getX()+a)/(2*a);
        double green = (colorVector.getY()+a)/(2*a);
        double blue = (colorVector.getZ()+a)/(2*a);

        return new Color((float)red, (float)green, (float)blue);
    }

    public Vector3 at(double t) {
        return originPoint.add(direction.multiply(t));
    }

    public Vector3 getOriginPoint() {
        return originPoint;
    }

    public Vector3 getDirection() {
        return direction;
    }

    public Ray(Vector3 originPoint, Vector3 direction) {
        this.originPoint = originPoint;
        this.direction = direction.normalize();
    }

    @Override
    public String toString() {
        return
                "raytracer.graphics.Ray[originPoint= " + originPoint.toString()
                        + ", direction= " + direction.toString() + "]";
    }
}
