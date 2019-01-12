package raytracer.graphics.materials;

import raytracer.graphics.illumination.Phong;

import java.awt.*;

public class SolidMaterial extends Material {

    private Color color;

    public SolidMaterial(Phong phong, double reflectance, double transmittance, double refraction, Color color) {
        super(phong, reflectance, transmittance, refraction);
        this.color = color;
    }

    @Override
    public Color getMaterialcolor() {
        return color;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "raytracer.graphics.materials.SolidMaterial[" +
                "color=" + color +
                ", phong=" + phong +
                ", reflectance=" + reflectance +
                ", transmittance=" + transmittance +
                ", refraction=" + refraction +
                ']';
    }
}
