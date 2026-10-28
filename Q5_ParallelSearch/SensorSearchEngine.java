
package Q5_ParallelSearch;

public abstract class SensorSearchEngine {
    protected final double[] sensorData;
    protected final int startIdx;
    protected final int endIdx;

    public SensorSearchEngine(
            double[] sensorData, int startIdx, int endIdx) {
        this.sensorData = sensorData;
        this.startIdx = startIdx;
        this.endIdx = endIdx;
    }

    public abstract double processGrid();
}
