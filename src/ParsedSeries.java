import java.util.ArrayList;

public class ParsedSeries {
    String seriesIndicator;
    ArrayList<Float> values;
    float cumulativeAverage;
    ArrayList<Integer> xDelimitation;
    String country;
    public ParsedSeries(String seriesIndicator, ArrayList<Float> values, ArrayList<Integer> xDelimitation, Float cumulativeAverage){
        this.seriesIndicator = seriesIndicator;
        this.values = values;
        this.cumulativeAverage = cumulativeAverage;
        this.xDelimitation = xDelimitation;
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


    public void toSeriesString() {
        System.out.println("Printing Parsed series :\n--------------------------------------");
       for(int i = 0; i<values.size(); i++){
           System.out.println(values.get(i)+"\t"+xDelimitation.get(i));
       }

    }
}
