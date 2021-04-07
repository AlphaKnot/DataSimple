import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import java.util.ArrayList;

public class StrategySix {
    String[] analysisNames;
    String[] seriesName;
    ProgramUI root;
    ArrayList<TimeSeriesCollection> timeSeriesList;
    ArrayList<TimeSeriesCollection> scatterSeriesList;
    ArrayList<TimeSeriesCollection> barSeriesList;
    ArrayList<TimeSeriesCollection> xySeriesList;

    public StrategySix(ProgramUI root, ArrayList<ParsedSeries> series, int method) {

        seriesName = new String[]{"Hospital Beds","Current Health Care Expenditure","Ratio of Hospital beds to CHE"};

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
                "Beds",
                "Expenditure",
                "Beds/Expenditure"
        };
        String[] viewerTypes={
                "Line Chart",
                "Scatter Plot",
                "Bar Chart",
                "Time Series",
        };

        Analysis strategySix = new Analysis(root,method,axis,analysisNames,viewerTypes,"",timeSeriesList,scatterSeriesList,barSeriesList,xySeriesList);




    }

}