package raytracer;

import raytracer.graphics.Camera;
import raytracer.graphics.Ray;
import raytracer.graphics.Scene;
import raytracer.graphics.illumination.Phong;
import raytracer.graphics.lights.ParallelLight;
import raytracer.graphics.lights.PointLight;
import raytracer.graphics.lights.SpotLight;
import raytracer.graphics.materials.SolidMaterial;
import raytracer.graphics.surfaces.Sphere;
import raytracer.graphics.surfaces.Surface;
import raytracer.graphics.trafo.Transformation;
import raytracer.io.ImageWriter;
import raytracer.io.SceneParser;
import raytracer.math.MathUtils;
import raytracer.math.Vector3;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

public class Raytracer {


    // Syntax: raytracer {file <'path/to/file.xml'> | scene {debug|example1|example2} | color}
    public static void main(String[] args) {

        String scenefile = null;
        boolean renderColor = false;

        if (args.length == 0) {
            System.out.println("Raytracer: Help");
            System.out.println(" Render an input file: 'gradle run file <filename>");
            System.out.println(" Render an example scene: 'gradle run scene {example1|example2}");
            System.out.println(" Render rays as 500x500 color image: 'gradle run color");
        } else if (args.length == 2) {
            if (args[0].equals("file")) { // check if file exists
                File file = new File(args[1]);
                if(file.exists() && !file.isDirectory()) scenefile = args[1];
                else {
                    System.out.println(" WARNING: File not found: " + file.getAbsolutePath());
                    System.out.println(" Falling back to debug scene.");
                    scenefile = SceneParser.debugScene;
                }
            } else if (args[0].equals("scene")) {
                switch (args[1]) {
                    case "example1": scenefile = SceneParser.example1; break;
                    case "example2": scenefile = SceneParser.example2; break;
                }
            }
        } else if (args.length == 1) {
            if (args[0].equals("color")) {
                renderColor = true;
            }
        }

        if(renderColor) {
            System.out.println("Now rendering: rayToColor...");
            BufferedImage col = renderRaycolorAsImage(500, 500);
            ImageWriter.writeImage(col, "png", "rayToColor");
            return;
        }

        if (scenefile == null) { // fallback default
            scenefile = SceneParser.debugScene;
            System.out.printf("Now rendering: %s... (fallback default)%n", scenefile);
        } else
            System.out.printf("Now rendering: %s...%n", scenefile);

        Scene scene = SceneParser.parseXML(scenefile);
        BufferedImage image = renderSceneAsImage(scene);
        ImageWriter.writeImage(
                image,
                "png",
                scene.getOutputFileName());
    }

    // debug main method
    /*
    public static void main(String[] args) {
        Scene scene = SceneParser.parseXML(SceneParser.debugScene);
        //BufferedImage image = renderRaycolorAsImage(scene.getCamera());
        BufferedImage image = renderSceneAsImage(scene);
        ImageWriter.writeImage(image, "png", "scene");
    }
    */

    public static BufferedImage renderRaycolorAsImage(int resX, int resY) {
        return renderRaycolorAsImage(new Camera(resX, resY));
    }

    public static BufferedImage renderRaycolorAsImage(Camera camera) {
        BufferedImage image = new BufferedImage(
                camera.getRes().getHorizontal(),
                camera.getRes().getVertical(),
                BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < camera.getRes().getHorizontal(); i++) {
            for (int j = 0; j < camera.getRes().getVertical(); j++) {

                double u = camera.imPlane_u(i);
                double v = camera.imPlane_v(j);

                //Ray ray = camera.generateRay(u,v);
                Ray ray = camera.generateRay(i, j);

                Color color = ray.toColor(1);

                image.setRGB(i, j, color.getRGB());
                //System.out.println("Ray: " + ray.getDirection().toString() + "| Color: " + color.toString());
            }
        }

        return image;
    }

    // image-order rendering
    // Resources: Shirley, Marsh: Fundamentals of Computer Graphics, Chp.4 - Ray Tracing
    public static BufferedImage renderSceneAsImage(Scene scene) {

        Camera camera = scene.getCamera();

        // create the image we render into
        BufferedImage image = new BufferedImage(
                camera.getRes().getHorizontal(),
                camera.getRes().getVertical(),
                BufferedImage.TYPE_INT_ARGB);

        // TODO remove these
        //scene.getSurfaces().get(1).getTransformation().translateBy(new Vector3(1,1,-1));
        //scene.getSurfaces().get(1).getTransformation().setScale(new Vector3(2,2,2));
        //scene.getCamera().transformation.translateBy(new Vector3(0,0,5));

        // for each pixel in this image, do...
        for (int i = 0; i < camera.getRes().getHorizontal(); i++) {
            for (int j = 0; j < camera.getRes().getVertical(); j++) {
                // get a ray from the camera to this pixel
                Ray ray = camera.generateRay(i, j);
                //Ray ray = camera.generateRay(camera.imPlane_u(i),camera.imPlane_v(j));

                // trace this ray
                Color color = traceRay(scene, ray, 0, scene.getCamera().getPosition());
                //Color color = traceDebugSphere(ray);

                // store the resulting color in the pixel
                image.setRGB(i, j, color.getRGB());
            }
        }

        return image;
    }

