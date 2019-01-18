package raytracer.graphics.surfaces.obj;

import raytracer.math.Vector3;

public class MeshVertex {

    public Vector3 geometricVertex;
    public Vector3 vertexNormal;
    public double textureU;
    public double textureV;

    public MeshVertex(Vector3 geometricVertex, Vector3 vertexNormal, double textureU, double textureV) {
        this.geometricVertex = geometricVertex;
        this.vertexNormal = vertexNormal;
        this.textureU = textureU;
        this.textureV = textureV;
    }

    @Override
    public String toString() {
        return "MeshVertex[" +
                "geometricVertex=" + geometricVertex +
                ", vertexNormal=" + vertexNormal +
                ", textureU=" + textureU +
                ", textureV=" + textureV +
                ']';
    }
}
