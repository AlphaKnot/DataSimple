import java.util.ArrayList;

public class ParsedSeries {
    String seriesIndicator;
    ArrayList<Float> values;
    float cumulativeAverage;
    String country;
    public ParsedSeries(String seriesIndicator, ArrayList<Float> values, Float cumulativeAverage){
        this.seriesIndicator = seriesIndicator;
        this.values = values;
        this.cumulativeAverage = cumulativeAverage;
    }

    public ArrayList<Float> getValues() {
        return values;
    }
    public String getSeriesIndicator() {
        return seriesIndicator;
    }
    public float getCumulativeAverage() {
        return cumulativeAverage;
    }
}
