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
    ArrayList<TimeSeries> timeSeriesDataSets;
    ArrayList<TimeSeries> scatterSeriesDataSets;
    ArrayList<DefaultCategoryDataset> barSeriesDataSets;
    ArrayList<XYSeries> XYSeriesSets;
    //ArrayList<String> reportMessages;

    // the constructor
    public StrategySeven(ProgramUI root, ArrayList<ParsedSeries> series, int method){

        seriesName = new String[] {
                "Infant Mortality Rate (per 1000 live births)",
                "Current Health Expenditure per capita"};

        this.root = root;
        analysisNames = root.getAnalysisLabels();
        timeSeriesDataSets = new ArrayList<>();
        barSeriesDataSets = new ArrayList<>();
        XYSeriesSets = new ArrayList<>();
        scatterSeriesDataSets = new ArrayList<>();
        //reportMessages = new ArrayList<>();


        String message = "";
        String title = seriesName[0] +" vs "+seriesName[1]+"\n";
        StringBuilder finalMessage = new StringBuilder();

        finalMessage.append(title+"\n"+"==========================================\n");

        for (int i = 0; i<series.size()-1; i++) {
            XYSeries xyseries = new XYSeries(seriesName[i]);
            TimeSeries scatterseries = new TimeSeries(seriesName[i]);
            DefaultCategoryDataset barseries = new DefaultCategoryDataset();

            for (int j = 0; j < series.get(i).getValues().size()-1; j++) {
                // Getting year will need to be managed for the series some how
                xyseries.add(series.get(i).xDelimitation.get(j), series.get(i).getValues().get(j));
                scatterseries.add(new Year(series.get(i).xDelimitation.get(j)),series.get(i).getValues().get(j));

                message = seriesName[i] +" had a value in year " + series.get(i).xDelimitation.get(j) + " of "+ series.get(i).getValues().get(j)+"\n";
                finalMessage.append(message);

                barseries.setValue(series.get(i).getValues().get(j),seriesName[i], series.get(i).xDelimitation.get(j));
            }
            XYSeriesSets.add(xyseries);
            timeSeriesDataSets.add(scatterseries);

            barSeriesDataSets.add(barseries);

        }
        //System.out.println(finalMessage);

        XYSeriesCollection lineDataSet = new XYSeriesCollection();
        lineDataSet.addSeries(XYSeriesSets.get(0));
        lineDataSet.addSeries(XYSeriesSets.get(1));

        TimeSeriesCollection scatterDataSet = new TimeSeriesCollection();
        scatterDataSet.addSeries(timeSeriesDataSets.get(0));
        scatterDataSet.addSeries(timeSeriesDataSets.get(1));


        Analysis strategySeven = new Analysis(analysisNames,root,timeSeriesDataSets,scatterSeriesDataSets,barSeriesDataSets,XYSeriesSets);
        strategySeven.CreateLineChart(root,method,lineDataSet,"Years","$US",600,400);
        strategySeven.createScatter(root,method,scatterDataSet,"Year","$US",600,400);
        strategySeven.createBar(root,method,barSeriesDataSets,"Years","$US",600,400);
        strategySeven.createReport(root, finalMessage.toString(),600,400);

    }

}
