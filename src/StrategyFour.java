//importing the required libraries for the class
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;

import java.util.ArrayList;
/***
 * This class processes the data required (from the parsed series) of strategy 4
 * Strategy 5: Forest cover (% of Land Area)
 * @author Ishaan Kumar
 */




public class StrategyFour{
    String[] analysisNames ;
    String[] seriesName;
    ProgramUI root;
    String message;
    boolean isEmpty = true;
    /***
     * the constructor for strategy 4
     * @param root the program ui
     * @param series the dataset parsed from ParsedSeries to be used for data processing
     * @param method the index of the analysis on the dropdown menu
     */
    public StrategyFour(ProgramUI root, ArrayList<ParsedSeries> series, int method) throws DataProcessorException {

        //the names of the dataset series
        seriesName = new String[]{
                "Forest Area (% of Land Area)"
        };
        //calling the programui
        this.root = root;

        // getting the analysis labels to the program ui
        analysisNames = root.getAnalysisLabels();

        //Initialising the lists for data.
        ArrayList<TimeSeriesCollection> timeSeriesList = new ArrayList<>();
        ArrayList<TimeSeriesCollection> scatterSeriesList = new ArrayList<>();
        ArrayList<TimeSeriesCollection> xySeriesList = new ArrayList<>();
        ArrayList<TimeSeriesCollection> barSeriesList = new ArrayList<>();

        // initializing the report message with a title and empty message
        String message = "";
        String title = seriesName[0] +"\n";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(title+"==========================================\n");

        // looping through the parsed datasets to store information
        for (int i = 0; i < series.size(); i++) {

            // adding the datasets to their series
            TimeSeriesCollection timeSeriesCollection = new TimeSeriesCollection();
            TimeSeriesCollection scatterSeriesCollection = new TimeSeriesCollection();
            TimeSeriesCollection xySeriesCollection = new TimeSeriesCollection();
            TimeSeriesCollection barSeriesCollection = new TimeSeriesCollection();

            // initializing the series with their appropriate names
            TimeSeries xyseries = new TimeSeries(seriesName[i]);
            TimeSeries scatterseries = new TimeSeries(seriesName[i]);
            TimeSeries timeseries = new TimeSeries(seriesName[i]);
            TimeSeries barseries = new TimeSeries(seriesName[i]);


            for (int j = 0; j < series.get(i).getValues().size(); j++) {
                //Check if the selected year range has data or not
                if (series.get(i).getValues().get(j) != null){
                    isEmpty = false;
                }

                // adding the datasets to their series
                xyseries.add(new Year(series.get(i).xDelimitation.get(j)),series.get(i).getValues().get(j));
                scatterseries.add(new Year(series.get(i).xDelimitation.get(j)),series.get(i).getValues().get(j));
                barseries.add(new Year(series.get(i).xDelimitation.get(j)),series.get(i).getValues().get(j));
                timeseries.add(new Year(series.get(i).xDelimitation.get(j)), series.get(i).getValues().get(j));

                // creating the report message and adding it to the final report message
                message = seriesName[i] +" had a value in year " + series.get(i).xDelimitation.get(j) + " of "+ series.get(i).getValues().get(j)+"\n";
                stringBuilder.append(message);
            }

            if (isEmpty){
                //Throw an exception if no data for the selected years
                throw new DataProcessorException("No Data For The Selected Years");
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

        // the options for the dropdown
        String[] analysisTypes = {
                "Line Chart",
                "Scatter Plot",
                "Bar Chart",
                "Time Series",
                "Report"
        };

        // calling the Analysis class to compute the data into viewers
        Analysis strategyFour = new Analysis(root,method,seriesName,analysisNames,analysisTypes,stringBuilder.toString(),timeSeriesList,scatterSeriesList,barSeriesList,xySeriesList);

    }
}
