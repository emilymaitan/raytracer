package raytracer.math;

public class Vector3 {

    /**
     * The x-value of the vector.
     */
    private double x;
    /**
     * The y-value of the vector.
     */
    private double y;
    /**
     * The z-value of the vector.
     */
    private double z;

    /**
     * Adds two vectors.
     * @param vector the addend
     * @return a new vector
     */
    public Vector3 add (Vector3 vector) {
        return new Vector3(
                this.x + vector.x,
                this.y + vector.y,
                this.z + vector.z
        );
    }

    /**
     * Subtracts two vectors.
     * @param vector the subtrahend
     * @return a new vector
     */
    public Vector3 subtract (Vector3 vector) {
        return new Vector3(
                this.x - vector.x,
                this.y - vector.y,
                this.z - vector.z
        );
    }

    /**
     * Multiplies a vector by a scalar (element-wise).
     * @param scalar the scalar
     * @return a new scaled vector
     */
    public Vector3 multiply (double scalar) {
        return new Vector3(
                this.x * scalar,
                this.y * scalar,
                this.z * scalar
        );
    }

    /**
     * Divides a vector by a scalar (element-wise).
     * @param scalar the scalar (must not be null)
     * @return a new scaled vector
     */
    public Vector3 divide (double scalar) {
        return new Vector3(
                this.x / scalar,
                this.y / scalar,
                this.z / scalar
        );
    }

    /**
     * Calculates the dot product of two vectors.
     * @param vector other
     * @return the dot product
     */
    public double dot (Vector3 vector) {
        return this.x * vector.x +
                this.y * vector.y +
                this.z * vector.z;
    }

    /**
     * Creates the cross product of two vectors.
     * @param vector other
     * @return a vector orthogonal to both operands
     */
    public Vector3 cross (Vector3 vector) {
        return new Vector3(
                this.y*vector.z - this.z*vector.y,
                this.z*vector.x - this.x*vector.z,
                this.x*vector.y - this.y*vector.x
        );
    }

    /**
     * Normalizes a vector.
     * @return the normalized vector (length = 1)
     * @throws RaytracerMathException If length = 0.
     */
    public Vector3 normalize() throws RaytracerMathException {
        double length = this.length();

        if (length == 0) throw new RaytracerMathException("Cannot normalize Vector of size 0!");

        return new Vector3(
                this.x / length,
                this.y / length,
                this.z / length
        );
    }

    /**
     * Returns the length of a vector.
     * @return the absolute length
     */
    public double length () {
        return Math.sqrt(x*x + y*y + z*z);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public Vector3() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(Vector3 vector) {
        this.x = vector.x;
        this.y = vector.y;
        this.z = vector.z;
    }

    public Vector3(Vector4 vec) {
        this.x = vec.getA();
        this.y = vec.getB();
        this.z = vec.getC();
    }

    @Override
    public boolean equals(Object obj) {
        // if the other object is null, they are not equal
        if (obj == null) return false;
        // if the other object can not be assigned to this class, then not equal
        if(!Vector3.class.isAssignableFrom(obj.getClass())) return false;

        // finally, it depends whether their values are equal
        Vector3 _obj = (Vector3)obj;
        return this.x == _obj.getX()
                && this.y == _obj.getY()
                && this.z == _obj.getZ();
    }

    @Override
    public String toString() {
        return String.format("raytracer.math.Vector3[(%.4f | %.4f | %.4f)]", x, y, z);
    }
}
