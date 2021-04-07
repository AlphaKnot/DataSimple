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


        String message = "";
        String title = seriesName[0] +" vs "+seriesName[1]+"\n";
        StringBuilder finalMessage = new StringBuilder();

        finalMessage.append(title+"\n"+"==========================================\n");

        for (int i = 0; i<series.size()-1; i++) {
            XYSeries xyseries = new XYSeries(seriesName[i]);
            TimeSeries scatterseries = new TimeSeries(seriesName[i]);
            TimeSeries timeseries = new TimeSeries(seriesName[i]);
            DefaultCategoryDataset barseries = new DefaultCategoryDataset();

            for (int j = 0; j < series.get(i).getValues().size()-1; j++) {
                xyseries.add(series.get(i).xDelimitation.get(j), series.get(i).getValues().get(j));
                scatterseries.add(new Year(series.get(i).xDelimitation.get(j)),series.get(i).getValues().get(j));
                timeseries.add(new Year(series.get(i).xDelimitation.get(j)),series.get(i).getValues().get(j));
                barseries.setValue(series.get(i).getValues().get(j),seriesName[i], series.get(i).xDelimitation.get(j));

                message = seriesName[i] +" had a value in year " + series.get(i).xDelimitation.get(j) + " of "+ series.get(i).getValues().get(j)+"\n";
                finalMessage.append(message);

            }
            XYSeriesSets.add(xyseries);
            timeSeriesDataSets.add(timeseries);
            barSeriesDataSets.add(barseries);
            scatterSeriesDataSets.add(scatterseries);
        }

        XYSeriesCollection lineDataSet = new XYSeriesCollection();
        lineDataSet.addSeries(XYSeriesSets.get(0));
        lineDataSet.addSeries(XYSeriesSets.get(1));

        TimeSeriesCollection timeSeriesDataset = new TimeSeriesCollection();
        timeSeriesDataset.addSeries(timeSeriesDataSets.get(0));
        timeSeriesDataset.addSeries(timeSeriesDataSets.get(1));

        TimeSeriesCollection scatterDataSet = new TimeSeriesCollection();
        scatterDataSet.addSeries(scatterSeriesDataSets.get(0));
        scatterDataSet.addSeries(scatterSeriesDataSets.get(1));


        Analysis strategySeven = new Analysis(analysisNames,root,timeSeriesDataSets,scatterSeriesDataSets,barSeriesDataSets,XYSeriesSets);
        strategySeven.CreateLineChart(root,method,lineDataSet,"Years","$US",600,400);
        strategySeven.createTimeSeries(root,method,timeSeriesDataset,"Years","$US",600,400);
        strategySeven.createScatter(root,method,scatterDataSet,"Year","$US",600,400);
        strategySeven.createBar(root,method,barSeriesDataSets,"Years","$US",600,400);
        strategySeven.createReport(root, finalMessage.toString(),600,400);

    }

}
