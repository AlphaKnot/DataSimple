// importing the required libraries for this class
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.jfree.data.xy.XYSeries;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Vector;

/***
 * This class processes the data required (from the parsed series) of strategy 2
 * Strategy 2: PM2.5 air pollution, mean annual exposure (micrograms per cubic meter)
 * vs Forest area (% of land area)
 * @author Amaar Hussein
 */
public class StrategyTwo {
    // the analysis object representing strategy 2
    Analysis strategyTwo;
    // represents the dropdown of analyses
    String[] analysisNames;
    // represents the name of datasets being processed
    String[] seriesName;
    // the program ui
    ProgramUI root;
    
    // creating arraylists to store each dataset given the series' name
    ArrayList<TimeSeries> timeSeriesDatasets;
    ArrayList<TimeSeries> scatterSeriesDatasets;
    DefaultCategoryDataset barSeries;
    ArrayList<XYSeries> XYSeriesSets;
    
    // represents the dropdown menu
    Vector<String> viewDropdownList;
    // boolean array that checks if the graph is already set
    Boolean[] GraphAlreadySet;
    
    /***
     * the constructor for strategy 2
     * @param root the program ui
     * @param series the dataset parsed from ParsedSeries to be used for data processing
     * @param method the index of the analysis on the dropdown menu
     */
    public StrategyTwo(ProgramUI root, ArrayList<ParsedSeries> series, int method) throws DataProcessorException {

        // the names of the dataset series
        seriesName = new String[]{
                "(micrograms per cubic meter)",
                "(% of land area)",
        };
        // calling the program ui
        this.root = root;

        // getting the analysis labels to the program ui
        analysisNames = root.getAnalysisLabels();

        boolean isEmpty = true;

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
        for (int i = 0; i < series.size(); i++) {

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


            for (int j = 0; j < series.get(i).getValues().size(); j++) {

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
                finalMessage.append(message);
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
        strategyTwo = new Analysis(root,method,seriesName,analysisNames,analysisTypes,finalMessage.toString(),timeSeriesList,scatterSeriesList,barSeriesList,xySeriesList);
    }
}

