package raytracer.graphics.materials;

import raytracer.graphics.illumination.Phong;
import raytracer.graphics.surfaces.Surface;
import raytracer.math.Vector3;

import java.awt.*;

public class SolidMaterial extends Material {

    private Color color;

    public SolidMaterial(Phong phong, double reflectance, double transmittance, double refraction, Color color) {
        super(phong, reflectance, transmittance, refraction);
        this.color = color;
    }

    @Override
    public Color getMaterialColor(Surface s, Vector3 at) {
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
