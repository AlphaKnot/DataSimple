// importing the required libraries for this class
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.jfree.data.xy.XYSeries;
import java.util.ArrayList;
import java.util.Vector;

/***
 * This class processes the data required (from the parsed series) of strategy 2
 * Strategy 2: PM2.5 air pollution, mean annual exposure (micrograms per cubic meter)
 * vs Forest area (% of land area)
 * @author Amaar Hussein
 */
public class StrategyTwo {
    Analysis strategyTwo;
    String[] analysisNames;
    String[] seriesName;
    ProgramUI root;
    ArrayList<TimeSeries> timeSeriesDatasets;
    ArrayList<TimeSeries> scatterSeriesDatasets;
    DefaultCategoryDataset barSeries;
    ArrayList<XYSeries> XYSeriesSets;
    Vector<String> viewDropdownList;
    Boolean[] GraphAlreadySet;
    public StrategyTwo(ProgramUI root, ArrayList<ParsedSeries> series, int method){

        seriesName = new String[]{
                "(micrograms per cubic meter)",
                "(% of land area)",
        };
        this.root = root;

        analysisNames = root.getAnalysisLabels();

        ArrayList<TimeSeriesCollection> timeSeriesList = new ArrayList<>();
        ArrayList<TimeSeriesCollection> scatterSeriesList = new ArrayList<>();
        ArrayList<TimeSeriesCollection> xySeriesList = new ArrayList<>();
        ArrayList<TimeSeriesCollection> barSeriesList = new ArrayList<>();

        // initializing the report message with a title and empty message
        String message = "";
        String title = seriesName[0] +" vs "+seriesName[1]+"\n";
        StringBuilder finalMessage = new StringBuilder();
        finalMessage.append(title+"==========================================\n");


        for (int i = 0; i < series.size(); i++) {

            TimeSeriesCollection timeSeriesCollection = new TimeSeriesCollection();
            TimeSeriesCollection scatterSeriesCollection = new TimeSeriesCollection();
            TimeSeriesCollection xySeriesCollection = new TimeSeriesCollection();
            TimeSeriesCollection barSeriesCollection = new TimeSeriesCollection();

            TimeSeries xyseries = new TimeSeries(seriesName[i]);
            TimeSeries scatterseries = new TimeSeries(seriesName[i]);
            TimeSeries timeseries = new TimeSeries(seriesName[i]);
            TimeSeries barseries = new TimeSeries(seriesName[i]);


            for (int j = 0; j < series.get(i).getValues().size(); j++) {
                xyseries.add(new Year(series.get(i).xDelimitation.get(j)),series.get(i).getValues().get(j));
                scatterseries.add(new Year(series.get(i).xDelimitation.get(j)),series.get(i).getValues().get(j));
                barseries.add(new Year(series.get(i).xDelimitation.get(j)),series.get(i).getValues().get(j));
                timeseries.add(new Year(series.get(i).xDelimitation.get(j)), series.get(i).getValues().get(j));

                // creating the report message and adding it to the final report message
                message = seriesName[i] +" had a value in year " + series.get(i).xDelimitation.get(j) + " of "+ series.get(i).getValues().get(j)+"\n";
                finalMessage.append(message);
            }

            xySeriesCollection.addSeries(xyseries);
            scatterSeriesCollection.addSeries(scatterseries);
            timeSeriesCollection.addSeries(timeseries);
            barSeriesCollection.addSeries(barseries);

            xySeriesList.add(xySeriesCollection);
            scatterSeriesList.add(scatterSeriesCollection);
            timeSeriesList.add(timeSeriesCollection);
            barSeriesList.add(barSeriesCollection);



        }
        String[] analysisTypes = {
                "Line Chart",
                "Scatter Plot",
                "Bar Chart",
                "Time Series",
        };
        strategyTwo = new Analysis(root,method,seriesName,analysisNames,analysisTypes,finalMessage.toString(),timeSeriesList,scatterSeriesList,barSeriesList,xySeriesList);
    }

}

