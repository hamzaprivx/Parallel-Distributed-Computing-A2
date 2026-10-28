
package Q6_ConcurrentCollections;

import java.util.*;
import java.util.concurrent.*;

public class InventoryCatalogManager {
    private static final int THREADS = 10;
    private static final int TOTAL_OPERATIONS = 50000;
    private static final int OPERATIONS_PER_THREAD =
        TOTAL_OPERATIONS / THREADS;

    private static long benchmark(Map<String, Integer> catalog)
            throws InterruptedException {

        for (int i = 0; i < 100; i++) {
            catalog.put("ITEM-" + i, 100);
        }

        ExecutorService executor = Executors.newFixedThreadPool(THREADS);
        CountDownLatch ready = new CountDownLatch(THREADS);
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(THREADS);

        for (int worker = 0; worker < THREADS; worker++) {
            final int workerId = worker;

            executor.execute(() -> {
                ready.countDown();

                try {
                    start.await();

                    for (int i = 0; i < OPERATIONS_PER_THREAD; i++) {
                        String key = "ITEM-" + ((i + workerId) % 100);

                        catalog.get(key);
                        catalog.compute(key, (k, value) ->
                            value == null ? 1 : value + 1
                        );
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    done.countDown();
                }
            });
        }

        ready.await();
        long startTime = System.nanoTime();
        start.countDown();
        done.await();
        long elapsed = System.nanoTime() - startTime;

        executor.shutdown();
        return elapsed;
    }

    private static void printResults(String name, long elapsed) {
        double seconds = elapsed / 1_000_000_000.0;
        double throughput = TOTAL_OPERATIONS / seconds;

        System.out.println(name);
        System.out.printf("Execution time: %.3f ms%n",
            elapsed / 1_000_000.0);
        System.out.printf("Throughput: %.2f operations/second%n",
            throughput);
    }

    public static void main(String[] args) throws InterruptedException {
        Map<String, Integer> synchronizedMap =
            Collections.synchronizedMap(new HashMap<>());

        Map<String, Integer> concurrentMap = new ConcurrentHashMap<>();

        long syncTime = benchmark(synchronizedMap);
        long concurrentTime = benchmark(concurrentMap);

        System.out.println("Threads: " + THREADS);
        System.out.println("Operations per collection: "
            + TOTAL_OPERATIONS);

        printResults("Synchronized HashMap", syncTime);
        printResults("ConcurrentHashMap", concurrentTime);
    }
}
