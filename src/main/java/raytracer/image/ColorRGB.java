package raytracer.image;

import java.awt.Color;

@Deprecated
public class ColorRGB {

    private Double red;
    private Double green;
    private Double blue;

    public static final Double COLOR_DEPTH = 255.0;

    public static final ColorRGB COLOR_BLACK = new ColorRGB(0,0,0);

    public int getRGB() {
        Color col = new Color (
                Math.round(red),
                Math.round(green),
                Math.round(blue)
        );
        return col.getRGB();
    }

    public ColorRGB add(double red, double green, double blue) {
        return new ColorRGB(
                Math.min(this.red + red, COLOR_DEPTH),
                Math.min(this.green + green, COLOR_DEPTH),
                Math.min(this.blue + blue, COLOR_DEPTH)
        );
    }

    public ColorRGB subtract(double red, double green, double blue) {
        return new ColorRGB(
                Math.max(this.red - red, 0),
                Math.max(this.green - green, 0),
                Math.max(this.blue - blue, 0)
        );
    }

    public ColorRGB normalize() {
        return new ColorRGB(
                red / COLOR_DEPTH,
                blue / COLOR_DEPTH,
                green / COLOR_DEPTH
        );
    }

    public ColorRGB deNormalize() {
        return new ColorRGB(
                red * COLOR_DEPTH,
                green * COLOR_DEPTH,
                blue * COLOR_DEPTH
        );
    }

    public ColorRGB(double red, double green, double blue) {
        this.red = Math.max(0, Math.min(red, COLOR_DEPTH));
        this.green = Math.max(0, Math.min(green, COLOR_DEPTH));
        this.blue = Math.max(0, Math.min(blue, COLOR_DEPTH));
    }

    @Override
    public String toString() {
        return String.format("raytracer.image.ColorRGB[r=%f|g=%f|b=%f]", red, green, blue);
    }
}
