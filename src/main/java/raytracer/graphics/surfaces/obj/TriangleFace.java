package raytracer.graphics.surfaces.obj;

import raytracer.math.Vector3;

public class TriangleFace {

    private MeshVertex vertex1;
    private MeshVertex vertex2;
    private MeshVertex vertex3;

    private Vector3 normal; // pre-computed for efficiency
    private Vector3 edgev12;
    private Vector3 edgev13;

    public TriangleFace(MeshVertex vertex1, MeshVertex vertex2, MeshVertex vertex3) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.vertex3 = vertex3;

        this.edgev12 = vertex2.getV().subtract(vertex1.getV());
        this.edgev13 = vertex3.getV().subtract(vertex1.getV());
        this.normal = edgev12.cross(edgev13);
    }

    @Override
    public String toString() {
        return "TriangleFace[" +
                "vertex1=" + vertex1 +
                ", vertex2=" + vertex2 +
                ", vertex3=" + vertex3 +
                ']';
    }

    public MeshVertex getVertex1() {
        return vertex1;
    }

    public MeshVertex getVertex2() {
        return vertex2;
    }

    public MeshVertex getVertex3() {
        return vertex3;
    }

    public Vector3 getNormal() {
        return normal;
    }

    public Vector3 getEdgev12() {
        return edgev12;
    }

    public Vector3 getEdgev13() {
        return edgev13;
    }
}
