package raytracer.graphics.materials;

import raytracer.graphics.illumination.Phong;
import raytracer.graphics.surfaces.Surface;
import raytracer.math.Vector3;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TexturedMaterial extends Material {

    private String textureName;
    private BufferedImage texture;

    // Shirley, Marshner pg.244
    private Color textureLookup(float u, float v) {
        int i = Math.round(u* texture.getWidth() -0.5f);
        int j = Math.round(v * texture.getHeight() - 0.5f);
        return new Color(texture.getRGB(i, j));
    }

    public TexturedMaterial(Phong phong, double reflectance, double transmittance, double refraction, String textureName, BufferedImage texture) {
        super(phong, reflectance, transmittance, refraction);
        this.textureName = textureName;
        this.texture = texture;
    }

    @Override
    public Color getMaterialColor(Surface s, Vector3 at) {
        float[] uv = s.getTextureCoordinates(at);
        return textureLookup(uv[0],uv[1]);
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
