import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.util.TableOrder;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.concurrent.Flow;

public class StrategyEight {

    String[] analysisNames;
    String[] seriesName;
    ProgramUI root;
    ArrayList<TimeSeries> timeSeriesDatasets;
    ArrayList<TimeSeries> scatterSeriesDatasets;
    ArrayList<DefaultCategoryDataset> barSeriesDataSets;
    ArrayList<XYSeries> XYSeriesSets;

    ArrayList<DefaultCategoryDataset> pieDatasets;

    // Ratio of Government expenditure on education,
    // total (% of GDP) vs Current health expenditure (% of GDP).
    public StrategyEight(ProgramUI root, ArrayList<ParsedSeries> series, int method){

        // Ratio of Government expenditure on education, total (% of GDP) vs Current health expenditure (% of GDP)
        seriesName = new String[] {"Education Expenditure to GDP", "Health Expenditure to GDP"};

        this.root = root;
        analysisNames = root.getAnalysisLabels();
        timeSeriesDatasets = new ArrayList<>();
        barSeriesDataSets = new ArrayList<>();
        XYSeriesSets = new ArrayList<>();
        scatterSeriesDatasets = new ArrayList<>();

        pieDatasets = new ArrayList<>();

        String message = "";
        String title = seriesName[0] +" vs "+seriesName[1]+"\n";
        StringBuilder finalMessage = new StringBuilder();

        finalMessage.append(title+"\n"+"==========================================\n");

        for (int i = 0; i < series.size(); i++) {
            XYSeries xyseries = new XYSeries(seriesName[i]);
            TimeSeries scatterseries = new TimeSeries(seriesName[i]);
            TimeSeries timeseries = new TimeSeries(seriesName[i]);
            DefaultCategoryDataset barseries = new DefaultCategoryDataset();
            DefaultCategoryDataset pieseries = new DefaultCategoryDataset();

            for (int j = 0; j < series.get(i).getValues().size(); j++) {

                xyseries.add(series.get(i).xDelimitation.get(j), series.get(i).getValues().get(j));
                scatterseries.add(new Year(series.get(i).xDelimitation.get(j)),series.get(i).getValues().get(j));
                timeseries.add(new Year(series.get(i).xDelimitation.get(j)),series.get(i).getValues().get(j));
                barseries.setValue(series.get(i).getValues().get(j),seriesName[i], series.get(i).xDelimitation.get(j));

                // the report message to the added to the array-jen
                message = seriesName[i] + " had a value in " + series.get(i).xDelimitation.get(j) + " of " + series.get(i).getValues().get(j) + "\n";
                finalMessage.append(message);

                pieseries.addValue(series.get(i).getValues().get(j),"",seriesName[i]);
            }
            XYSeriesSets.add(xyseries);
            timeSeriesDatasets.add(scatterseries);
            barSeriesDataSets.add(barseries);
            scatterSeriesDatasets.add(scatterseries);

            pieDatasets.add(pieseries);
        }

        XYSeriesCollection lineDataSet = new XYSeriesCollection();
        lineDataSet.addSeries(XYSeriesSets.get(0));
        lineDataSet.addSeries(XYSeriesSets.get(1));

        TimeSeriesCollection timeSeriesDataset = new TimeSeriesCollection();
        timeSeriesDataset.addSeries(timeSeriesDatasets.get(0));
        timeSeriesDataset.addSeries(timeSeriesDatasets.get(1));

        TimeSeriesCollection scatterDataSet = new TimeSeriesCollection();
        scatterDataSet.addSeries(scatterSeriesDatasets.get(0));
        scatterDataSet.addSeries(scatterSeriesDatasets.get(1));


        Analysis strategyEight = new Analysis(analysisNames, root, timeSeriesDatasets, scatterSeriesDatasets, barSeriesDataSets, XYSeriesSets);
        strategyEight.CreateLineChart(root, method, lineDataSet,"Years","$US",600,400);
        strategyEight.createTimeSeries(root, method, timeSeriesDataset,"Years","$US",600,400);
        strategyEight.createBar(root, method, barSeriesDataSets,"Years","US",600,400);
        strategyEight.createScatter(root, method, scatterDataSet,"Years","$US",600,400);
        strategyEight.createReport(root,finalMessage.toString(),600,400);

        //strategyEight.createPieChart(root,method,pieDatasets);
    }

}

