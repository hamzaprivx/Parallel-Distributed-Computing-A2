
package Q5_ParallelSearch;

public class ParallelMinSearch extends SensorSearchEngine {
    public ParallelMinSearch(double[] data, int start, int end) {
        super(data, start, end);
    }

    @Override
    public double processGrid() {
        double min = Double.POSITIVE_INFINITY;

        for (int i = startIdx; i < endIdx; i++) {
            min = Math.min(min, sensorData[i]);
        }

        return min;
    }
}
