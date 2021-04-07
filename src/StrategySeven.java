// importing the libraries required for this class
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import java.util.ArrayList;

/***
 * This class processes the data required (from the parsed series) of strategy 7
 * Strategy 7: Infant Mortality Rate (per 1000 live births) vs Current Health Expenditure )per capita
 * @author Jenessa Lu
 */
public class StrategySeven {

    // represents the dropdown of analyses
    String[] analysisNames;
    // represents the name of datasets being processed
    String[] seriesName;
    // the program ui
    ProgramUI root;

    /***
     * the constructor for strategy 7
     * @param root the program ui
     * @param series the dataset parsed from ParsedSeries to be used for data processing
     * @param method the index of the analysis on the dropdown menu
     */
    public StrategySeven(ProgramUI root, ArrayList<ParsedSeries> series, int method){

        // the names of the dataset series
        seriesName = new String[] {
                "Infant Mortality Rate (per 1000 live births)",
                "Current Health Expenditure per capita"};

        // calling the program ui
        this.root = root;
        // getting the analysis labels to the program ui
        analysisNames = root.getAnalysisLabels();

        // creating arraylists to store each dataset given the series' name
        ArrayList<TimeSeriesCollection> timeSeriesList = new ArrayList<>();
        ArrayList<TimeSeriesCollection> scatterSeriesList = new ArrayList<>();
        ArrayList<TimeSeriesCollection> xySeriesList = new ArrayList<>();
        ArrayList<TimeSeriesCollection> barSeriesList = new ArrayList<>();

        // initializing the report message with a title and empty message
        String message = "";
        String title = seriesName[0] +" vs "+seriesName[1]+"\n";
        StringBuilder finalMessage = new StringBuilder();
        finalMessage.append(title+"==========================================\n");

        // looping through the parsed datasets to store information
        for (int i = 0; i<series.size()-1; i++) {

            // initializing the data collections
            TimeSeriesCollection timeSeriesCollection = new TimeSeriesCollection();
            TimeSeriesCollection scatterSeriesCollection = new TimeSeriesCollection();
            TimeSeriesCollection xySeriesCollection = new TimeSeriesCollection();
            TimeSeriesCollection barSeriesCollection = new TimeSeriesCollection();

            // initializing the series with their appropriate names
            TimeSeries xyseries = new TimeSeries(seriesName[i]);
            TimeSeries scatterseries = new TimeSeries(seriesName[i]);
            TimeSeries timeseries = new TimeSeries(seriesName[i]);
            TimeSeries barseries = new TimeSeries(seriesName[i]);

            for (int j = 0; j < series.get(i).getValues().size()-1; j++) {
                // adding the datasets to their series
                xyseries.add(new Year(series.get(i).xDelimitation.get(j)),series.get(i).getValues().get(j));
                scatterseries.add(new Year(series.get(i).xDelimitation.get(j)),series.get(i).getValues().get(j));
                barseries.add(new Year(series.get(i).xDelimitation.get(j)),series.get(i).getValues().get(j));
                timeseries.add(new Year(series.get(i).xDelimitation.get(j)), series.get(i).getValues().get(j));

                // creating the report message and adding it to the final report message
                message = seriesName[i] +" had a value in year " + series.get(i).xDelimitation.get(j) + " of "+ series.get(i).getValues().get(j)+"\n";
                finalMessage.append(message);

            }
            // adding the series to their collections
            xySeriesCollection.addSeries(xyseries);
            scatterSeriesCollection.addSeries(scatterseries);
            timeSeriesCollection.addSeries(timeseries);
            barSeriesCollection.addSeries(barseries);

            // adding the collections to their appropriate arraylist
            xySeriesList.add(xySeriesCollection);
            scatterSeriesList.add(scatterSeriesCollection);
            timeSeriesList.add(timeSeriesCollection);
            barSeriesList.add(barSeriesCollection);
        }
        String[] analysisTypes={
                "Line Chart",
                "Scatter Plot",
                "Bar Chart",
                "Time Series",
                "Report"
        };
        // calling the Analysis class to compute the data into viewers
        Analysis strategySeven = new Analysis(root,method,seriesName,analysisNames,analysisTypes,message.toString(),timeSeriesList,scatterSeriesList,barSeriesList,xySeriesList);

    }
}
