package raytracer.graphics.surfaces;

import raytracer.graphics.Ray;
import raytracer.graphics.materials.Material;
import raytracer.graphics.trafo.Transformation;
import raytracer.math.Vector3;

public abstract class Surface {

    protected Material material;
    protected Transformation transformation;

    /**
     * Checks whether a ray intersects with this surface.
     * @param ray The ray r(t)=o+d*t.
     * @return The parameter t for the nearest intersection point. (double.MAX_VALUE if no intersection is found.)
     */
    public abstract double intersect(Ray ray);

    /**
     * Computes the surface normal at a certain point.
     * @param at a point on the surface
     * @return normalized surface normal vector
     */
    public abstract Vector3 surfaceNormal(Vector3 at);

    public Surface(Material material, Transformation transformation) {
        this.material = material;
        this.transformation = transformation;
    }

    public Material getMaterial() {
        return material;
    }

    public Transformation getTransformation() {
        return transformation;
    }
}