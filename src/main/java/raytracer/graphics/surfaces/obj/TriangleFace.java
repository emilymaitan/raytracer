package raytracer.graphics.surfaces.obj;

public class TriangleFace {

    public MeshVertex vertex1;
    public MeshVertex vertex2;
    public MeshVertex vertex3;

    public TriangleFace(MeshVertex vertex1, MeshVertex vertex2, MeshVertex vertex3) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.vertex3 = vertex3;
    }

    @Override
    public String toString() {
        return "TriangleFace[" +
                "vertex1=" + vertex1 +
                ", vertex2=" + vertex2 +
                ", vertex3=" + vertex3 +
                ']';
    }
}
