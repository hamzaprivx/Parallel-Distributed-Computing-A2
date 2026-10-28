
package Q3_Multithreading;

public class TelemetryThread extends Thread {
    public TelemetryThread() {
        super("Telemetry-Worker");
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            double cpuUsage = 20 + Math.random() * 60;
            double memoryUsage = 30 + Math.random() * 50;

            System.out.printf(
                "%s - Iteration %d | CPU: %.2f%% | Memory: %.2f%%%n",
                getName(), i, cpuUsage, memoryUsage
            );

            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                interrupt();
                return;
            }
        }
    }
}

