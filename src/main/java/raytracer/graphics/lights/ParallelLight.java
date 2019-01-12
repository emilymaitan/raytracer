package raytracer.graphics.lights;

import raytracer.math.Vector3;

import java.awt.*;

/**
 * Defines a parallel light.
 * Akin to the sun, it is infinitely far away and only has a direction, no origin.
 */
public class ParallelLight extends Light {

    private Vector3 direction;

    public Vector3 getDirection() {
        return direction;
    }

    public ParallelLight(Color color, Vector3 direction) {
        super(color);
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "raytracer.graphics.lights.ParallelLight[color=" + color + ", pos=" + direction.toString() + "]";
    }
}
