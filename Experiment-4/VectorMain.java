
public class VectorMain {
    public static void main(String[] args) {
        // Test 1: Valid 2D addition
        System.out.println("Test 1: Valid 2D addition");
        try {
            Vector v1 = new Vector(1, 2);
            Vector v2 = new Vector(3, 4);
            Vector result = v1.add(v2);
            System.out.println(v1 + " + " + v2 + " = " + result);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println();

        // Test 2: Valid 3D subtraction
        System.out.println("Test 2: Valid 3D subtraction");
        try {
            Vector v1 = new Vector(5, 6, 7);
            Vector v2 = new Vector(1, 2, 3);
            Vector result = v1.subtract(v2);
            System.out.println(v1 + " - " + v2 + " = " + result);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println();

        // Test 3: Valid 2D dot product
        System.out.println("Test 3: Valid 2D dot product");
        try {
            Vector v1 = new Vector(1, 2);
            Vector v2 = new Vector(3, 4);
            double result = v1.dotProduct(v2);
            System.out.println(v1 + " · " + v2 + " = " + result);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println();

        // Test 4: Valid 3D dot product
        System.out.println("Test 4: Valid 3D dot product");
        try {
            Vector v1 = new Vector(1, 2, 3);
            Vector v2 = new Vector(4, 5, 6);
            double result = v1.dotProduct(v2);
            System.out.println(v1 + " · " + v2 + " = " + result);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println();

        // Test 5: InvalidDimensionException - create a vector with 4 components
        System.out.println("Test 5: InvalidDimensionException (4D vector)");
        try {
            Vector v = new Vector(1, 2, 3, 4);
            System.out.println("Vector created: " + v);
        } catch (InvalidDimensionException e) {
            System.out.println("Caught InvalidDimensionException: " + e.getMessage());
        }
        System.out.println();

        // Test 6: DimensionMismatchException - add 2D to 3D
        System.out.println("Test 6: DimensionMismatchException (2D + 3D)");
        try {
            Vector v1 = new Vector(1, 2);
            Vector v2 = new Vector(1, 2, 3);
            Vector result = v1.add(v2);
            System.out.println(v1 + " + " + v2 + " = " + result);
        } catch (DimensionMismatchException e) {
            System.out.println("Caught DimensionMismatchException: " + e.getMessage());
        } catch (InvalidDimensionException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println();
    }
}
