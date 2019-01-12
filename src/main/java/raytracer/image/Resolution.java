package raytracer.image;

public class Resolution {

    private Integer horizontal;
    private Integer vertical;

    public double aspectRatio() {
        return horizontal.doubleValue()/vertical.doubleValue();
    }

    public double width() {
        return horizontal.doubleValue();
    }

    public double height() {
        return vertical.doubleValue();
    }

    public Resolution(Integer horizontal, Integer vertical) {
        this.horizontal = horizontal;
        this.vertical = vertical;
    }

    public Integer getHorizontal() {
        return horizontal;
    }

    public Integer getVertical() {
        return vertical;
    }

    @Override
    public String toString() {
        return String.format("raytracer.image.Resolution[%dx%d]", horizontal, vertical);
    }
}
