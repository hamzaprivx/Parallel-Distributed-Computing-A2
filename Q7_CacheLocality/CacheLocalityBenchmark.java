
package Q7_CacheLocality;

public class CacheLocalityBenchmark {
    private static final int N = 2000;
    private static final int REPETITIONS = 10;

    private final double[][] matrix = new double[N][N];
    private volatile double result;

    public CacheLocalityBenchmark() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matrix[i][j] = (i + j) * 0.5;
            }
        }
    }

    public double traverseRowMajor() {
        double sum = 0.0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sum += matrix[i][j];
            }
        }

        return sum;
    }

    public double traverseColumnMajor() {
        double sum = 0.0;

        for (int j = 0; j < N; j++) {
            for (int i = 0; i < N; i++) {
                sum += matrix[i][j];
            }
        }

        return sum;
    }

    public static void main(String[] args) {
        CacheLocalityBenchmark benchmark = new CacheLocalityBenchmark();

        // Warm up the JVM before collecting measurements.
        benchmark.result = benchmark.traverseRowMajor();
        benchmark.result = benchmark.traverseColumnMajor();

        long rowTotal = 0;
        long columnTotal = 0;

        for (int i = 0; i < REPETITIONS; i++) {
            long start = System.nanoTime();
            benchmark.result = benchmark.traverseRowMajor();
            rowTotal += System.nanoTime() - start;

            start = System.nanoTime();
            benchmark.result = benchmark.traverseColumnMajor();
            columnTotal += System.nanoTime() - start;
        }

        double rowAverageMs =
            rowTotal / (double) REPETITIONS / 1_000_000.0;
        double columnAverageMs =
            columnTotal / (double) REPETITIONS / 1_000_000.0;

        double speedup = rowAverageMs > 0
            ? columnAverageMs / rowAverageMs : 0.0;

        System.out.println("Matrix size: " + N + " x " + N);
        System.out.println("Repetitions: " + REPETITIONS);
        System.out.printf("Row-major average: %.3f ms%n", rowAverageMs);
        System.out.printf("Column-major average: %.3f ms%n",
            columnAverageMs);
        System.out.printf("Row-major speedup ratio: %.3fx%n", speedup);
        System.out.println("Checksum: " + benchmark.result);
    }
}
