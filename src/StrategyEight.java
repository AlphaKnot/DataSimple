import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.util.TableOrder;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

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
    ArrayList<DefaultCategoryDataset> reportDataSets;

    // Ratio of Government expenditure on education,
    // total (% of GDP) vs Current health expenditure (% of GDP).
    public StrategyEight(ProgramUI root, ArrayList<ParsedSeries> series, int method){
        createPieS8(root, series, method);



        // Ratio of Government expenditure on education, total (% of GDP) vs Current health expenditure (% of GDP)
        seriesName = new String[] {"Education Expenditure to GDP", "Health Expenditure to GDP"};

        this.root = root;
        analysisNames = root.getAnalysisLabels();
        timeSeriesDatasets = new ArrayList<>();
        barSeriesDataSets = new ArrayList<>();
        XYSeriesSets = new ArrayList<>();
        scatterSeriesDatasets = new ArrayList<>();
        //DefaultCategoryDataset reportseries = new DefaultCategoryDataset();

        // this part is setting up the report-jen
        ArrayList<String> reportMessages = new ArrayList<>();
        String message = "";
        String title;
        String finalMessage;

        
        //title = analysisNames[method] + "=============================\n";

        // need to create report[], scatter[X], bar[], line[X], pie[FIX]
        for (int i = 0; i < series.size(); i++) {
            XYSeries xyseries = new XYSeries(seriesName[i]);
            TimeSeries scatterseries = new TimeSeries(seriesName[i]);
            TimeSeries timeseries = new TimeSeries(seriesName[i]);


            DefaultCategoryDataset barseries = new DefaultCategoryDataset();
            for (int j = 0; j < series.get(i).getValues().size(); j++) {
                // do division edu/health
                // maybe fix the 0 and 1 getters???
                double val = series.get(0).getValues().get(i) / series.get(1).getValues().get(j);
                xyseries.add(series.get(i).xDelimitation.get(j),(Number) val);
                scatterseries.add(new Year(series.get(i).xDelimitation.get(j)),val);
                barseries.setValue(val,seriesName[i], series.get(i).xDelimitation.get(j));
                timeseries.add(new Year(series.get(i).xDelimitation.get(j)), val);

                // the report message to the added to the array-jen
                message = seriesName[i] + " had a value in:" + series.get(i).xDelimitation.get(j) + "of : " + series.get(i).getValues().get(j) + "\n";
            }
            XYSeriesSets.add(xyseries);
            timeSeriesDatasets.add(scatterseries);
            barSeriesDataSets.add(barseries);
            scatterSeriesDatasets.add(scatterseries);

            // adding the message to the array-jen
            reportMessages.add(message);

        }
        //finalMessage = title + reportMessages;


        XYSeriesCollection XYSeriesDataset = new XYSeriesCollection();
        TimeSeriesCollection scatterDataSet = new TimeSeriesCollection();
        TimeSeriesCollection timeSeriesDataSet = new TimeSeriesCollection();

        XYSeriesDataset.addSeries(XYSeriesSets.get(0));
        scatterDataSet.addSeries(scatterSeriesDatasets.get(0));
        timeSeriesDataSet.addSeries(timeSeriesDatasets.get(0));

        Analysis strategyEight = new Analysis(analysisNames, root, timeSeriesDatasets, scatterSeriesDatasets, barSeriesDataSets, XYSeriesSets);
        strategyEight.CreateLineChart(root, method, XYSeriesDataset);
        strategyEight.createScatter(root, method, scatterDataSet);
        strategyEight.createTimeSeries(root, method, timeSeriesDataSet);
        
        // creating the report
        strategyEight.createReport(root, method,reportMessages);

    }

    public void createPieS8(ProgramUI root, ArrayList<ParsedSeries> series, int method){

        String[] seriesName = new String[]{
                "Ratio of Education Expenditure to Total GDP",
                "Ratio of Health Expenditure to Total GDP",
        };

        analysisNames = root.getAnalysisLabels();

        ArrayList<DefaultCategoryDataset> datasets = new ArrayList<>();

        for (int i = 0; i<series.size(); i++) {
            DefaultCategoryDataset pieseries = new DefaultCategoryDataset();

            for (int j = 0; j < series.get(i).getValues().size(); j++) {
                // Getting year will need to be managed for the series some how

                // adds the float value, the spending and the indicator name
                // idk how it will add up to 100 OOOP
                pieseries.addValue(series.get(i).getValues().get(j),"",seriesName[i]);
            }
            datasets.add(pieseries);
        }


        // i frankly have no idea if this works i fucking hate pie charts
        JFreeChart pieChart = ChartFactory.createMultiplePieChart(analysisNames[method], datasets.get(0), TableOrder.BY_COLUMN, true, true, false);
        JFreeChart pieChart2 = ChartFactory.createMultiplePieChart(analysisNames[method], datasets.get(1), TableOrder.BY_COLUMN, true, true, false);

        ChartPanel chartPanel = new ChartPanel(pieChart);
        ChartPanel chartPanel1 = new ChartPanel(pieChart2);
        chartPanel.setPreferredSize(new Dimension(400, 300));
        chartPanel1.setPreferredSize(new Dimension(400, 300));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel1.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        chartPanel1.setBackground(Color.white);
        //west.add(chartPanel);
        root.getCenter().add(chartPanel);
        root.getCenter().add(chartPanel1);
        root.validate();
    }
}

