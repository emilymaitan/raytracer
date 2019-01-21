package raytracer.graphics.surfaces;

import raytracer.graphics.Ray;
import raytracer.graphics.illumination.Phong;
import raytracer.graphics.materials.Material;
import raytracer.graphics.materials.SolidMaterial;
import raytracer.graphics.materials.TexturedMaterial;
import raytracer.graphics.trafo.Transformation;
import raytracer.math.MathUtils;
import raytracer.math.Vector3;
import raytracer.math.Vector4;

import java.awt.Color;

/**
 * Represents a sphere object in a scene.
 */
public class Sphere extends Surface {

    /**
     * The radius of the sphere.
     */
    private double radius;
    /**
     * The position of the sphere's center (in world coordinates).
     */
    private Vector3 position;

    // Sources:
    // https://www.scratchapixel.com/lessons/3d-basic-rendering/minimal-ray-tracer-rendering-simple-shapes/ray-sphere-intersection
    // https://www.youtube.com/watch?v=vjeU6aOntmY

    @Override
    public double intersect(Ray ray) {
        // sphere equation: x^2 + y^2 + z^2 = r^2
        // intersection equation: (ray - spherecenter)^2 + r^2 = 0
        // TODO: worldPosition is bandaid fix, only works for translation!
        Vector3 originOffset = ray.getOriginPoint().subtract(worldPosition());


        double a = ray.getDirection().dot(ray.getDirection());
        double b = 2 * ray.getDirection().dot(originOffset);
        double c = originOffset.dot(originOffset) - this.radius; // TODO fix "world radius"
        double[] solutions = MathUtils.solveQuadratic(a, b, c);

        double result = 0;

        /* Assuming t0 and t1 are sorted, the following cases exist:
         * - no t0 or 01: there is no intersection (a)
         * - t0 = t1: the ray is tangent to the sphere (b)
         * - t0 > 0 && t1 > 0: the ray "cuts through" the sphere in two points (c)
         * - t0 < 0 && t1 > 0: ray cuts twice, but the camera is inside the ray (d)
         * - t1 < 0 && t1 < 0: the sphere is completely behind the camera (e)
         */

        switch (solutions.length) {
            case 0: // there is no intersection
                result = Double.MAX_VALUE; // (a)
                break;
            case 1: // one intersection, it's a tangent
                result = solutions[0]; // (b)
                break;
            case 2: // two intersections, we must do further checks
                if (solutions[0] < 0) {
                    // the first one is negative, thus "behind" our ray - we don't count it
                    // we check for the second ray instead
                    solutions[0] = solutions[1];
                    if (solutions[0] < 0) { // both are <0, ie. the sphere is behind us!
                        return Double.MAX_VALUE; // (e)
                    } // else, if t1>0 the camera is inside the sphere
                }
                // we have either the classic "two intersections, camera in front" case and return the min
                // (note: MathUtils sorts the result!)
                // or the camera is inside the sphere
                result = solutions[0]; // (c) or (d)
                break;
            default: throw new RuntimeException("Illegal quadratic solution!");
        }

        return result;
    }

    @Override
    public Vector3 surfaceNormal(Vector3 at) {
        // check if "at" is really a point on this surface
        if (!isPointOnSphere(at)) throw new RuntimeException("Vector is not on the sphere!");

        // the normal is simply the vector from the sphere's middle point
        // to the point on the surface
        return at.subtract(worldPosition()).normalize();
    }

    @Override
    public float[] getTextureCoordinates(Vector3 at) {
        float[] uv = new float[2];

        // Formula taken from: https://en.wikipedia.org/wiki/UV_mapping
        Vector3 d = at.subtract(position).normalize();
        uv[0] = (float)(0.5 + ((Math.atan2(d.getZ(), d.getX())/(2*Math.PI))));
        uv[1] = (float)(0.5 - ((Math.asin(d.getY())/Math.PI)));

        if(uv[0] > 1 || uv[0] < 0 || uv[1] > 1 || uv[1] < 0)
            throw new RuntimeException("illegal uv");

        return uv;
    }

    private boolean isPointOnSphere(Vector3 p) {
        Vector3 o = p.subtract(worldPosition());
        return (o.getX()*o.getX() + o.getY()*o.getY() + o.getZ()*o.getZ() - radius*radius) <= MathUtils.EPSILON;
    }

    public Vector3 worldPosition() {
        return new Vector3(this.transformation.getObjToWorld().multiply4x4(new Vector4(position,1)));
    }

    public Sphere(Material material, Transformation transformation, Vector3 position, double radius) {
        super(material, transformation);
        this.position = position;
        this.radius = radius;
    }

    @Override
    public String toString() {
        return "raytracer.graphics.surfaces.Sphere[" +
                "radius=" + radius +
                ", position=" + position +
                ", material=" + material +
                ", Transformation=" + transformation +
                ']';
    }
}
