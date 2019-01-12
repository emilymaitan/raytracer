package raytracer.graphics.materials;

import raytracer.graphics.illumination.Phong;

import java.awt.*;

public class TexturedMaterial extends Material {

    private String texture;

    public TexturedMaterial(Phong phong, double reflectance, double transmittance, double refraction, String texture) {
        super(phong, reflectance, transmittance, refraction);
        this.texture = texture;
    }

    @Override
    public Color getMaterialcolor() {
        return null; // TODO do some texture calculations
    }

    @Override
    public String toString() {
        return "raytracer.graphics.materials.TexturedMaterial[" +
                "texture='" + texture + '\'' +
                ", phong=" + phong +
                ", reflectance=" + reflectance +
                ", transmittance=" + transmittance +
                ", refraction=" + refraction +
                ']';
}
}
