package raytracer.graphics.lights;

import raytracer.math.Vector3;

import java.awt.*;

public class PointLight extends Light {

    private Vector3 position;

    public Vector3 getPosition() {
        return position;
    }

    public PointLight(Color color, Vector3 position) {
        super(color);
        this.position = position;
    }

    @Override
    public String toString() {
        return "raytracer.graphics.lights.PointLight[color=" + color + ", pos=" + position.toString() + "]";
    }
}
