import java.util.concurrent.RecursiveTask;

class MatrixMultiplication extends RecursiveTask<Matrix> {

  /** The fork threshold. */
  private static final int FORK_THRESHOLD = 128; // Find a good threshold

  /** The first matrix to multiply with. */
  private final Matrix m1;

  /** The second matrix to multiply with. */
  private final Matrix m2;

  /** The starting row of m1. */
  private final int m1Row;

  /** The starting col of m1. */
  private final int m1Col;

  /** The starting row of m2. */
  private final int m2Row;

  /** The starting col of m2. */
  private final int m2Col;

  /**
   * The dimension of the input (sub)-matrices and the size of the output
   * matrix.
   */
  private int dimension;

  /**
   * A constructor for the Matrix Multiplication class.
   * @param  m1 The matrix to multiply with.
   * @param  m2 The matrix to multiply with.
   * @param  m1Row The starting row of m1.
   * @param  m1Col The starting col of m1.
   * @param  m2Row The starting row of m2.
   * @param  m2Col The starting col of m2.
   * @param  dimension The dimension of the input (sub)-matrices and the size
   *     of the output matrix.
   */
  MatrixMultiplication(Matrix m1, Matrix m2, int m1Row, int m1Col, int m2Row,
      int m2Col, int dimension) {
    this.m1 = m1;
    this.m2 = m2;
    this.m1Row = m1Row;
    this.m1Col = m1Col;
    this.m2Row = m2Row;
    this.m2Col = m2Col;
    this.dimension = dimension;
  }

  /**
   * Helper method to add 2 matrices
   */
  private static Matrix add(Matrix m1, Matrix m2) {
    Matrix m = new Matrix(m1.dimension);
    for (int i = 0; i < m1.dimension; i += 1) {
      for (int j = 0; j < m1.dimension; j += 1) {
        m.m[i][j] = m1.m[i][j] + m2.m[i][j];
      }
    }
    return m;
  }

