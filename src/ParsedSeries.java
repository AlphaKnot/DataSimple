import java.util.ArrayList;


public class ParsedSeries {
    /***
     *This class is used to contain a myriad of related series parsed from the world bank API, including years, country name, cumulative averages
     * @Author : Amaar Hussein
     *
     */
    String seriesIndicator;
    ArrayList<Float> values;
    float cumulativeAverage;
    ArrayList<Integer> xDelimitation;
    String country;
    /***
     * the constructor for ParsedSeries
     * @param String series indicator
     * @param float arraylist of y values
     * @param integer arraylist of years
     * @param cumulativeAverage average for pie chart calculation
     */
    public ParsedSeries(String seriesIndicator, ArrayList<Float> values, ArrayList<Integer> xDelimitation, Float cumulativeAverage){
        this.seriesIndicator = seriesIndicator;
        this.values = values;
        this.cumulativeAverage = cumulativeAverage;
        this.xDelimitation = xDelimitation;
    }

    /***
     * Getter for values
     * @return values (yaxis)
     */
    public ArrayList<Float> getValues() {
        return values;
    }

    /***
     * series indicator getter
     * @return y axis title
     */
    public String getSeriesIndicator() {
        return seriesIndicator;
    }

    /***
     * gets cumulative average
     * @return cumulative average
     */
    public float getCumulativeAverage() {
        return cumulativeAverage;
    }

    /***
     * to String method for parsed series
     */
    public void toSeriesString() {
        System.out.println("Printing Parsed series :\n--------------------------------------");
       for(int i = 0; i<values.size(); i++){
           System.out.println(values.get(i)+"\t"+xDelimitation.get(i));
       }

    }
}
