package raytracer.io;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import raytracer.graphics.Camera;
import raytracer.graphics.Scene;
import raytracer.graphics.illumination.Phong;
import raytracer.graphics.lights.ParallelLight;
import raytracer.graphics.lights.PointLight;
import raytracer.graphics.lights.SpotLight;
import raytracer.graphics.materials.Material;
import raytracer.graphics.materials.SolidMaterial;
import raytracer.graphics.materials.TexturedMaterial;
import raytracer.graphics.surfaces.Mesh;
import raytracer.graphics.surfaces.Sphere;
import raytracer.graphics.surfaces.obj.TriangleFace;
import raytracer.graphics.trafo.Transformation;
import raytracer.math.Vector3;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

/**
 * Contains methods for parsing scene data out of XML files.
 */
public class SceneParser {

    public static final String sceneDTD = new File(SceneParser.class.getResource("/scenes/scene.dtd").getFile()).getAbsolutePath();

    public static final String example1 = new File(SceneParser.class.getResource("/scenes/example1.xml").getFile()).getAbsolutePath();
    public static final String example2 = new File(SceneParser.class.getResource("/scenes/example2.xml").getFile()).getAbsolutePath();
    public static final String example3 = new File(SceneParser.class.getResource("/scenes/example3.xml").getFile()).getAbsolutePath();
    public static final String example4 = new File(SceneParser.class.getResource("/scenes/example4.xml").getFile()).getAbsolutePath();
    public static final String example5 = new File(SceneParser.class.getResource("/scenes/example5.xml").getFile()).getAbsolutePath();
    public static final String example6 = new File(SceneParser.class.getResource("/scenes/example6.xml").getFile()).getAbsolutePath();

    public static final String debugScene = new File(SceneParser.class.getResource("/scenes/debugScene.xml").getFile()).getAbsolutePath();

    public static final String dtdtest = new File(SceneParser.class.getResource("/scenes/dtd-test.xml").getFile()).getAbsolutePath();


