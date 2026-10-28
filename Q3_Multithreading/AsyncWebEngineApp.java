
package Q3_Multithreading;

public class AsyncWebEngineApp {
    public static void main(String[] args) {
        System.out.println("Main thread: " + Thread.currentThread().getName());

        TelemetryThread telemetry = new TelemetryThread();
        Thread logThread = new Thread(
            new LogProcessorTask(), "Log-Worker"
        );

        telemetry.start();
        logThread.start();

        System.out.println("Telemetry state: " + telemetry.getState());
        System.out.println("Log thread state: " + logThread.getState());

        try {
            telemetry.join();
            logThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Main thread interrupted.");
            return;
        }

        System.out.println("All workers completed.");
    }
}
