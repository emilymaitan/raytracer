package raytracer.graphics.surfaces.obj;

import raytracer.math.Vector3;

public class MeshVertex {

    /**
     * The geometric vertex.
     */
    private Vector3 v;
    /**
     * The vertex normal.
     */
    private Vector3 vn;
    /**
     * Texture coordinate u.
     */
    private double texU;
    /**
     * Texture coordinate v.
     */
    private double texV;

    public MeshVertex(Vector3 v, Vector3 vn, double texU, double texV) {
        this.v = v;
        this.vn = vn;
        this.texU = texU;
        this.texV = texV;
    }

    @Override
    public String toString() {
        return "MeshVertex[" +
                "v=" + v +
                ", vn=" + vn +
                ", texU=" + texU +
                ", texV=" + texV +
                ']';
    }

    public Vector3 getV() {
        return v;
    }

    public Vector3 getVn() {
        return vn;
    }

    public double getTexU() {
        return texU;
    }

    public double getTexV() {
        return texV;
    }
}
