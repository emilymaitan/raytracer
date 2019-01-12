package raytracer.graphics.surfaces;

import raytracer.graphics.Ray;
import raytracer.graphics.materials.Material;
import raytracer.math.Vector3;

import java.awt.*;

public abstract class Surface {

    protected Material material;

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

    public Surface(Material material) {
        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }
}