    @Deprecated
    public static Color traceDebugSphere(Ray ray) {
        Color color = Color.BLACK;

        Sphere sphere = new Sphere(
                new SolidMaterial(
                        new Phong(),
                        1,
                        1,
                        1,
                        Color.CYAN
                ),
                new Transformation(
                        new Vector3(0.0,0.0,0.0), // translation
                        new Vector3(1,1,1), // scale
                        new Vector3(0.0,0.0,0.0)  // rotation
                ),
                new Vector3(0,0,-3),
                1
        );

        double t = Double.MAX_VALUE-1;
        double tt = sphere.intersect(ray);
        if ((tt > 0) && (tt < t)) {
            color = ((SolidMaterial)sphere.getMaterial()).getColor();
        }

        return color;
    }

    // inspiration for this from here: https://www.youtube.com/watch?v=m_IeoWvSbQI
    // and the tutorial
    public static Color traceRay(Scene scene, Ray ray, int depth, Vector3 currentView) {
        //initialize the color to black
        Color color = Color.BLACK;

        // Check how deep into the recursion we are, and abort if we must.
        if (depth > scene.getCamera().getMaxBounces()) return color;

        // find closest intersection i
        // For all objects in the scene, do an intersection test.
        double t = Double.MAX_VALUE-1; // the t-parameter of the ray where we intersect
        Surface closestSurface = null;

        for (Surface s : scene.getSurfaces()) {
            double tt = s.intersect(ray);
            // if this new value is in front of our ray (that is, positive)
            // and if it is smaller than t, we have a new closest object!
            if ((tt > 0) && (tt < t)) {
                closestSurface = s;
                t = tt;
            }
        }

        if (closestSurface == null) { // if no intersection was found
            // add the background color
            return scene.getBackgroundColor();
            // return ray.toColor(1); // ray direction colored background for debugging
        }

        // An intersection exists! Now, we compute the color.
        // For this, we need to know the position where we intersected the element:
        Vector3 intersectionPos = ray.at(t);
        // todo: transform intersection position from object to world space

        // Pre-calculate important variables at the reflection point.
        Vector3 surfaceNormal = closestSurface.surfaceNormal(intersectionPos);
        Vector3 surfToView = currentView.subtract(intersectionPos).normalize();

        // We need to sum up red, green and blue over all lights:
        int r = 0, g = 0, b = 0;

        // First, factor in the ambient light.
        Color amb = closestSurface.illuminateAmbient(intersectionPos);
        r += amb.getRed();
        g += amb.getGreen();
        b += amb.getBlue();

        // Basic Phong: Find each light source and compute the phong illumination.
        for (int i = 0; i < scene.getLights().size(); i++) {
            // Compute the required vectors for phong illumination:
            Vector3 surfaceToLight = null;

            // Which kind of light source is it?
            if (scene.getLights().get(i) instanceof ParallelLight) {
                ParallelLight light = (ParallelLight)scene.getLights().get(i);
                surfaceToLight = light.getDirection().multiply(-1).normalize(); // inverse
            } else if (scene.getLights().get(i) instanceof PointLight) {
                PointLight light = (PointLight)scene.getLights().get(i);
                surfaceToLight = light.getPosition().subtract(intersectionPos).normalize();
            } else if (scene.getLights().get(i) instanceof SpotLight) {
                SpotLight light = (SpotLight)scene.getLights().get(i);
                // TODO implement this properly
                surfaceToLight = light.getPosition().subtract(intersectionPos).normalize();
            } else throw new RuntimeException("Unknown light type!");

            // Are we shadowed?
            // We start from a slightly offset position so that we don't get shadowed by "ourself".
            Vector3 ipOffset = intersectionPos.add(surfaceNormal.multiply(MathUtils.EPSILON));
            Ray shadowRay = new Ray(ipOffset, surfaceToLight);
            boolean shadowed = false;
            for (Surface s : scene.getSurfaces()) {
                double tt = s.intersect(shadowRay);
                if (tt > 0 && tt < Double.MAX_VALUE) {
                    shadowed = true; break; // TODO adjust for translucent things
                }
            }

            // if (shadowed) return Color.CYAN; // colored shadow for debugging
            if (shadowed) continue;

            // Compute color
            Color phong = closestSurface.illuminate(
                    scene.getLights().get(i).getColor(),
                    intersectionPos,
                    surfaceToLight,
                    surfaceNormal,
                    surfToView,
                    scene.getAmbientLight() == null
            );

            r += phong.getRed();
            g += phong.getGreen();
            b += phong.getBlue();
        }

        // if reflectivity > 0
        if (closestSurface.getMaterial().getReflectance() > 0) {
            // ############# compute reflected ray #############
            // Perfect reflection formula: 2*(n.l)n - l
            Vector3 reflDir = surfaceNormal.multiply(
                    2*Math.max(0, surfaceNormal.dot(surfToView))
            ).subtract(surfToView).normalize();

            Ray reflRay = new Ray(
                    intersectionPos.add(reflDir.multiply(MathUtils.EPSILON)),
                    reflDir
            );

            // ############# Trace it #############
            // Now we trace this ray - recursively!
            Color reflColor = traceRay(scene, reflRay, depth+1, reflRay.getOriginPoint());

            // ############# add contribution to output #############
            float rf = (float)closestSurface.getMaterial().getReflectance();
            // todo review this if necessary
            r = Math.round((1-rf)*r + rf*reflColor.getRed());
            g = Math.round((1-rf)*g + rf*reflColor.getGreen());
            b = Math.round((1-rf)*b + rf*reflColor.getBlue());
        }

        // TODO: if transmittivity > 0
        if (closestSurface.getMaterial().getTransmittance() > 0) {

        }


        return new Color(
                Math.max(0, Math.min(255, r)),
                Math.max(0, Math.min(255, g)),
                Math.max(0, Math.min(255, b))
        );
    }
}
