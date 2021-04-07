import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import java.util.ArrayList;

public class StrategyThree {
    String[] analysisNames;
    String[] seriesName;
    ProgramUI root;
    ArrayList<TimeSeriesCollection> timeSeriesList;
    ArrayList<TimeSeriesCollection> scatterSeriesList;
    ArrayList<TimeSeriesCollection> barSeriesList;
    ArrayList<TimeSeriesCollection> xySeriesList;

    public StrategyThree(ProgramUI root, ArrayList<ParsedSeries> series, int method) {

        seriesName = new String[]{"CO2 Emissions (Per Capita)","GDP (Per Capita)","Ratio of CO2 to GDP"};

        this.root = root;
        analysisNames = root.getAnalysisLabels();
        timeSeriesList = new ArrayList<>();
        barSeriesList = new ArrayList<>();
        xySeriesList = new ArrayList<>();
        scatterSeriesList = new ArrayList<>();

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

        String[] axis={
                "CO2 (per capita)",
                "GDP (per capita)",
                "CO2/GDP"
        };

        System.out.println(xySeriesList.size());
        System.out.println(scatterSeriesList.size());
        System.out.println(timeSeriesList.size());
        System.out.println(barSeriesList.size());
        Analysis strategyThree = new Analysis(analysisNames,root,timeSeriesList,scatterSeriesList,barSeriesList,xySeriesList);
        strategyThree.CreateLineChart(method,axis);
        strategyThree.createScatter(method,axis);
        strategyThree.createTimeSeries(method,axis);
        strategyThree.createBar(method,axis);



    }

}
