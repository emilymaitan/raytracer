package raytracer.graphics.surfaces;

import raytracer.graphics.Ray;
import raytracer.graphics.materials.Material;
import raytracer.math.Vector3;

public class Mesh extends Surface {

    private String name;

    @Override
    public double intersect(Ray ray) {

        return Double.MAX_VALUE;
    }

    @Override
    public Vector3 surfaceNormal(Vector3 at) {
        return null;
    }

    public Mesh(Material material, String name) {
        super(material);
        this.name = name;
    }

    @Override
    public String toString() {
        return "raytracer.graphics.surfaces.Mesh[" +
                "name='" + name + '\'' +
                ", material=" + material +
                ']';
    }
}
