package raytracer.graphics.lights;

import raytracer.math.Vector3;

import java.awt.*;

public class SpotLight extends Light {

    /**
     * Where this light is located.
     */
    private Vector3 position;
    /**
     * Into which direction it shines.
     */
    private Vector3 direction;
    /**
     * Defines the angle 1 for the falloff.
     * Between 0 and alpha1, the Spotlight is like a point light.
     * Between alpha1 and alpha2, the light falls off.
     * For angles greater than alpha2, there is no light.
     */
    private double falloffAlpha1;
    /**
     * Defines the angle 2 for the falloff.
     * Between 0 and alpha1, the Spotlight is like a point light.
     * Between alpha1 and alpha2, the light falls off.
     * For angles greater than alpha2, there is no light.
     */
    private double falloffAlpha2;

    public SpotLight(Color color, Vector3 position, Vector3 direction, double falloffAlpha1, double falloffAlpha2) {
        this.color = color;
        this.position = position;
        this.direction = direction;
        this.falloffAlpha1 = falloffAlpha1;
        this.falloffAlpha2 = falloffAlpha2;
    }

    @Override
    public String toString() {
        return "raytraacer.graphics.light.SpotLight[" +
                "position=" + position +
                ", direction=" + direction +
                ", falloff=[" + falloffAlpha1 + "|" + falloffAlpha2 + "]" +
                ", color=" + color +
                ']';
    }

    public Vector3 getPosition() {
        return position;
    }

    public Vector3 getDirection() {
        return direction;
    }

    public double getFalloffAlpha1() {
        return falloffAlpha1;
    }

    public double getFalloffAlpha2() {
        return falloffAlpha2;
    }
}
