package raytracer.graphics.surfaces;

import raytracer.graphics.Ray;
import raytracer.graphics.materials.Material;
import raytracer.graphics.surfaces.obj.TriangleFace;
import raytracer.graphics.trafo.Transformation;
import raytracer.math.Vector3;

import java.util.ArrayList;

public class Mesh extends Surface {

    private String name;
    private ArrayList<TriangleFace> faces;

    @Override
    public double intersect(Ray ray) {

        return Double.MAX_VALUE;
    }

    @Override
    public Vector3 surfaceNormal(Vector3 at) {
        return null;
    }

    public Mesh(Material material, Transformation transformation, String name) {
        super(material, transformation);
        this.name = name;
    }

   // TODO tostring
}