    /**
     * Parses a scene out of an XML file.
     * @param xmlPath The full path of the XML file.
     * @return the scene parsed out of the file
     */
    public static Scene parseXML(String xmlPath) {

        Scene scene = new Scene();

        try {
            // ============================ SETUP ========================== //
            File xmlFile = new File(xmlPath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setValidating(true); // validate against schema TODO
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            // ============================ ROOT ========================== //
            // get the output file attribute from the <scene> root node
            scene.setOutputFileName(doc.getDocumentElement().getAttribute("output_file"));

            // ========================== BACKGROUND COLOR ======================== //
            // we know there is only one
            Element bgcolor = (Element) doc.getElementsByTagName("background_color").item(0);
            Color background = new Color(
                    Float.valueOf(bgcolor.getAttribute("r")),
                    Float.valueOf(bgcolor.getAttribute("b")),
                    Float.valueOf(bgcolor.getAttribute("g"))
            );
            scene.setBackgroundColor(background);


            // ============================ CAMERA ========================== //
            // there can only be one, once again
            Element cam = (Element) doc.getElementsByTagName("camera").item(0);

            // get each child node
            Element camPos = (Element) cam.getElementsByTagName("position").item(0);
            Element camLookat = (Element) cam.getElementsByTagName("lookat").item(0);
            Element camUp = (Element) cam.getElementsByTagName("up").item(0);
            Element camFov = (Element) cam.getElementsByTagName("horizontal_fov").item(0);
            Element camRes = (Element) cam.getElementsByTagName("resolution").item(0);
            Element camBounces = (Element) cam.getElementsByTagName("max_bounces").item(0);

            // create a new camera and add it to the scene
            Camera camera = new Camera(
                    parseVec3(camPos),
                    parseVec3(camLookat),
                    parseVec3(camUp),
                    Double.valueOf(camFov.getAttribute("angle")),
                    Integer.valueOf(camRes.getAttribute("horizontal")),
                    Integer.valueOf(camRes.getAttribute("vertical")),
                    Integer.valueOf(camBounces.getAttribute("n"))
            );
            scene.setCamera(camera);


            // ============================ LIGHTS ========================== //
            Element lights = (Element) doc.getElementsByTagName("lights").item(0);
            NodeList allLights = lights.getChildNodes();

            for (int i = 0; i < allLights.getLength(); i++) {
                Node node = allLights.item(i);
                Element color, direction, position, falloff = null;

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element light = (Element) node;

                    color = (Element) light.getElementsByTagName("color").item(0);

                    switch (light.getNodeName()) {
                        case "ambient_light":
                            scene.setAmbientLight(parseColor(color));
                            break;
                        case "parallel_light":
                            direction = (Element) light.getElementsByTagName("direction").item(0);
                            ParallelLight parallelLight = new ParallelLight(
                                    parseColor(color),
                                    parseVec3(direction)
                            );
                            scene.addLight(parallelLight);
                            break;
                        case "point_light":
                            position = (Element) light.getElementsByTagName("position").item(0);
                            PointLight pointLight = new PointLight(
                                    parseColor(color),
                                    parseVec3(position)
                            );
                            scene.addLight(pointLight);
                            break;
                        case "spot_light":
                            position = (Element) light.getElementsByTagName("position").item(0);
                            direction = (Element) light.getElementsByTagName("direction").item(0);
                            falloff = (Element) light.getElementsByTagName("falloff").item(0);
                            SpotLight spotLight = new SpotLight(
                                    parseColor(color),
                                    parseVec3(position),
                                    parseVec3(direction),
                                    Double.valueOf(falloff.getAttribute("alpha1")),
                                    Double.valueOf(falloff.getAttribute("alpha2"))
                            );
                            scene.addLight(spotLight);
                            break;
                    }
                }
            }

            // ============================ SURFACES & Mats ========================== //
            Element surfaces = (Element) doc.getElementsByTagName("surfaces").item(0);

            // first, check for spheres
            NodeList spheres = surfaces.getElementsByTagName("sphere");
            for (int i = 0; i < spheres.getLength(); i++) {
                Node node = spheres.item(i);
                Element position;
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element sphere = (Element) node;
                    position = (Element) sphere.getElementsByTagName("position").item(0);

                    // there may be at most one material
                    Material material = parseMaterial(sphere);

                    // there may be at most one transformation
                    Transformation transform = parseTransform(sphere);

                    Sphere s = new Sphere(
                            material,
                            transform,
                            parseVec3(position),
                            Double.valueOf(sphere.getAttribute("radius"))
                    );

                    scene.addSurface(s);
                }
            }

            // next, for meshes

            // generate a list of already parsed OBJ files for performance increase
            Map<String, ArrayList<TriangleFace>> parsedOBJs = new HashMap<>();

            NodeList meshes = surfaces.getElementsByTagName("mesh");
            for (int i = 0; i < meshes.getLength(); i++) {
                Node node = meshes.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    // parse out all data from the xml
                    Element mesh = (Element) node;
                    Material material = parseMaterial(mesh);
                    Transformation transformation = parseTransform(mesh);

                    // parse out the mesh information from the OBJ file
                    String objDir = xmlFile.getAbsolutePath().substring(
                            0, xmlFile.getAbsolutePath().lastIndexOf(File.separator)
                    );

                    String objName = mesh.getAttribute("name");
                    ArrayList<TriangleFace> objData;
                    if (!parsedOBJs.containsKey(objName)) {
                        objData = ObjParser.parseObj(objDir+File.separator+objName);
                        parsedOBJs.put(objName, objData);
                    } else {
                        objData = parsedOBJs.get(objName);
                    }

                    //System.out.println("OBJ @ " + objDir + File.separator + mesh.getAttribute("name"));
                    Mesh m = new Mesh(
                            parseMaterial(mesh),
                            parseTransform(mesh),
                            objName,
                            objData
                    );

                    scene.addSurface(m);
                }
            }


        } catch (IOException | NumberFormatException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(),e.getCause());
        }

        return scene;
    }

    private static Vector3 parseVec3(Element element) {
        return new Vector3(
                Double.valueOf(element.getAttribute("x")),
                Double.valueOf(element.getAttribute("y")),
                Double.valueOf(element.getAttribute("z"))
        );
    }

    private static Color parseColor(Element element) {
        return new Color(
                Float.valueOf(element.getAttribute("r")),
                Float.valueOf(element.getAttribute("g")),
                Float.valueOf(element.getAttribute("b"))
        );
    }

    private static Material parseMaterial(Element element) {
        Element material = (Element) element.getElementsByTagName("material_solid").item(0);

        if (material == null) {
            material = (Element) element.getElementsByTagName("material_textured").item(0);
            if (material.getNodeName() == null) throw new RuntimeException("Unknown material!");
        }

        Element color = (Element) material.getElementsByTagName("color").item(0);
        Element texture = (Element) material.getElementsByTagName("texture").item(0);
        Element phong = (Element) material.getElementsByTagName("phong").item(0);
        Element reflectance = (Element) material.getElementsByTagName("reflectance").item(0);
        Element transmittance = (Element) material.getElementsByTagName("transmittance").item(0);
        Element refraction = (Element) material.getElementsByTagName("refraction").item(0);

        Phong phongIllum = new Phong(
                Double.valueOf(phong.getAttribute("ka")),
                Double.valueOf(phong.getAttribute("kd")),
                Double.valueOf(phong.getAttribute("ks")),
                Double.valueOf(phong.getAttribute("exponent"))
        );

        if (material.getNodeName().equals("material_solid")) return new SolidMaterial(
                phongIllum,
                Double.valueOf(reflectance.getAttribute("r")),
                Double.valueOf(transmittance.getAttribute("t")),
                Double.valueOf(refraction.getAttribute("iof")),
                parseColor(color)
        );
        else if (material.getNodeName().equals("material_textured")) return new TexturedMaterial(
                phongIllum,
                Double.valueOf(reflectance.getAttribute("r")),
                Double.valueOf(transmittance.getAttribute("t")),
                Double.valueOf(refraction.getAttribute("iof")),
                texture.getAttribute("name")
        );

        throw new RuntimeException("Unknown Material Type!");
    }

    private static Transformation parseTransform(Element element) {
        Element trafos = (Element) element.getElementsByTagName("transform").item(0);
        if (trafos == null) return null;

        Element translate = (Element) trafos.getElementsByTagName("translate").item(0);
        Element scale = (Element) trafos.getElementsByTagName("scale").item(0);
        Element rotateX = (Element) trafos.getElementsByTagName("rotateX").item(0);
        Element rotateY = (Element) trafos.getElementsByTagName("rotateY").item(0);
        Element rotateZ = (Element) trafos.getElementsByTagName("rotateZ").item(0);

        return new Transformation(
                translate == null ? null : parseVec3(translate),
                scale == null ? null : parseVec3(scale),
                new Vector3(
                        rotateX == null ? 0 : Double.valueOf(rotateX.getAttribute("theta")),
                        rotateY == null ? 0 : Double.valueOf(rotateY.getAttribute("theta")),
                        rotateZ == null ? 0 : Double.valueOf(rotateZ.getAttribute("theta"))
                )
        );
    }

    public static void main(String[] args) {
        Scene scene = SceneParser.parseXML(example3);
        System.out.println(scene.toString());
    }
}
