package raytracer.math;

import java.util.Arrays;

/**
 * A utility class for math operations.
 */
public class MathUtils {

    /**
     * A small number usually used to account for floating point errors.
     */
    public static final double EPSILON = 0.0005;

    /**
     * Solves equations of the form ax^2 + bx + c for x with the formula:
     * x1,2 = (-b+-sqrt(b^2-4ac))/(2a)
     * @param a a
     * @param b b
     * @param c c
     * @return A sorted array containing all solutions for this problem. May be empty.
     */
    public static double[] solveQuadratic (double a, double b, double c) { // TODO unit test
        double[] result = null;

        if (a == 0) // we cannot divide by 0, there is no solution
            return new double[0];

        // calculate the discriminant (= the term under the root)
        // to find out how many solutions we have
        double discriminant = b*b - 4*a*c;
        if (discriminant < 0) { // there is no solution
           return new double[0];
        } else if (discriminant == 0) { // there is exactly one solution
            result = new double[1];
            result[0] = -(b/(2*a));
        } else { // there are two solutions
            result = new double[2];
            result[0] = (-b+Math.sqrt(discriminant))/2*a;
            result[1] = (-b-Math.sqrt(discriminant))/2*a;
        }

        // sort the results by ascending (=increasing) order
        Arrays.sort(result);

        return result;
    }
}
