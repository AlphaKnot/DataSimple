import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;


public class StrategyOne{
    Vector<String> viewDropdownList;
    String[] analysisNames ;
    String[] seriesName;
    ProgramUI root;
    Analysis strategyOne;
    Boolean[] GraphAlreadySet;
    Viewer myView;
    public StrategyOne(ProgramUI root, ArrayList<ParsedSeries> series, int method){
        seriesName = new String[]{
                "CO2 emissions (metric tons per capita) ",
                "Energy use (kg of oil equivalent per capita) ",
                "PM2.5 air pollution, mean annual exposure (micrograms per cubic meter) ",

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
        String[] viewerTypes = {
            "Line Chart",
            "Scatter Plot",
            "Bar Chart",
            "Time Series",
            "Report"
        };
        String[] axis={
                "(metric tonper capita)",
                "(kg per capita)",
                "(micrograms per cubic meter)"
        };

        strategyOne = new Analysis(root,method,axis,analysisNames,viewerTypes,"",timeSeriesList,scatterSeriesList,barSeriesList,xySeriesList);
    }

    public Analysis getAnalysis() {
        return strategyOne;
    }

    public Boolean[] getGraphAlreadySet() {
        return myView.getGraphAlreadySet();
    }

}
