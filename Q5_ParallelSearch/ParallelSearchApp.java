
package Q5_ParallelSearch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ParallelSearchApp {

    public static void main(String[] args) throws Exception {

        final int dataSize = 50000;
        final int workers = 4;
        double[] data = new double[dataSize];

        // Generate sensor data
        for (int i = 0; i < dataSize; i++) {
            data[i] = Math.random() * 10000;
        }

        ExecutorService executor =
                Executors.newFixedThreadPool(workers);

        try {
            List<Future<Double>> minResults = new ArrayList<>();
            List<Future<Double>> maxResults = new ArrayList<>();

            long startTime = System.nanoTime();

            // Divide data among worker threads
            for (int worker = 0; worker < workers; worker++) {

                int start = worker * dataSize / workers;
                int end = (worker + 1) * dataSize / workers;

                minResults.add(executor.submit(
                    () -> new ParallelMinSearch(data, start, end)
                            .processGrid()
                ));

                maxResults.add(executor.submit(
                    () -> new ParallelMaxSearch(data, start, end)
                            .processGrid()
                ));
            }

            double minimum = Double.POSITIVE_INFINITY;
            double maximum = Double.NEGATIVE_INFINITY;

            // Combine results from all workers
            for (int i = 0; i < workers; i++) {
                minimum = Math.min(minimum, minResults.get(i).get());
                maximum = Math.max(maximum, maxResults.get(i).get());
            }

            long elapsed = System.nanoTime() - startTime;

            System.out.println("Sensor values: " + dataSize);
            System.out.println("Worker threads: " + workers);
            System.out.println("Minimum value: " + minimum);
            System.out.println("Maximum value: " + maximum);

            System.out.printf(
                "Parallel search time: %.3f ms%n",
                elapsed / 1_000_000.0
            );

        } finally {
            executor.shutdown();
        }
    }
}
