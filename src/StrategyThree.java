import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import java.util.ArrayList;

/**
 * @author nathan halsey
 * @description completes analysis for strategy 3
 *
 */
public class StrategyThree {
    String[] analysisNames;
    String[] seriesName;
    ProgramUI root;
    ArrayList<TimeSeriesCollection> timeSeriesList;
    ArrayList<TimeSeriesCollection> scatterSeriesList;
    ArrayList<TimeSeriesCollection> barSeriesList;
    ArrayList<TimeSeriesCollection> xySeriesList;
    float cum = (float)0.0;

    // constructor is only thing needed
    public StrategyThree(ProgramUI root, ArrayList<ParsedSeries> series, int method) throws DataProcessorException{

        // graph labels
        seriesName = new String[]{"CO2 Emissions (Per Capita)","GDP (Per Capita)","Ratio of CO2 to GDP"};

        this.root = root;
        //initialize containers
        analysisNames = root.getAnalysisLabels();
        timeSeriesList = new ArrayList<>();
        barSeriesList = new ArrayList<>();
        xySeriesList = new ArrayList<>();
        scatterSeriesList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();

        // create datasets
        for (int i  = 0; i < series.size()+1; i++) {

            TimeSeriesCollection timeSeriesCollection = new TimeSeriesCollection();
            TimeSeriesCollection scatterSeriesCollection = new TimeSeriesCollection();
            TimeSeriesCollection xySeriesCollection = new TimeSeriesCollection();
            TimeSeriesCollection barSeriesCollection = new TimeSeriesCollection();

            TimeSeries xyseries = new TimeSeries(seriesName[i]);
            TimeSeries scatterseries = new TimeSeries(seriesName[i]);
            TimeSeries timeseries = new TimeSeries(seriesName[i]);
            TimeSeries barseries = new TimeSeries(seriesName[i]);

            if (i == series.size()) {
                int a = 0;
                int b = 0;
                while (a < series.get(0).xDelimitation.size() && b < series.get(1).xDelimitation.size()) {
                    if (series.get(0).xDelimitation.get(a).equals(series.get(1).xDelimitation.get(b))) {
                        double val = series.get(0).getValues().get(a) / series.get(1).getValues().get(b);
                        xyseries.add(new Year(series.get(0).xDelimitation.get(a)), val);
                        scatterseries.add(new Year(series.get(0).xDelimitation.get(a)), val);
                        timeseries.add(new Year(series.get(0).xDelimitation.get(a)), val);
                        barseries.add(new Year(series.get(0).xDelimitation.get(a)), val);
                        a++;
                        b++;

                    } else if (series.get(0).xDelimitation.get(a) < series.get(1).xDelimitation.get(b)) {
                        b++;

                    } else {
                        a++;
                    }
                }
            } else {
                cum += series.get(i).cumulativeAverage;
                for (int j = 0; j < series.get(i).getValues().size(); j++) {
                    xyseries.add(new Year(series.get(i).xDelimitation.get(j)),series.get(i).getValues().get(j));
                    scatterseries.add(new Year(series.get(i).xDelimitation.get(j)),series.get(i).getValues().get(j));
                    barseries.add(new Year(series.get(i).xDelimitation.get(j)),series.get(i).getValues().get(j));
                    timeseries.add(new Year(series.get(i).xDelimitation.get(j)), series.get(i).getValues().get(j));
                }
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
        if (cum == 0){
            throw new DataProcessorException("No data for selected year range");
        }
        // axis names
        String[] axis={
                "CO2 (per capita)",
                "GDP (per capita)",
                "CO2/GDP"
        };
        // types of viewers associated with this analysis type
        String[] viewerTypes={
            "Line Chart",
            "Scatter Plot",
            "Bar Chart",
            "Time Series",
        };
        // completes the analysis, and generates viewer
        Analysis strategyThree = new Analysis(root,method,axis,analysisNames,viewerTypes,"",timeSeriesList,scatterSeriesList,barSeriesList,xySeriesList);




    }

}
