
package Q5_ParallelSearch;

public class ParallelMaxSearch extends SensorSearchEngine {
    public ParallelMaxSearch(double[] data, int start, int end) {
        super(data, start, end);
    }

    @Override
    public double processGrid() {
        double max = Double.NEGATIVE_INFINITY;

        for (int i = startIdx; i < endIdx; i++) {
            max = Math.max(max, sensorData[i]);
        }

        return max;
    }
}
