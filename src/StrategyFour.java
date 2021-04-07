import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;

import java.util.ArrayList;


public class StrategyFour{
    String[] analysisNames ;
    String[] seriesName;
    ProgramUI root;
    String message;

    public StrategyFour(ProgramUI root, ArrayList<ParsedSeries> series, int method) throws DataProcessorException {
        seriesName = new String[]{
                "Forest Area (% of Land Area)"
        };
        this.root = root;
        StringBuilder stringBuilder = new StringBuilder();
        analysisNames = root.getAnalysisLabels();

        ArrayList<TimeSeriesCollection> timeSeriesList = new ArrayList<>();
        ArrayList<TimeSeriesCollection> scatterSeriesList = new ArrayList<>();
        ArrayList<TimeSeriesCollection> xySeriesList = new ArrayList<>();
        ArrayList<TimeSeriesCollection> barSeriesList = new ArrayList<>();



        if (series.size() == 0){
            throw new DataProcessorException("This country has no valid data for the selected years");
        }
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
                message = seriesName[i] +" had a value in year " + series.get(i).xDelimitation.get(j) + " of "+ series.get(i).getValues().get(j)+"\n";
                stringBuilder.append(message);
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
            "Report"
        };
        Analysis strategyFour = new Analysis(root,method,seriesName,analysisNames,analysisTypes,message.toString(),timeSeriesList,scatterSeriesList,barSeriesList,xySeriesList);

    }
}
