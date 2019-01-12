package raytracer.graphics;

import raytracer.image.Resolution;
import raytracer.math.Vector3;

// TUTORIALS:
// Shirley/Marsh
// https://www.scratchapixel.com/lessons/3d-basic-rendering/ray-tracing-generating-camera-rays/generating-camera-rays
public class Camera {

    /**
     * Location of the camera in world coordinates.
     */
    private Vector3 position;
    /**
     * Which point in world space the camera is looking at.
     */
    private Vector3 lookAt; // VPN
    /**
     * Defines which direction is "up".
     */
    private Vector3 up; // VUP
    /**
     * Angle (in degrees) specifying half the angle from the left side of the screen to the right.
     * Used in spacing out the rays for rendering.
     */
    private Double horizontalFOV;
    /**
     * The pixel resolution of the final output image.
     */
    private Resolution res;
    /**
     * The maximum number of bounces for reflection and refraction.
     */
    private int maxBounces;

    private static final double imPlaneDistance = -1; // "focal length"

    // private CameraMode camMode = CAMERAMODE_PERSPECTIVE;


    public Ray generateRay(double u, double v) {

        Vector3 view = w().multiply(imPlaneDistance*d()).add(u().multiply(u)).add(v().multiply(v));

        // PERSPECTIVE
        return new Ray(
                position,
                view
        );
    }

    // raster space to world space
    public Ray generateRay(int pixelX, double pixelY) {
        // first, we transform the raster space into normalized device coordinates
        // which is a raster top left [0,0]
        double xNDC = (pixelX+0.5)/res.width();
        double yNDC = (pixelY+0.5)/res.height();

        // then, we convert them into screen space: top left [-1,1] to bot right [1,-1]
        double xScreen = 2*xNDC-1;
        double yScreen = 1-2*yNDC;

        // the next step is to convert them to camera space to account for different
        // aspect ratios, as well as the FoV
        // The camera coordinate system is called so because the raster coordinates are
        // now expressed through the camera coordinate system.
        // Assuming the camera is in default position, this CS and the world CS are aligned!
        double xCamera = xScreen * res.aspectRatio() * Math.tan(Math.toRadians(horizontalFOV));
        double yCamera = yScreen * Math.tan(Math.toRadians(horizontalFOV));

        // the final coordinate on the image plane is now:
        Vector3 pixelOnPlane = new Vector3(xCamera, yCamera, imPlaneDistance);

        return new Ray(
                position,
                pixelOnPlane.subtract(position)
        );
    }


    public double imPlane_left() {
        return -res.width()/2.0;
    }

    public double imPlane_right() {
        return res.width()/2.0;
    }

    public double imPlane_top() {
        return res.height()/2.0;
    }

    public double imPlane_bottom() {
        return -res.height()/2.0;
    }

    // from raster space to screen space
    // formula: Fundamentals, pg.74
    public double imPlane_u(int pixelX) {
        double x = (double)pixelX;
        return imPlane_left() + ((imPlane_right()- imPlane_left())*(x+0.5))/res.width();
    }

    // formula: Fundamentals, pg.74
    public double imPlane_v(int pixelY) {
        double y = (double)pixelY;
        return imPlane_bottom() + ((imPlane_top()-imPlane_bottom())*(y+0.5))/res.height();
    }

    /**
     * The normalized u-basis of the orthonormal right-handed camera frame. "Points right".
     * @return "Right" of the camera
     */
    public Vector3 u() {
        return up.cross(w()).normalize();
    }

    /**
     * The normalized v-basis of the orthonormal right-handed camera frame. "Points up".
     * @return "Up" of the camera
     */
    public Vector3 v() {
        return w().cross(u()).normalize();
    }

    /**
     * The normalized w-basis of the orthonormal right-handed camera frame. "Points behind".
     * @return "Behind" the camera
     */
    public Vector3 w() {
        // our w is the inverse of where we look at (right-handed camera frame)
        return position.subtract(lookAt).normalize();
    }

    private double d() {
        return imPlane_top()/Math.tan(Math.toRadians(horizontalFOV));
    }

    private double verticalFOV() { // TODO round?
        return res.height()/res.width() * Math.toRadians(horizontalFOV);
    }

    public Camera() {
        this.position = new Vector3(0,0,0);
        this.lookAt = new Vector3(0,0,-1);
        this.up = new Vector3(0, 1, 0);
        this.horizontalFOV = 45.0;
        this.res = new Resolution(10, 10);
        this.maxBounces = 5;
    }

    public Camera(int resX, int resY) {
        this.position = new Vector3(0,0,0);
        this.lookAt = new Vector3(0,0,-1);
        this.up = new Vector3(0, 1, 0);
        this.horizontalFOV = 45.0;
        this.res = new Resolution(resX, resY);
        this.maxBounces = 5;
    }

    public Camera(Vector3 position, Vector3 lookAt, Vector3 up, double horizontalFOV, int resX, int resY, int maxBounces) {
        this.position = position;
        this.lookAt = lookAt;
        this.up = up;
        this.horizontalFOV = horizontalFOV;
        this.res = new Resolution(resX, resY);
        this.maxBounces = maxBounces;
    }

    @Override
    public String toString() {
        return
                "raytracer.graphics.Camera[position= " + position.toString()
                + ", lookAt= " + lookAt.toString()
                + ", up= " + up.toString()
                + ", horizontalFov= " + horizontalFOV
                + ", resolution= " + res.getHorizontal() + "x" + res.getVertical()
                + ", maxBounces= " + maxBounces
                ;
    }

    public static void main(String[] args) {
        Camera cam = new Camera(100,100);
        System.out.println(cam.v());
    }

    public Vector3 getPosition() {
        return position;
    }

    public Vector3 getLookAt() {
        return lookAt;
    }

    public Vector3 getUp() {
        return up;
    }

    public Double getHorizontalFOV() {
        return horizontalFOV;
    }

    public Resolution getRes() {
        return res;
    }

    public int getMaxBounces() {
        return maxBounces;
    }
}
