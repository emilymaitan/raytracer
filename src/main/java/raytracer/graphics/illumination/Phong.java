package raytracer.graphics.illumination;

import raytracer.math.Vector3;

import java.awt.Color;

/**
 * Contains data and methods for Phong illumination.
 */
public class Phong {

    /**
     * The ambient component.
     * Ambient light is a very distant and omnipresent light - like a "base tone".
     */
    private double kAmbient;
    /**
     * The diffuse component.
     * Diffuse light "scatters rays" and gives a flat look - like matt fabric.
     */
    private double kDiffuse;
    /**
     * The specular component.
     * Specular light "bundles rays" and gives a shiny look - like a circular highlight.
     */
    private double kSpecular;
    /**
     * The Phong cosine power for highlights.
     * The higher this is, the slimmer is the specular spot.
     */
    private double exponent;

    /**
     * Calculates ambient-only phong illumination.
     * @param color the ambient light color
     * @return ambient phong
     */
    public Color computeAmbient(Color color) {
        return new Color(
                (int)Math.round(Math.max(0, Math.min(255, color.getRed()*kAmbient))),
                (int)Math.round(Math.max(0, Math.min(255, color.getGreen()*kAmbient))),
                (int)Math.round(Math.max(0, Math.min(255, color.getBlue()*kAmbient)))
        );
    }

    /**
     * Calculates a color according to full Phong illumination.
     * Note of trivia, this is the same code as in my javascript shaders :)
     * @param color The base color.
     * @param surfToLight A vector from the surface to the light source.
     * @param surfNorm The surface normal at the point of illumination.
     * @return Color in Phong illumination.
     */
    public Color computeIllumination(Color color, Vector3 surfToLight, Vector3 surfNorm, boolean includeAmbient) {
        Vector3 result = new Vector3();
        Vector3 col = new Vector3((double)color.getRed(), (double)color.getGreen(), (double)color.getBlue());

        // For ambient light, no directions matter - it's a flat factor on the color.
        if (includeAmbient) result = result.add(col.multiply(kAmbient));

        // For diffuse light, a surface becomes brighter the closer the incoming light is to its normal.
        // The dot is 1 when they are equal (very bright) and 0 when they're perpendicular.
        double diffuseFactor = Math.max(0.0, surfNorm.dot(surfToLight));
        result = result.add(col.multiply(diffuseFactor));

        // Specular light bases on the idea that a surface reflects light at a certain angle.
        // If this reflected light hits the camera, we see a specular highlight.
        // Reflected Vector = 2*dot(n,l)*n - l
        Vector3 reflectionVector = surfNorm.multiply(2*surfNorm.dot(surfToLight)).subtract(surfToLight).normalize();
        double specularFactor = Math.pow(Math.max(0, reflectionVector.dot(surfToLight)), exponent);
        result = result.add(col.multiply(specularFactor));

        return new Color(
                (int)Math.round(Math.max(0, Math.min(255, result.getX()))),
                (int)Math.round(Math.max(0, Math.min(255, result.getY()))),
                (int)Math.round(Math.max(0, Math.min(255, result.getZ())))
        );
    }

    public Phong() {
        kAmbient = 0;
        kDiffuse = 0;
        kSpecular = 0;
        exponent = 1;
    }

    public Phong(double kAmbient, double kDiffuse, double kSpecular, double exponent) {
        this.kAmbient = kAmbient;
        this.kDiffuse = kDiffuse;
        this.kSpecular = kSpecular;
        this.exponent = exponent;
    }

    @Override
    public String toString() {
        return "raytracer.graphics.illumination.Phong[" +
                "kAmbient=" + kAmbient +
                ", kDiffuse=" + kDiffuse +
                ", kSpecular=" + kSpecular +
                ", exponent=" + exponent +
                ']';
    }
}