  @Override
  public Matrix compute() {

    // If the matrix is small enough, just multiple non-recursively.
    if (this.dimension <= MatrixMultiplication.FORK_THRESHOLD) {
      return Matrix.nonRecursiveMultiply(m1, m2, m1Row, m1Col, m2Row, m2Col, dimension);
    }

    // Else, cut the matrix into four blocks of equal size, recursively
    // multiply then sum the multiplication result.
    int size = this.dimension / 2;
    Matrix result = new Matrix(dimension);

    MatrixMultiplication a11b11 = new MatrixMultiplication(m1, m2, m1Row, m1Col, m2Row,
        m2Col, size);
    MatrixMultiplication a12b21 = new MatrixMultiplication(m1, m2, m1Row, m1Col + size,
        m2Row + size, m2Col, size);
    MatrixMultiplication a11b12 = new MatrixMultiplication(m1, m2, m1Row, m1Col, m2Row,
        m2Col + size, size);
    MatrixMultiplication a12b22 = new MatrixMultiplication(m1, m2, m1Row, m1Col + size,
        m2Row + size, m2Col + size, size);
    MatrixMultiplication a21b11 = new MatrixMultiplication(m1, m2, m1Row + size, m1Col,
        m2Row, m2Col, size);
    MatrixMultiplication a22b21 = new MatrixMultiplication(m1, m2, m1Row + size, m1Col + size,
        m2Row + size, m2Col, size);
    MatrixMultiplication a21b12 = new MatrixMultiplication(m1, m2, m1Row + size, m1Col,
        m2Row, m2Col + size, size);
    MatrixMultiplication a22b22 = new MatrixMultiplication(m1, m2, m1Row + size, m1Col + size,
        m2Row + size, m2Col + size, size);

    a22b22.fork();
    a21b12.fork();
    a22b21.fork();
    a21b11.fork();
    a12b22.fork();
    a11b12.fork();
    a12b21.fork();
    a11b11.fork();

    Matrix Ma11b11 = a11b11.join();
    Matrix Ma12b21 = a12b21.join();

    for (int i = 0; i < size; i++) {
      double[] m1m = Ma11b11.m[i];
      double[] m2m = Ma12b21.m[i];
      double[] r1m = result.m[i];
      for (int j = 0; j < size; j++) {
        r1m[j] = m1m[j] + m2m[j];
      }
    }

    Matrix Ma11b12 = a11b12.join();
    Matrix Ma12b22 = a12b22.join();

    for (int i = 0; i < size; i++) {
      double[] m1m = Ma11b12.m[i];
      double[] m2m = Ma12b22.m[i];
      double[] r1m = result.m[i];
      for (int j = 0; j < size; j++) {
        r1m[j + size] = m1m[j] + m2m[j];
      }
    }

    Matrix Ma21b11 = a21b11.join();
    Matrix Ma22b21 = a22b21.join();

    for (int i = 0; i < size; i++) {
      double[] m1m = Ma21b11.m[i];
      double[] m2m = Ma22b21.m[i];
      double[] r1m = result.m[i + size];
      for (int j = 0; j < size; j++) {
        r1m[j] = m1m[j] + m2m[j];
      }
    }

    Matrix Ma21b12 = a21b12.join();
    Matrix Ma22b22 = a22b22.join();

    for (int i = 0; i < size; i++) {
      double[] m1m = Ma21b12.m[i];
      double[] m2m = Ma22b22.m[i];
      double[] r1m = result.m[i + size];
      for (int j = 0; j < size; j++) {
        r1m[j + size] = m1m[j] + m2m[j];
      }
    }
    return result;


    /*
    if (this.dimension <= MatrixMultiplication.FORK_THRESHOLD) {
      return Matrix.recursiveMultiply(m1, m2, m1Row, m1Col, m2Row, m2Col, dimension);
    }

    int newDimension = this.dimension / 2;

    MatrixMultiplication a11b11 = new MatrixMultiplication(this.m1, this.m2, 0, 0, 0, 0, newDimension);
    MatrixMultiplication a12b21 = new MatrixMultiplication(this.m1, this.m2, 0, newDimension, newDimension, 0, newDimension);
    MatrixMultiplication a11b12 = new MatrixMultiplication(this.m1, this.m2, 0, 0, 0, newDimension, newDimension);
    MatrixMultiplication a12b22 = new MatrixMultiplication(this.m1, this.m2, 0, newDimension, newDimension, newDimension, newDimension);
    MatrixMultiplication a21b11 = new MatrixMultiplication(this.m1, this.m2, newDimension, 0, 0, 0, newDimension);
    MatrixMultiplication a22b21 = new MatrixMultiplication(this.m1, this.m2, newDimension, newDimension, newDimension, 0, newDimension);
    MatrixMultiplication a21b12 = new MatrixMultiplication(this.m1, this.m2, newDimension, 0, 0, newDimension, newDimension);
    MatrixMultiplication a22b22 = new MatrixMultiplication(this.m1, this.m2, newDimension, newDimension, newDimension, newDimension, newDimension);

    a22b22.fork();
    a21b12.fork();
    a22b21.fork();
    a21b11.fork();
    a12b22.fork();
    a11b12.fork();
    a12b21.fork();
    a11b11.fork();

    Matrix topLeft = MatrixMultiplication.add(a11b11.join(), a12b21.join());
    Matrix topRight = MatrixMultiplication.add(a11b12.join(), a12b22.join());
    Matrix bottomLeft = MatrixMultiplication.add(a21b11.join(), a22b21.join());
    Matrix bottomRight = MatrixMultiplication.add(a21b12.join(), a22b22.join());

    Matrix result = new Matrix(this.dimension);

    for (int i = 0; i < newDimension; i += 1) {
      for (int j = 0; j < newDimension; j += 1) {
        result.m[i][j] = topLeft.m[i][j];
      }
    }

    for (int i = 0; i < newDimension; i += 1) {
      for (int j = 0; j < newDimension; j += 1) {
        result.m[i][j + newDimension] = topRight.m[i][j];
      }
    }

    for (int i = 0; i < newDimension; i += 1) {
      for (int j = 0; j < newDimension; j += 1) {
        result.m[i + newDimension][j] = bottomLeft.m[i][j];
      }
    }

    for (int i = 0; i < newDimension; i += 1) {
      for (int j = 0; j < newDimension; j += 1) {
        result.m[i + newDimension][j + newDimension] = bottomRight.m[i][j];
      }
    }

    return result;
    */
  }
}






