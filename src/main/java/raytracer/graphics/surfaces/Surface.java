package raytracer.graphics.surfaces;

import raytracer.graphics.Ray;
import raytracer.graphics.materials.Material;
import raytracer.graphics.trafo.Transformation;
import raytracer.math.Vector3;

import java.awt.*;

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

    public Color illuminate(Color lightColor, Vector3 at, Vector3 surfToLight, Vector3 surfToView, Vector3 surfNormal, boolean includeAmbient) {
        return material.getPhong().computeIllumination(material.getMaterialColor(this, at),lightColor,surfToLight,surfToView,surfNormal,includeAmbient);
    }

    public Color illuminateAmbient(Vector3 at) {
        return material.getPhong().computeAmbient(material.getMaterialColor(this, at));
    }

    public abstract float[] getTextureCoordinates(Vector3 at);

    public Surface(Material material, Transformation transformation) {
        this.material = material;
        this.transformation = transformation == null ? new Transformation() : transformation;
    }

    public Material getMaterial() {
        return material;
    }

    public Transformation getTransformation() {
        return transformation;
    }
}