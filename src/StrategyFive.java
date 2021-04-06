import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.*;
import java.util.ArrayList;

public class StrategyFive extends JFrame {
    String[] analysisNames;

    public StrategyFive(ProgramUI root, ArrayList<ParsedSeries> series, int method){
        String[] seriesName = new String[]{"Average govt expenditure on education"};
        analysisNames = root.getAnalysisLabels();
        ArrayList<TimeSeries> timeSeriesDatasets = new ArrayList<>();
        ArrayList<DefaultCategoryDataset> barSeriesDataSets = new ArrayList<>();
        ArrayList<XYSeries> XYSeriesSets = new ArrayList<>();
        ArrayList<TimeSeries>scatterSeriesDatasets = new ArrayList<>();
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




        TimeSeriesCollection scatterDataSet = new TimeSeriesCollection();
        scatterDataSet.addSeries(scatterSeriesDatasets.get(0));


        TimeSeriesCollection timeSeriesDataset = new TimeSeriesCollection();
        timeSeriesDataset.addSeries(timeSeriesDatasets.get(0));

        //CategoryDataset barSeriesDataset = new DefaultCategoryDataset();


        Analysis strategyFive = new Analysis(analysisNames,root,timeSeriesDatasets,scatterSeriesDatasets,barSeriesDataSets,XYSeriesSets);
        strategyFive.CreateLineChart(root,method,XYSeriesDataset);
        strategyFive.createScatter(root,method,scatterDataSet);
        strategyFive.createBar(root,method,barSeriesDataSets);
        //strategyFive.createTimeSeries(root,method,timeSeriesDataset);
    }
}
