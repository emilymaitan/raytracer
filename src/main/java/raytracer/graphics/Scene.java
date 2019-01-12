package raytracer.graphics;

import raytracer.graphics.lights.Light;
import raytracer.graphics.surfaces.Surface;

import java.awt.Color;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Scene {

    private String outputFileName;
    private Camera camera;
    private Color backgroundColor;
    private Color ambientLight = null;
    private ArrayList<Light> lights = new ArrayList<>();
    private ArrayList<Surface> surfaces = new ArrayList<>();


    public void addLight(Light light) {
        lights.add(light);
    }

    public void addSurface(Surface surface) {
        surfaces.add(surface);
    }

    @Override
    public String toString() {
        return "raytracer.graphics.Scene" +
                "output: " + outputFileName + "\n" +
                "camera: " + camera.toString() + "\n" +
                "background color: " + backgroundColor.toString() + "\n" +
                "ambient light: " + ambientLight.toString() + "\n" +
                "lights: " +  lights.stream().map(Object::toString).collect(Collectors.joining(", ")) + "\n" +
                "surfaces: " + surfaces.stream().map(Object::toString).collect(Collectors.joining(", "))
                ;
    }

    // ===================== GETTERS & SETTERS ===================== //

    public String getOutputFileName() {
        return outputFileName;
    }

    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Color getAmbientLight() {
        return ambientLight;
    }

    public void setAmbientLight(Color ambientLight) {
        this.ambientLight = ambientLight;
    }

    public ArrayList<Light> getLights() {
        return lights;
    }

    public ArrayList<Surface> getSurfaces() {
        return surfaces;
    }

    public void setLights(ArrayList<Light> lights) {
        this.lights = lights;
    }
}
