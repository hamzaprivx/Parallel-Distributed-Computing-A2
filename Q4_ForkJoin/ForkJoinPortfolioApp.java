
package Q4_ForkJoin;

import java.util.concurrent.ForkJoinPool;

public class ForkJoinPortfolioApp {

    public static void main(String[] args) {

        final int assetCount = 10000;
        double[] assets = new double[assetCount];

        // Initialize portfolio assets
        for (int i = 0; i < assetCount; i++) {
            assets[i] = 100.0 + (i % 100);
        }

        // Use the shared ForkJoinPool
        ForkJoinPool pool = ForkJoinPool.commonPool();

        // Calculate original portfolio risk
        long startTime = System.nanoTime();

        double totalRisk = pool.invoke(
            new ParallelRiskCalculator(assets, 0, assets.length)
        );

        long riskTime = System.nanoTime() - startTime;

        // Perform portfolio threshold stress test
        double riskCap = 150.0;

        startTime = System.nanoTime();

        pool.invoke(
            new PortfolioThresholdTask(
                assets, 0, assets.length, riskCap
            )
        );

        long stressTime = System.nanoTime() - startTime;

        // Calculate adjusted portfolio risk
        double adjustedTotal = pool.invoke(
            new ParallelRiskCalculator(assets, 0, assets.length)
        );

        // Display results
        System.out.println("Asset count: " + assetCount);
        System.out.printf("Original total risk: %.2f%n", totalRisk);
        System.out.printf("Adjusted total risk: %.2f%n", adjustedTotal);

        System.out.printf(
            "Risk calculation time: %.3f ms%n",
            riskTime / 1_000_000.0
        );

        System.out.printf(
            "Stress test time: %.3f ms%n",
            stressTime / 1_000_000.0
        );
    }
}
