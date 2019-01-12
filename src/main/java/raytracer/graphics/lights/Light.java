package raytracer.graphics.lights;

import java.awt.*;

public class Light {

    protected Color color;

    public Color getColor() {
        return color;
    }

    public Light() {
        this.color = Color.WHITE;
    }

    public Light(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "raytracer.graphics.lights.ParallelLight[color=" + color + "]";
    }
}