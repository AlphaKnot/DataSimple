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
import org.jfree.chart.title.TextTitle;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StrategySeven {
    public StrategySeven(ProgramUI root, ArrayList<ParsedSeries> series, int method){

        createLineChartS7(root, series, method);

    }

    public void createLineChartS7(ProgramUI root, ArrayList<ParsedSeries> series, int method){

        // creating an array list of XYSeries datasets
        ArrayList<XYSeries> datasets = new ArrayList<>();

        // looping through the values
        for (ParsedSeries parsedSeries : series) {
            XYSeries xyseries = new XYSeries(parsedSeries.seriesIndicator);

            for (int j = 0; j < parsedSeries.getValues().size(); j++) {
                // Getting year will need to be managed for the series some how
                xyseries.add(parsedSeries.xDelimitation.get(j), parsedSeries.getValues().get(j));
            }
            datasets.add(xyseries);
        }

        // add the data to the chart
        // maybe name the series??? like the kratos example
        XYSeriesCollection lineDataSet = new XYSeriesCollection();
        lineDataSet.addSeries(datasets.get(0));
        lineDataSet.addSeries(datasets.get(1));

        // creates a line chart
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Infant Mortality vs Health Expenditure", // Title
                "Year", // x-axis Label
                "", // y-axis Label
                lineDataSet, // Dataset
                PlotOrientation.VERTICAL, // Plot Orientation
                true, // Show Legend
                true, // Use tooltips
                false // Configure chart to generate URLs?
        );
        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(
                new TextTitle("Infant Mortality vs Health Expenditure", new Font("Serif", java.awt.Font.BOLD, 18)));

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(400, 300));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);

        // to add to the program ui
        root.getCenter().add(chartPanel);
        root.validate();

    }

    public void createScatterS7(ProgramUI root, ArrayList<ParsedSeries> series, int method){
        // creating an array list of TimeSeries datasets
        ArrayList<TimeSeries> datasets = new ArrayList<>();

        // looping through the values
        for (int i = 0; i <series.size();i++) {
            TimeSeries scatterseries = new TimeSeries(series.get(i).seriesIndicator);

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

        JFreeChart scatterChart = new JFreeChart("Infant Mortality vs Health Expenditure",
                new Font("Serif", java.awt.Font.BOLD, 18), plot, true);

        ChartPanel chartPanel = new ChartPanel(scatterChart);
        chartPanel.setPreferredSize(new Dimension(400, 300));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);

        // to add to the program ui
        root.getCenter().add(chartPanel);
        root.validate();

    }

    public void createBarS7(ProgramUI root, ArrayList<ParsedSeries> series, int method){
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
        chartPanel.setPreferredSize(new Dimension(400, 300));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);

        // to add to the program ui
        root.getCenter().add(chartPanel);
        root.validate();
    }

    // pass a location as a parameter
    public void createReport(ProgramUI root, ArrayList<ParsedSeries> series, int method){
        JTextArea report = new JTextArea();
        report.setEditable(false);
        report.setPreferredSize(new Dimension(400, 300));
        report.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        report.setBackground(Color.white);
        String reportMessage;
        String title;
        String yeartoString;
        float infantMort;
        int year;

     /*   reportMessage = "Mortality vs Expenses & Hospital Beds\n" + "==============================\n" + "Year 2018:\n"
                + "\tMortality/1000 births => 5.6\n" + "\tHealth Expenditure per Capita => 10624\n"
                + "\tHospital Beds/1000 people => 2.92\n" + "\n" + "Year 2017:\n" + "\tMortality/1000 births => 5.7\n"
                + "\tHealth Expenditure per Capita => 10209\n" + "\tHospital Beds/1000 people => 2.87\n" + "\n"
                + "Year 2016:\n" + "\tMortality/1000 births => 5.8\n" + "\tHealth Expenditure per Capita => 9877\n"
                + "\tHospital Beds/1000 people => 2.77\n";*/

/*        for (int i = 0; i <series.size();i++) {
            TimeSeries scatterseries = new TimeSeries(series.get(i).seriesIndicator);

            for (int j = 0; j < series.get(i).getValues().size(); j++) {

                scatterseries.add(new Year(series.get(i).xDelimitation.get(j)),series.get(i).getValues().get(j));

            }
            datasets.add(scatterseries);
        }*/

        title = "Infant Mortality vs Health Expenditure\n" + "=============================\n";
        for (int i = 0; i < series.size(); i++){
            for (int j = 0; j <series.get(i).getValues().size(); j++){
                year = series.get(i).xDelimitation.get(j);
                infantMort = series.get(i).getValues().get(j);


            }


        }


    }
}
