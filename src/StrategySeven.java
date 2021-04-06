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
            }
            XYSeriesSets.add(xyseries);
            timeSeriesDatasets.add(scatterseries);
            barSeriesDataSets.add(barseries);
            scatterSeriesDatasets.add(scatterseries);

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


        Analysis strategyOne = new Analysis(analysisNames,root,timeSeriesDatasets,scatterSeriesDatasets,barSeriesDataSets,XYSeriesSets);
        strategyOne.CreateLineChart(root,method,XYSeriesDataset);
        strategyOne.createScatter(root,method,scatterDataSet);
        strategyOne.createBar(root,method,barSeriesDataSets);
        strategyOne.createTimeSeries(root,method,timeSeriesDataset);
    }



/*
    *//***
     * creates a line chart for strategy 7
     * @param root the program ui
     * @param series the datasets
     * @param method the index of the method selected
     *//*
    public void createLineChartS7(ProgramUI root, ArrayList<ParsedSeries> series, int method){

        String[] seriesName = new String[]{
                "Current Health Expenditure per capita",
                "Infant Mortality Rate (per 1000 live births)",

        };

        analysisNames = root.getAnalysisLabels();

        // creating an array list of XYSeries datasets
        ArrayList<XYSeries> datasets = new ArrayList<>();

        // looping through the values
        for (int i = 0; i<series.size(); i++) {
            XYSeries xyseries = new XYSeries(seriesName[i]);

            for (int j = 0; j < series.get(i).getValues().size(); j++) {
                // Getting year will need to be managed for the series some how
                xyseries.add(series.get(i).xDelimitation.get(j), series.get(i).getValues().get(j));
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
                analysisNames[method], // Title
                "Year", // x-axis Label
                "", // y-axis Label
                lineDataSet, // Dataset
                PlotOrientation.VERTICAL, // Plot Orientation
                true, // Show Legend
                true, // Use tooltips
                false // Configure chart to generate URLs?
        );

        // thank you kostas i copied this from you
        *//*XYPlot plot = chart.getXYPlot();

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
        root.validate();*//*

        // from ammar's strategy 1 code
        ChartPanel chartPanel = new ChartPanel(chart){
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(600, 400);
            }


        };
        //System.out.println("------ Outputting Graph -------");
        root.getCenter().setLayout(new FlowLayout(FlowLayout.LEFT));
        chartPanel.setBackground(Color.white);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        root.getCenter().add(chartPanel);
        root.validate();

    }

    *//***
     * creates a scatterchart for strategy 7
     * @param root the program ui
     * @param series the datasets
     * @param method the index of the method chosen
     *//*
    public void createScatterS7(ProgramUI root, ArrayList<ParsedSeries> series, int method){

        String[] seriesName = new String[]{
                "Current Health Expenditure per capita",
                "Infant Mortality Rate (per 1000 live births)"

        };

        analysisNames = root.getAnalysisLabels();

        // creating an array list of TimeSeries datasets
        ArrayList<TimeSeries> datasets = new ArrayList<>();

        // looping through the values
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


        // lmao idk if this part even works
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

        JFreeChart scatterChart = new JFreeChart(analysisNames[method],
                new Font("Serif", java.awt.Font.BOLD, 18), plot, true);

        ChartPanel chartPanel = new ChartPanel(scatterChart);
        chartPanel.setPreferredSize(new Dimension(400, 300));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);

        // to add to the program ui
        root.getCenter().add(chartPanel);
        root.validate();

    }

    *//***
     * creates a bar graph
     * @param root the program ui
     * @param series the datasets
     * @param method the index of the analysis chosen on the dropdown
     *//*
    public void createBarS7(ProgramUI root, ArrayList<ParsedSeries> series, int method){

        String[] seriesName = new String[]{
                "Current Health Expenditure per capita",
                "Infant Mortality Rate (per 1000 live births)",

        };

        analysisNames = root.getAnalysisLabels();

        // creating an array list of DefaultCategory datasets
        ArrayList<DefaultCategoryDataset> datasets = new ArrayList<>();

        // looping through the values
        for (int i = 0; i <series.size();i++) {
            DefaultCategoryDataset barseries = new DefaultCategoryDataset();

            for (int j = 0; j < series.get(i).getValues().size(); j++) {

                barseries.setValue(series.get(i).getValues().get(j),seriesName[i], series.get(i).xDelimitation.get(j));
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

        JFreeChart barChart = new JFreeChart(analysisNames[method],
                new Font("Serif", java.awt.Font.BOLD, 18), plot, true);


        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(600, 400));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);

        // to add to the program ui
        root.getCenter().add(chartPanel);
        root.validate();
    }*/

    // pass a location as a parameter

    /***
     * creates a report
     * @param root the program ui
     * @param series the datasets
     * @param method the index of the analysis chosen on the dropdown menu
     */
    public void createReportS7(ProgramUI root, ArrayList<ParsedSeries> series, int method){
        JTextArea report = new JTextArea();
        report.setEditable(false);
        report.setPreferredSize(new Dimension(400, 300));
        report.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        report.setBackground(Color.white);




        analysisNames = root.getAnalysisLabels();

        String[] seriesName = new String[]{
                "Current Health Expenditure per capita",
                "Infant Mortality Rate (per 1000 live births)",

        };

     /*   reportMessage = "Mortality vs Expenses & Hospital Beds\n" + "==============================\n" + "Year 2018:\n"
                + "\tMortality/1000 births => 5.6\n" + "\tHealth Expenditure per Capita => 10624\n"
                + "\tHospital Beds/1000 people => 2.92\n" + "\n" + "Year 2017:\n" + "\tMortality/1000 births => 5.7\n"
                + "\tHealth Expenditure per Capita => 10209\n" + "\tHospital Beds/1000 people => 2.87\n" + "\n"
                + "Year 2016:\n" + "\tMortality/1000 births => 5.8\n" + "\tHealth Expenditure per Capita => 9877\n"
                + "\tHospital Beds/1000 people => 2.77\n";*/

        ArrayList<String> reportMessages = new ArrayList<>();
        String message = "";
        String title;
        String finalMessage;

        title = analysisNames[method] + "=============================\n";
        for (int i = 0; i < series.size(); i++){
            for (int j = 0; j <series.get(i).getValues().size(); j++){
                message = seriesName[i] +" had a value in:" + series.get(i).xDelimitation.get(j) + "of : "+ series.get(i).getValues().get(j)+"\n";
            }
            reportMessages.add(message);
        }
        finalMessage = title + reportMessages;
        report.setText(finalMessage);

        JScrollPane outputScrollPane = new JScrollPane(report);
        //west.add(outputScrollPane);

        root.getCenter().add(outputScrollPane);
        root.validate();



    }
}
