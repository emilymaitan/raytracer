package raytracer.io;

import com.owens.oobjloader.parser.Parse;
import com.owens.oobjloader.builder.Build;

import raytracer.graphics.surfaces.Mesh;

import java.io.File;

/**
 * A wrapper class for the oObjLoader by Sean Owans: https://github.com/seanrowens/oObjLoader
 */
public class ObjParser {

    public static Mesh parseObj(String filename) {
        try {
            Build builder = new Build();
            Parse objData = new Parse(builder, filename);
            System.out.println(builder.faceVerticeList.get(0));



            return null;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        parseObj(
                new File(SceneParser.class.getResource("/scenes/open_room.obj").getFile()).getAbsolutePath()
        );
    }
}
