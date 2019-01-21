package raytracer.graphics.lights;

import raytracer.math.Vector3;

import java.awt.*;
import java.util.Arrays;

public class SpotLight extends Light {

    private Vector3 position;
    private Vector3 direction;
    private double[] falloff; // TODO find a more efficient storetype

    public SpotLight(Color color, Vector3 position, Vector3 direction, double falloffX, double falloffY) {
        this.color = color;
        this.position = position;
        this.falloff = new double[2];
        falloff[0] = falloffX;
        falloff[1] = falloffY;
    }

    @Override
    public String toString() {
        return "raytraacer.graphics.light.SpotLight[" +
                "position=" + position +
                ", direction=" + direction +
                ", falloff=" + Arrays.toString(falloff) +
                ", color=" + color +
                ']';
    }

    public Vector3 getPosition() {
        return position;
    }

    public Vector3 getDirection() {
        return direction;
    }

    public double[] getFalloff() {
        return falloff;
    }
}
