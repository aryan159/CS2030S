import java.util.Scanner;
import java.time.Instant;
import java.time.Duration;

/**
 * Lab9a is the main driver class for testing matrix multiplication.
 * Usage: java Lab9a
 */
class testing{
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int n = scanner.nextInt();
    
    // Read matrix 1
    Matrix m1 = new Matrix(n);
    for (int i=0; i<n; i++) {
      for (int j=0; j<n; j++) {
        m1.m[i][j] = scanner.nextDouble();
      }
    }
    
    // Read matrix 1
    Matrix m2 = new Matrix(n);
    for (int i=0; i<n; i++) {
      for (int j=0; j<n; j++) {
        m2.m[i][j] = scanner.nextDouble();
      }
    }
    
    // Multiply matrices
    final long startTime = System.currentTimeMillis();
    Matrix res = Matrix.parallelMultiply(m1, m2);
    final long endTime = System.currentTimeMillis();
    //System.out.println(res);
    //System.out.println();
    System.out.println("Total execution time: " + (endTime - startTime));

    
  }
}
