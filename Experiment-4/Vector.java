public class Vector {
    private double[] components;
    private int dimension;

    // Constructor: accepts variable number of components
    public Vector(double... components) throws InvalidDimensionException {
        // Validate that dimension is 2 or 3
        if (components.length != 2 && components.length != 3) {
            throw new InvalidDimensionException("Invalid dimension: " + components.length + ". Only 2D and 3D vectors are supported.");
        }

        this.dimension = components.length;
        this.components = new double[dimension];
        for (int i = 0; i < dimension; i++) {
            this.components[i] = components[i];
        }
    }

    // Add two vectors
    public Vector add(Vector other) throws DimensionMismatchException {
        if (this.dimension != other.dimension) {
            throw new DimensionMismatchException("Dimension mismatch: cannot operate on a " + this.dimension + "D vector with a " + other.dimension + "D vector.");
        }

        double[] result = new double[this.dimension];
        for (int i = 0; i < this.dimension; i++) {
            result[i] = this.components[i] + other.components[i];
        }

        try {
            return new Vector(result);
        } catch (InvalidDimensionException e) {
            // This shouldn't happen since we're using same dimension
            throw new RuntimeException("Unexpected error in add operation", e);
        }
    }

    // Subtract two vectors
    public Vector subtract(Vector other) throws DimensionMismatchException {
        if (this.dimension != other.dimension) {
            throw new DimensionMismatchException("Dimension mismatch: cannot operate on a " + this.dimension + "D vector with a " + other.dimension + "D vector.");
        }

        double[] result = new double[this.dimension];
        for (int i = 0; i < this.dimension; i++) {
            result[i] = this.components[i] - other.components[i];
        }

        try {
            return new Vector(result);
        } catch (InvalidDimensionException e) {
            // This shouldn't happen since we're using same dimension
            throw new RuntimeException("Unexpected error in subtract operation", e);
        }
    }

    // Dot product of two vectors
    public double dotProduct(Vector other) throws DimensionMismatchException {
        if (this.dimension != other.dimension) {
            throw new DimensionMismatchException("Dimension mismatch: cannot operate on a " + this.dimension + "D vector with a " + other.dimension + "D vector.");
        }

        double result = 0.0;
        for (int i = 0; i < this.dimension; i++) {
            result += this.components[i] * other.components[i];
        }
        return result;
    }

    // Get dimension
    public int getDimension() {
        return dimension;
    }

    // Get components for display
    public double[] getComponents() {
        return components;
    }

    // toString method
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 0; i < dimension; i++) {
            sb.append(components[i]);
            if (i < dimension - 1) {
                sb.append(", ");
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
