package raytracer.math;

public class Vector4 {

    private double a;
    private double b;
    private double c;
    private double d;

    /**
     * Adds two vectors.
     * @param vec the addend
     * @return a new vector
     */
    public Vector4 add(Vector4 vec) {
        return new Vector4(
                a + vec.a,
                b + vec.b,
                c + vec.c,
                d + vec.d
        );
    }

    public Vector4 subtract(Vector4 vec) {
        return new Vector4(
                a - vec.a,
                b - vec.b,
                c - vec.c,
                d - vec.d
        );
    }

    /**
     * Multiplies a vector by a scalar (element-wise).
     * @param scalar the scalar
     * @return a new scaled vector
     */
    public Vector4 multiply (double scalar) {
        return new Vector4(
                a * scalar,
                b * scalar,
                c * scalar,
                d * scalar
        );
    }

    /**
     * Calculates the dot product of two vectors.
     * @param vec other
     * @return the dot product
     */
    public double dot (Vector4 vec) {
        return a*vec.a + b*vec.b + c*vec.c + d*vec.d;
    }

    /**
     * Normalizes a vector.
     * @return the normalized vector (length = 1)
     * @throws RaytracerMathException If length = 0.
     */
    public Vector4 normalize() throws RaytracerMathException {
        double length = this.length();

        if (length == 0) throw new RaytracerMathException("Cannot normalize Vector of size 0!");

        return new Vector4(
                a / length,
                b / length,
                c / length,
                d / length
        );
    }

    /**
     * Returns the length of a vector.
     * @return the absolute length
     */
    public double length () {
        return Math.sqrt(a*a + b*b + c*c + d*d);
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }

    public double getD() {
        return d;
    }

    public void setD(double d) {
        this.d = d;
    }

    public Vector4 () {
        this.a = 0;
        this.b = 0;
        this.c = 0;
        this.d = 0;
    }

    public Vector4 (double a, double b, double c, double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public Vector4 (Vector4 vec) {
        this.a = vec.a;
        this.b = vec.b;
        this.c = vec.c;
        this.d = vec.d;
    }

    public Vector4 (Vector3 vec, double d) {
        this.a = vec.getX();
        this.b = vec.getY();
        this.c = vec.getZ();
        this.d = d;
    }

    @Override
    public boolean equals(Object obj) {
        // if the other object is null, they are not equal
        if (obj == null) return false;
        // if the other object can not be assigned to this class, then not equal
        if(!Vector4.class.isAssignableFrom(obj.getClass())) return false;

        // finally, it depends whether their values are equal
        Vector4 _obj = (Vector4)obj;
        return this.a == _obj.getA()
                && this.b == _obj.getB()
                && this.c == _obj.getC()
                && this.d == _obj.getD();
    }

    @Override
    public String toString() {
        return String.format("raytracer.math.Vector4[(%.4f | %.4f | %.4f| %.4f)]", a,b,c,d);
    }
}