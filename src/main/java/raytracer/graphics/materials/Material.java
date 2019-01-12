package raytracer.graphics.materials;

import raytracer.graphics.illumination.Phong;

import java.awt.*;

public abstract class Material {

    /**
     * Coefficients for phong illumination.
     */
    protected Phong phong;
    /**
     * The fraction of the contribution of the reflected ray.
     */
    protected double reflectance;
    /**
     * The fraction of the contribution of the transmitted ray.
     */
    protected double transmittance;
    /**
     * Index of refraction.
     */
    protected double refraction;

    public abstract Color getMaterialcolor();

    public Material() {
        phong = new Phong();
        reflectance = 0;
        transmittance = 0;
        refraction = 0;
    }

    public Material(Phong phong, double reflectance, double transmittance, double refraction) {
        this.phong = phong;
        this.reflectance = reflectance;
        this.transmittance = transmittance;
        this.refraction = refraction;
    }

    public Phong getPhong() {
        return phong;
    }

    public double getReflectance() {
        return reflectance;
    }

    public double getTransmittance() {
        return transmittance;
    }

    public double getRefraction() {
        return refraction;
    }
}
