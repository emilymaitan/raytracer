package raytracer.io;

import com.owens.oobjloader.builder.Face;
import com.owens.oobjloader.builder.FaceVertex;
import com.owens.oobjloader.parser.Parse;
import com.owens.oobjloader.builder.Build;

import raytracer.graphics.surfaces.obj.TriangleFace;
import raytracer.graphics.surfaces.obj.MeshVertex;
import raytracer.math.Vector3;

import java.io.File;
import java.util.ArrayList;

/**
 * A wrapper class for the oObjLoader by Sean Owens: https://github.com/seanrowens/oObjLoader
 */
public class ObjParser {

    public static ArrayList<TriangleFace> parseObj(String filename) {
        try {
            Build builder = new Build();
            Parse objData = new Parse(builder, filename);

            if (builder.faceQuadCount + builder.facePolyCount > 0) {
                throw new RuntimeException("We do not support this feature yet.");
                // TODO if this happens, implement triangulation into the parser
                // use this: https://stackoverflow.com/questions/23723993/converting-quadriladerals-in-an-obj-file-into-triangles
                // insert a face split at Build.java, line 88
            }

            ArrayList<TriangleFace> faces = new ArrayList<>();

            for(Face face : builder.faces) {
                ArrayList<MeshVertex> vertices = new ArrayList<>();
                for(FaceVertex v : face.vertices) {
                    MeshVertex mv = new MeshVertex(
                            v.v == null ? null : new Vector3((double)v.v.x, (double)v.v.y, (double)v.v.z),
                            v.n == null ? null : new Vector3((double)v.n.x, (double)v.n.y, (double)v.n.z),
                            v.t == null ? null :(double)v.t.u,
                            v.t == null ? null :(double)v.t.v
                    );
                    vertices.add(mv);
                }
                faces.add(new TriangleFace(
                        vertices.get(0),
                        vertices.get(1),
                        vertices.get(2)
                ));
            }

            return faces;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        ArrayList<TriangleFace> faces = parseObj(
                new File(SceneParser.class.getResource("/scenes/box.obj").getFile()).getAbsolutePath()
        );

        System.out.println(faces.toString());
    }
}
