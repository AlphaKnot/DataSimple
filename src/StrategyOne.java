import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;

import java.util.ArrayList;


public class StrategyOne{
    String[] analysisNames ;
    String[] seriesName;
    ProgramUI root;
    public StrategyOne(ProgramUI root, ArrayList<ParsedSeries> series, int method){
        seriesName = new String[]{
                "CO2 emissions (metric tons per capita)",
                "Energy use (kg of oil equivalent per capita)",
                "PM2.5 air pollution, mean annual exposure (micrograms per cubic meter)",

        };
        this.root = root;

        analysisNames = root.getAnalysisLabels();

        ArrayList<TimeSeriesCollection> timeSeriesList = new ArrayList<>();
        ArrayList<TimeSeriesCollection> scatterSeriesList = new ArrayList<>();
        ArrayList<TimeSeriesCollection> xySeriesList = new ArrayList<>();
        ArrayList<TimeSeriesCollection> barSeriesList = new ArrayList<>();




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

        Analysis strategyOne = new Analysis(analysisNames,root,timeSeriesList,scatterSeriesList,barSeriesList,xySeriesList);
        strategyOne.CreateLineChart(method,seriesName);
        strategyOne.createScatter(method,seriesName);
        strategyOne.createBar(method,seriesName);
        strategyOne.createTimeSeries(method,seriesName);
    }
}
