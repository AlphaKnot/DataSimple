import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import java.util.ArrayList;


public class StrategyOne{
    String[] analysisNames ;
    String[] seriesName;
    ProgramUI root;
    ArrayList<TimeSeries> timeSeriesDatasets;
    ArrayList<TimeSeries> scatterSeriesDatasets;
    ArrayList<DefaultCategoryDataset> barSeriesDataSets;
    ArrayList<XYSeries> XYSeriesSets;
    public StrategyOne(ProgramUI root, ArrayList<ParsedSeries> series, int method){
        seriesName = new String[]{
                "CO2 emissions (metric tons per capita)",
                "Energy use (kg of oil equivalent per capita)",
                "PM2.5 air pollution, mean annual exposure (micrograms per cubic meter)",

        };
        this.root = root;
        analysisNames = root.getAnalysisLabels();
        timeSeriesDatasets = new ArrayList<>();
        barSeriesDataSets = new ArrayList<>();
        XYSeriesSets = new ArrayList<>();
        scatterSeriesDatasets = new ArrayList<>();


        for (int i = 0; i < series.size(); i++) {
            XYSeries xyseries = new XYSeries(seriesName[i]);
            TimeSeries scatterseries = new TimeSeries(seriesName[i]);
            TimeSeries timeseries = new TimeSeries(seriesName[i]);
            DefaultCategoryDataset barseries = new DefaultCategoryDataset();
            for (int j = 0; j < series.get(i).getValues().size(); j++) {
                xyseries.add(series.get(i).xDelimitation.get(j),series.get(i).getValues().get(j));
                scatterseries.add(new Year(series.get(i).xDelimitation.get(j)),series.get(i).getValues().get(j));
                barseries.setValue(series.get(i).getValues().get(j),seriesName[i], series.get(i).xDelimitation.get(j));
                timeseries.add(new Year(series.get(i).xDelimitation.get(j)), series.get(i).getValues().get(j));
            }
            XYSeriesSets.add(xyseries);
            timeSeriesDatasets.add(scatterseries);
            barSeriesDataSets.add(barseries);
            scatterSeriesDatasets.add(scatterseries);

        }
        XYSeriesCollection XYSeriesDataset = new XYSeriesCollection();
        XYSeriesDataset.addSeries(XYSeriesSets.get(0));
        XYSeriesDataset.addSeries(XYSeriesSets.get(1));
        XYSeriesDataset.addSeries(XYSeriesSets.get(2));

        TimeSeriesCollection scatterDataSet = new TimeSeriesCollection();
        scatterDataSet.addSeries(scatterSeriesDatasets.get(0));
        scatterDataSet.addSeries(scatterSeriesDatasets.get(1));

        TimeSeriesCollection timeSeriesDataset = new TimeSeriesCollection();
        timeSeriesDataset.addSeries(timeSeriesDatasets.get(0));
        timeSeriesDataset.addSeries(timeSeriesDatasets.get(1));
        timeSeriesDataset.addSeries(timeSeriesDatasets.get(2));


        Analysis strategyOne = new Analysis(analysisNames,root,timeSeriesDatasets,scatterSeriesDatasets,barSeriesDataSets,XYSeriesSets);
        strategyOne.CreateLineChart(root,method,XYSeriesDataset,"Years","$US",600,400);
        strategyOne.createScatter(root,method,scatterDataSet,"Years","$US",600,400);
        strategyOne.createBar(root,method,barSeriesDataSets,"Years","$US",600,400);
        strategyOne.createTimeSeries(root,method,timeSeriesDataset,"Years","$US",600,400);
    }
}
