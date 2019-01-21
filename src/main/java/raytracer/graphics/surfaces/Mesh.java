package raytracer.graphics.surfaces;

import raytracer.graphics.Ray;
import raytracer.graphics.materials.Material;
import raytracer.graphics.materials.SolidMaterial;
import raytracer.graphics.surfaces.obj.TriangleFace;
import raytracer.graphics.trafo.Transformation;
import raytracer.math.MathUtils;
import raytracer.math.Vector3;

import java.awt.*;
import java.util.ArrayList;

public class Mesh extends Surface {

    private String name;
    private ArrayList<TriangleFace> faces;

    private TriangleFace lastHit; // a reference to the last hit face for optimization


    @Override
    public double intersect(Ray ray) {
        double t = Double.MAX_VALUE;

        // todo for each triangle intersect
        for (TriangleFace face: faces) {
            double tt = triangleIntersect(ray, face);
            if (tt < t) {
                t = tt;
                lastHit = face;
            }
        }

        return t;
    }

    // Uses the Möller–Trumbore algorithm for fast and simple Ray-Triangle intersection. Sources:
    // - the tutorial: the formula is
    // (a b t) = 1/det * [((s x e1)^T * e2), ((d x e2)^T * s), ((s x e1)^T * d)]
    // with det = (d x e2)^T * e1, which is trianglenormal x direction; a b ... barycentrics
    // - https://en.wikipedia.org/wiki/M%C3%B6ller%E2%80%93Trumbore_intersection_algorithm
    // - https://www.scratchapixel.com/lessons/3d-basic-rendering/ray-tracing-rendering-a-triangle/moller-trumbore-ray-triangle-intersection
    private double triangleIntersect(Ray ray, TriangleFace triangle) {

        // first, check whether the ray and triangle are parallel
        // if the dot of the triangle normal and the ray is 0, they are!
        Vector3 p = ray.getDirection().cross(triangle.getEdgev13());
        double det = p.dot(triangle.getEdgev12());
        if (Math.abs(det) <= MathUtils.EPSILON) { // no backface culling
            return Double.MAX_VALUE; // there is no intersection
        }

        // pre-calculate the inverse determinant
        double invDet = 1.0/det;

        // now, we calculate the barycentric a and b coordinates, and check if they fulfill the constraints
        // it must be that 0 <= a, b <=1 ... if that is not fulfilled, we have no intersection
        // we start with calculating a and checking the constraints:
        Vector3 aa = ray.getOriginPoint().subtract(triangle.getVertex1().getV());
        double a = invDet * aa.dot(p);
        if (a < 0.0 || a > 1.0) { // the constraint is violated
            return Double.MAX_VALUE;
        }

        // a is allright, now we check for b
        Vector3 bb = aa.cross(triangle.getEdgev12());
        double b = invDet * ray.getDirection().dot(bb);
        if (b < 0.0 || b > 1.0) { // the constraint is violated
            return Double.MAX_VALUE;
        }

        // We now know that both a and b are valid, and can use them to calculate t.
        // This will give us the intersection point!
        double t = invDet * triangle.getEdgev13().dot(bb);

        // The final check: is this a ray or line intersection?
        if (t > MathUtils.EPSILON) {
            return t;
        } else {
            return Double.MAX_VALUE;
        }
    }

    private boolean isPointOnTriangle(TriangleFace face, Vector3 at) {
        return true;
    }

    @Override
    public Vector3 surfaceNormal(Vector3 at) {
        return lastHit.getNormal().normalize();

        /*
        // on which triangle is this?
        TriangleFace f = null;
        for (TriangleFace face : faces) {
            if (isPointOnTriangle(face, at)) {
                f = face; break;
            }
        }

        if (f == null) throw new RuntimeException("Point is not on any surface!");

        return f.getNormal().normalize();
        */
    }

    @Override
    public float[] getTextureCoordinates(Vector3 at) {
        float[] uv = new float[2];

        uv[0] = 0.0f;
        uv[1] = 0.0f;

        return uv;
    }

    public Mesh(Material material, Transformation transformation, String name, ArrayList<TriangleFace> faces) {
        super(material, transformation);
        this.name = name;
        this.faces = faces;
    }

    @Override
    public String toString() {
        return "Mesh['" + name + "', " +
                "faces=" + faces +
                ", material=" + material +
                ", transformation=" + transformation +
                '}';
    }
}
