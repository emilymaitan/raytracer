package raytracer.math;

// code inspired by: https://introcs.cs.princeton.edu/java/95linear/Matrix.java.html
/**
 * Represents Matrices and allows operations on them.
 */
public class Matrix {

    /**
     * How many rows the matrix has.
     */
    private int rows;
    /**
     * How many columns the matrix has.
     */
    private int cols;
    /**
     * The matrix data in the form rowXcolumn.
     */
    private double[][] data;

    /**
     * Adds to matrices together.
     * @param mat the addend
     * @return A new matrix c = this * mat.
     * @throws RaytracerMathException When the rows and columns are mismatched.
     */
    public Matrix add(Matrix mat) throws RaytracerMathException {
        if (this.rows != mat.rows || this.cols != mat.cols)
            throw new RaytracerMathException("Matrix::add: Incompatible rows and colums!");
        Matrix result = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                result.data[i][j] = data[i][j] + mat.data[i][j];
            }
        }
        return result;
    }

    /**
     * Multiplies two matrices.
     * @param mat the multiplicand
     * @return A matrix c = a * b
     * @throws RaytracerMathException When the rows and columns are mismatched.
     */
    public Matrix multiply(Matrix mat) throws RaytracerMathException {
        if (this.rows != mat.rows || this.cols != mat.cols)
            throw new RaytracerMathException("Matrix::multiply: Incompatible rows and colums!");
        Matrix result = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                result.data[i][j] = data[i][j] * mat.data[i][j];
            }
        }
        return result;
    }

    /**
     * Multiplies the matrix with a scalar element-wise.
     * @param scalar The scalar.
     * @return The scaled Matrix
     */
    public Matrix multiply(double scalar) {
        Matrix result = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                result.data[i][j] = data[i][j] * scalar;
            }
        }
        return result;
    }

    /**
     * Transposes a matrix by flipping its rows and columns.
     * @return a new transposed matrix
     */
    public Matrix transpose() {
        Matrix result = new Matrix(cols, rows);
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                result.data[i][j] = this.data[j][i];
            }
        }
        return result;
    }

    /**
     * Returns the (square) identity matrix I.
     * @param dimension how many rows = columns the matrix should have
     * @return a new identity matrix
     */
    public static Matrix identity(int dimension) {
        Matrix result = new Matrix(dimension, dimension);
        for (int i = 0; i < dimension; i++) {
            result.data[i][i] = 1;
        }
        return result;
    }

    /**
     * Calculates the determinant of a matrix.
     * @return determinant
     * @throws RaytracerMathException when the matrix is not square
     */
    public double determinant() throws RaytracerMathException {
        if (rows != cols) throw new RaytracerMathException("Matrix::determinant: Matrix is not square!");
        double result = 0;

        if (rows == 1) { // base case: only one element
            return data[0][0];
        } else if (rows == 2) { // Leibnitz formula: a*d - b*c for 2x2 matrices
            return data[0][0]*data[1][1] - data[0][1]*data[1][0];
        } else if (rows == 3) { // we can get by with the rule of Sarrus
            return data[0][0]*data[1][1]*data[2][2]
                    + data[0][1]*data[1][2]*data[2][0]
                    + data[0][2]*data[1][0]*data[2][1]
                    - data[2][0]*data[1][1]*data[0][2]
                    - data[2][1]*data[1][2]*data[0][0]
                    - data[2][2]*data[1][0]*data[0][1];
        } else { // we need a recursive laplace
            // Tutorials used: https://www.youtube.com/watch?v=wx1VRR97vZI
            // https://www.sanfoundry.com/java-program-compute-determinant-matrix/
            // https://en.wikipedia.org/wiki/Determinant

            // first, reset the determinant for the recursion
            result = 0;

            // we create a number of new matrices, and each time skip a different column
            for(int skipCol = 0; skipCol < cols; skipCol++) {
                // first, make a smaller submatrix with one less row and column
                double[][] submatrix = new double[rows-1][];
                for (int i = 0; i < cols-1; i++) { // fill the submatrix with data
                    submatrix[i] = new double[cols-1];
                }

                // fill this submatrix with the original matrix data, but we skip a column
                for (int i = 1; i < rows; i++) {
                    int skipRow = 0;
                    for (int j = 0; j < rows; j++) {
                        if (j == skipCol) continue;
                        submatrix[i-1][skipRow] = data[i][j];
                        skipRow++;
                    }
                }

                // determine the sign
                int sign;
                if (skipCol % 2 == 0) sign = 1;
                else sign = -1;

                // do the recursion
                Matrix matrix = new Matrix(submatrix);
                result += sign * data[0][skipCol] * matrix.determinant();
            }

            return result;
        }
    }

    // TODO test

    /**
     * Inverts a matrix.
     * @return the new inverted matrix
     * @throws RaytracerMathException when no inverse exists
     */
    public Matrix invert() throws RaytracerMathException {
        if (rows != cols) throw new RaytracerMathException("Matrix::determinant: Matrix is not square!");
        if (this.determinant() == 0) throw new RaytracerMathException("Matrix::determinant: Matrix is singular! (Det=0, cannot be inverted.)");

        if (rows == 1) { // base case: there is only one element
            return this;
        } else if (rows == 2) {
            double invDet = 1.0 / this.determinant();
            double[][] data = {
                    { invDet*this.at(2,2), -invDet*this.at(1,2)},
                    {-invDet*this.at(2,1),  invDet*this.at(1,1)}
            };
            return new Matrix(data);
        } else if (rows == 3) {
            throw new RaytracerMathException("Not implemented!"); // TODO
        } else if (rows == 4) {
            throw new RaytracerMathException("Not implemented!"); // TODO
        } else {
            throw new RaytracerMathException("Matrix::invert: Higher dimensions not implemented!");
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    /**
     * Returns the matrix at a certain row and column.
     * @param row the row (indexed from 1)
     * @param col the column (indexed from 1)
     * @return the entry at [row, col]
     */
    public double at (int row, int col) {
        return data[row-1][col-1];
    }

    public Matrix() {
        this.rows = 1;
        this.cols = 1;
        data = new double[rows][cols];
    }

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        data = new double[rows][cols];
    }

    public Matrix(double[][] data) {
        this.rows = data.length;
        this.cols = data[0].length;
        this.data = data;
    }

    @Override
    public boolean equals(Object obj) {
        // if the other object is null, they are not equal
        if (obj == null) return false;
        // if the other object can not be assigned to this class, then not equal
        if(!Matrix.class.isAssignableFrom(obj.getClass())) return false;

        // finally, it depends whether their values are equal
        Matrix _obj = (Matrix)obj;
        if (this.rows != _obj.rows
                || this.cols != _obj.cols) return false;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (this.data[i][j] != _obj.data[i][j]) return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(String.format("raytracer.math.Matrix %dx%d\n", rows, cols));

        for (int i = 0; i < rows; i++) {
            result.append("( ");
            for (int j = 0; j < cols; j++) {
                result.append(String.format("%.4f", data[i][j]));
                if (j+1 < cols) result.append(" | ");
            }
            result.append(" )\n");
        }
        return result.toString();
    }
}