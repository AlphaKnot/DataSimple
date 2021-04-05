import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
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

public class StrategyOne extends JFrame {
    String[] analysisNames ;
    public StrategyOne(ProgramUI root, ArrayList<ParsedSeries> series, int method){
        CreateLineChart(root,series,method);
        createScatter(root,series,method);
        createBar(root,series,method);
    }
    public void CreateLineChart(ProgramUI root, ArrayList<ParsedSeries> series, int method){
        String[] seriesName = new String[]{
            "CO2 emissions (metric tons per capita)",
            "Energy use (kg of oil equivalent per capita)",
            "PM2.5 air pollution, mean annual exposure (micrograms per cubic meter)",

        };
        analysisNames = root.getAnalysisLabels();
        ArrayList<XYSeries> sets = new ArrayList<>();

        // Loop to create sets of series.
        for (int i = 0; i<series.size(); i++) {
            XYSeries xyseries = new XYSeries(seriesName[i]);
            for (int j = 0; j < series.get(i).getValues().size(); j++) {
                // Getting year will need to be managed for the series some how
                xyseries.add(series.get(i).xDelimitation.get(j), series.get(i).getValues().get(j));
            }
            sets.add(xyseries);
        }

        // Add sets to XYSeries Collection
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(sets.get(0));
        dataset.addSeries(sets.get(1));
        dataset.addSeries(sets.get(2));

        // Creating JFreeChart from dataset
        JFreeChart chart =  ChartFactory.createXYLineChart(
                analysisNames[method], // Title
                "Years", // x-axis Label
                "", // y-axis Label
                dataset, // Dataset
                PlotOrientation.VERTICAL, // Plot Orientation
                true, // Show Legend
                true, // Use tooltips
                false // Configure chart to generate URLs?
        );


        ChartPanel chartPanel = new ChartPanel(chart){
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(600, 400);
            }


        };
        System.out.println("------ Outputting Graph -------");
        root.getCenter().setLayout(new FlowLayout(FlowLayout.LEFT));
        chartPanel.setBackground(Color.white);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        root.getCenter().add(chartPanel);
        root.validate();
        //root.getCenter().validate();


    }
    private void createScatter(ProgramUI root, ArrayList<ParsedSeries> series, int method) {
        String[] seriesName = new String[]{
                "CO2 emissions (metric tons per capita)",
                "Energy use (kg of oil equivalent per capita)",
                "PM2.5 air pollution, mean annual exposure (micrograms per cubic meter)",

        };
        ArrayList<TimeSeries> datasets = new ArrayList<>();
        analysisNames = root.getAnalysisLabels();
        for (int i = 0; i <series.size();i++) {
            TimeSeries scatterseries = new TimeSeries(seriesName[i]);

            for (int j = 0; j < series.get(i).getValues().size(); j++) {

                scatterseries.add(new Year(series.get(i).xDelimitation.get(j)),series.get(i).getValues().get(j));

            }
            datasets.add(scatterseries);
        }

        // add the data to the chart
        TimeSeriesCollection scatterDataSet = new TimeSeriesCollection();
        scatterDataSet.addSeries(datasets.get(0));
        scatterDataSet.addSeries(datasets.get(1));

        XYPlot plot = new XYPlot();
        XYItemRenderer itemrenderer1 = new XYLineAndShapeRenderer(false, true);
        //XYItemRenderer itemrenderer2 = new XYLineAndShapeRenderer(false, true);

        plot.setDataset(0, scatterDataSet);
        plot.setRenderer(0, itemrenderer1);
        DateAxis domainAxis = new DateAxis("Year");
        plot.setDomainAxis(domainAxis);
        plot.setRangeAxis(new NumberAxis(""));

        //plot.setDataset(1, dataset2);
        // plot.setRenderer(1, itemrenderer2);
        plot.setRangeAxis(1, new NumberAxis("US$"));

        plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis
        plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis

        JFreeChart scatterChart = new JFreeChart(
                analysisNames[method],
                new Font("Serif", java.awt.Font.BOLD, 18),
                plot,
                true);

        ChartPanel chartPanel = new ChartPanel(scatterChart);
        chartPanel.setBackground(Color.white);
        chartPanel.setPreferredSize(new Dimension(600, 400));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // to add to the program ui
        root.getCenter().add(chartPanel);
        root.validate();

    }
    public void createBar(ProgramUI root, ArrayList<ParsedSeries> series, int method){
        // creating an array list of DefaultCategory datasets
        ArrayList<DefaultCategoryDataset> datasets = new ArrayList<>();

        // looping through the values
        for (int i = 0; i <series.size();i++) {
            DefaultCategoryDataset barseries = new DefaultCategoryDataset();

            for (int j = 0; j < series.get(i).getValues().size(); j++) {

                barseries.setValue(series.get(i).getValues().get(j),series.get(i).getSeriesIndicator(), series.get(i).xDelimitation.get(j));
                //setValue(value, indicator, year)
                //add xy (year, value)
                // YEAR    VALUE
                //series.get(i).xDelimitation.get(j),series.get(i).getValues().get(j)
                // VALUE   YEAR
                // series.get(i).getValues().get(j), indicator, series.get(i).xDelimitation.get(j)

            }
            datasets.add(barseries);
        }

        // add the data to the chart
        CategoryPlot plot = new CategoryPlot();
        BarRenderer barrenderer1 = new BarRenderer();
        BarRenderer barrenderer2 = new BarRenderer();

        plot.setDataset(0, datasets.get(0));
        plot.setRenderer(0, barrenderer1);
        CategoryAxis domainAxis = new CategoryAxis("Year");
        plot.setDomainAxis(domainAxis);
        plot.setRangeAxis(new NumberAxis(""));

        plot.setDataset(1, datasets.get(1));
        plot.setRenderer(1, barrenderer2);
        plot.setRangeAxis(1, new NumberAxis("US$"));

        plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis
        plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis

        JFreeChart barChart = new JFreeChart("Infant Mortality vs Health Expenditure",
                new Font("Serif", java.awt.Font.BOLD, 18), plot, true);


        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(600, 400));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);

        // to add to the program ui
        root.getCenter().add(chartPanel);
        root.validate();
    }

    public void OutputGraphs(ProgramUI root, ArrayList<ParsedSeries> series, int method){


    }
}
