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
import org.jfree.data.category.DefaultCategoryDataset;
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

public class StrategySeven {

    // represents the dropdown of analyses
    String[] analysisNames;

    String[] seriesName;
    ProgramUI root;
    ArrayList<TimeSeries> timeSeriesDatasets;
    ArrayList<TimeSeries> scatterSeriesDatasets;
    ArrayList<DefaultCategoryDataset> barSeriesDataSets;
    ArrayList<XYSeries> XYSeriesSets;

    // the constructor
    public StrategySeven(ProgramUI root, ArrayList<ParsedSeries> series, int method){
    /*    createLineChartS7(root, series, method);
        createScatterS7(root,series,method);
        createBarS7(root,series,method);
        createReportS7(root,series,method);*/

        String[] seriesName = new String[]{
                "Current Health Expenditure per capita",
                "Infant Mortality Rate (per 1000 live births)",
        };

        this.root = root;
        analysisNames = root.getAnalysisLabels();

        timeSeriesDatasets = new ArrayList<>();
        barSeriesDataSets = new ArrayList<>();
        XYSeriesSets = new ArrayList<>();
        scatterSeriesDatasets = new ArrayList<>();

        // this part is setting up the report-jen
        ArrayList<String> reportMessages = new ArrayList<>();
        String message = "";
        String title;
        String finalMessage;



        for (int i = 0; i <= series.size(); i++) {
            XYSeries xyseries = new XYSeries(seriesName[i]);
            TimeSeries scatterseries = new TimeSeries(seriesName[i]);
            TimeSeries timeseries = new TimeSeries(seriesName[i]);
            DefaultCategoryDataset barseries = new DefaultCategoryDataset();
            for (int j = 0; j < series.get(i).getValues().size(); j++) {
                xyseries.add(series.get(i).xDelimitation.get(j),series.get(i).getValues().get(j));
                scatterseries.add(new Year(series.get(i).xDelimitation.get(j)),series.get(i).getValues().get(j));
                barseries.setValue(series.get(i).getValues().get(j),seriesName[i], series.get(i).xDelimitation.get(j));
                timeseries.add(new Year(series.get(i).xDelimitation.get(j)), series.get(i).getValues().get(j));


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
        XYSeriesCollection XYSeriesDataset = new XYSeriesCollection();
        XYSeriesDataset.addSeries(XYSeriesSets.get(0));
        XYSeriesDataset.addSeries(XYSeriesSets.get(1));
        //XYSeriesDataset.addSeries(XYSeriesSets.get(2));

        TimeSeriesCollection scatterDataSet = new TimeSeriesCollection();
        scatterDataSet.addSeries(scatterSeriesDatasets.get(0));
        scatterDataSet.addSeries(scatterSeriesDatasets.get(1));

        TimeSeriesCollection timeSeriesDataset = new TimeSeriesCollection();
        timeSeriesDataset.addSeries(timeSeriesDatasets.get(0));
        timeSeriesDataset.addSeries(timeSeriesDatasets.get(1));
       // timeSeriesDataset.addSeries(timeSeriesDatasets.get(2));
        
        


        Analysis strategySeven = new Analysis(analysisNames,root,timeSeriesDatasets,scatterSeriesDatasets,barSeriesDataSets,XYSeriesSets);
        strategySeven.CreateLineChart(root,method,XYSeriesDataset);
        strategySeven.createScatter(root,method,scatterDataSet);
        strategySeven.createBar(root,method,barSeriesDataSets);
        strategySeven.createTimeSeries(root,method,timeSeriesDataset);
        
        // creating the report
        strategySeven.createReport(root, method,reportMessages);
    }
    
}
