import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import java.util.ArrayList;

public class StrategyThree {
    String[] analysisNames;
    String[] seriesNames;
    ProgramUI root;
    ArrayList<TimeSeries> timeSeriesDatasets;
    ArrayList<TimeSeries> scatterSeriesDatasets;
    ArrayList<DefaultCategoryDataset> barSeriesDataSets;
    ArrayList<XYSeries> XYSeriesSets;

    public StrategyThree(ProgramUI root, ArrayList<ParsedSeries> series, int method) {

        seriesNames = new String[]{"CO2","GDP"};

        this.root = root;
        analysisNames = root.getAnalysisLabels();
        timeSeriesDatasets = new ArrayList<>();
        barSeriesDataSets = new ArrayList<>();
        XYSeriesSets = new ArrayList<>();
        scatterSeriesDatasets = new ArrayList<>();
        XYSeries xyseries = new XYSeries(seriesNames[0]);
        TimeSeries scatterseries = new TimeSeries(seriesNames[0]);
        TimeSeries timeseries = new TimeSeries(seriesNames[0]);
        DefaultCategoryDataset barseries = new DefaultCategoryDataset();
        DefaultCategoryDataset piechart = new DefaultCategoryDataset();


        int i = 0;
        int j = 0;
        while(i < series.get(0).xDelimitation.size() && j < series.get(1).xDelimitation.size()){
            if (series.get(0).xDelimitation.get(i).equals(series.get(1).xDelimitation.get(j))){
                double val = series.get(0).getValues().get(i) / series.get(1).getValues().get(j);
                xyseries.add(series.get(0).xDelimitation.get(i),(Number)val);
                scatterseries.add(new Year(series.get(0).xDelimitation.get(i)),val);
                timeseries.add(new Year(series.get(0).xDelimitation.get(i)),val);
                barseries.setValue((Number)series.get(0).xDelimitation.get(i),0,val);
                i++;
                j++;

            } else if (series.get(0).xDelimitation.get(i) < series.get(1).xDelimitation.get(j)){
                j++;

            } else {
                i++;
            }
        }
        XYSeriesSets.add(xyseries);
        scatterSeriesDatasets.add(scatterseries);
        timeSeriesDatasets.add(timeseries);
        barSeriesDataSets.add(barseries);


        XYSeriesCollection XYSeriesDataset = new XYSeriesCollection();
        TimeSeriesCollection scatterDataSet = new TimeSeriesCollection();
        TimeSeriesCollection timeSeriesDataSet = new TimeSeriesCollection();


        XYSeriesDataset.addSeries(XYSeriesSets.get(0));
        scatterDataSet.addSeries(scatterSeriesDatasets.get(0));
        timeSeriesDataSet.addSeries(timeSeriesDatasets.get(0));



        Analysis strategyThree = new Analysis(analysisNames,root,timeSeriesDatasets,scatterSeriesDatasets,barSeriesDataSets,XYSeriesSets);
        strategyThree.CreateLineChart(root,method,XYSeriesDataset,seriesNames);
        strategyThree.createScatter(root,method,scatterDataSet,seriesNames);
        strategyThree.createTimeSeries(root,method,timeSeriesDataSet,seriesNames);
        strategyThree.createBar(root,method,barSeriesDataSets,seriesNames);



    }

}
