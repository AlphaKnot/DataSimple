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
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Note: series.get(0).xDelimitation.get(0) will return the first year
 *
 *
 *
 */


public class Analysis extends JFrame {
        String[] analysisNames ;
        ProgramUI root;
        ArrayList<TimeSeries> timeSeriesDatasets;
        ArrayList<TimeSeries> scatterSeriesDatasets;
        ArrayList<DefaultCategoryDataset> barSeriesDataSets;
        ArrayList<XYSeries> XYSeriesSets;
        public Analysis(String[] analysisNames,ProgramUI root, ArrayList<TimeSeries> timeSeriesDatasets, ArrayList<TimeSeries> scatterSeriesDatasets, ArrayList<DefaultCategoryDataset> barSeriesDataSets, ArrayList<XYSeries> XYSeriesSets){
            this.root = root;
            this.analysisNames = analysisNames;
            this.timeSeriesDatasets = timeSeriesDatasets;
            this.scatterSeriesDatasets = scatterSeriesDatasets;
            this.barSeriesDataSets = barSeriesDataSets;
            this.XYSeriesSets = XYSeriesSets;

        }
    // default width = 600, default height = 400, default xAxisLabel = "Years", yAxisLabel = "$US"
        public void CreateLineChart(ProgramUI root, int method, XYSeriesCollection dataset, String xAxisLabel, String yAxisLabel, int width, int height){


            JFreeChart chart =  ChartFactory.createXYLineChart(
                    analysisNames[method], // Title
                    xAxisLabel, // x-axis Label
                    yAxisLabel, // y-axis Label
                    dataset, // Dataset
                    PlotOrientation.VERTICAL, // Plot Orientation
                    true, // Show Legend
                    true, // Use tooltips
                    false // Configure chart to generate URLs?
            );


            ChartPanel chartPanel = new ChartPanel(chart){
                @Override
                public Dimension getPreferredSize() {
                    return new Dimension(width, height);
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
        public void creatPieChart(ProgramUI root, int method, DefaultCategoryDataset piechart){

        }
    // default width = 600, default height = 400, default xAxisLabel = "Years", yAxisLabel = "$US"
        public void createScatter(ProgramUI root, int method, TimeSeriesCollection scatterDataSet, String xAxisLabel, String yAxisLabel, int width, int height) {

            // add the data to the chart
            XYPlot plot = new XYPlot();
            XYItemRenderer itemrenderer1 = new XYLineAndShapeRenderer(false, true);
            //XYItemRenderer itemrenderer2 = new XYLineAndShapeRenderer(false, true);

            plot.setDataset(0, scatterDataSet);
            plot.setRenderer(0, itemrenderer1);


            DateAxis domainAxis = new DateAxis(xAxisLabel);
            plot.setDomainAxis(domainAxis);
            plot.setRangeAxis(new NumberAxis(""));

            plot.setRangeAxis(1, new NumberAxis(yAxisLabel));

            plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis

            plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis

            JFreeChart scatterChart = new JFreeChart(
                    analysisNames[method],
                    new Font("Serif", Font.BOLD, 18),
                    plot,
                    true);

            ChartPanel chartPanel = new ChartPanel(scatterChart);
            chartPanel.setBackground(Color.white);
            chartPanel.setPreferredSize(new Dimension(width, height));
            chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

            // to add to the program ui
            root.getCenter().add(chartPanel);
            root.validate();

        }
    // default width = 600, default height = 400, default xAxisLabel = "Years", yAxisLabel = "$US"
        public void createBar(ProgramUI root,int method, ArrayList<DefaultCategoryDataset> datasets, String xAxisLabel, String yAxisLabel, int width, int height){

            // add the data to the chart
            CategoryPlot plot = new CategoryPlot();
            BarRenderer barrenderer1 = new BarRenderer();
            BarRenderer barrenderer2 = new BarRenderer();

            plot.setDataset(0, datasets.get(0));
            plot.setRenderer(0, barrenderer1);

            CategoryAxis domainAxis = new CategoryAxis(xAxisLabel);
            plot.setDomainAxis(domainAxis);
            plot.setRangeAxis(new NumberAxis(""));

            plot.setDataset(1, datasets.get(1));
            plot.setRenderer(1, barrenderer2);


            plot.setRangeAxis(1, new NumberAxis(yAxisLabel));

            plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis
            plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis

            JFreeChart barChart = new JFreeChart(analysisNames[method],
                    new Font("Serif", Font.BOLD, 18), plot, true);


            ChartPanel chartPanel = new ChartPanel(barChart);
            chartPanel.setPreferredSize(new Dimension(width, height));
            chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            chartPanel.setBackground(Color.white);

            // to add to the program ui
            root.getCenter().add(chartPanel);
            root.validate();
        }
        // default width = 600, default height = 400, default xAxisLabel = "Years", yAxisLabel = "$US"
        public void createTimeSeries(ProgramUI root,int method, TimeSeriesCollection dataset, String xAxisLabel, String yAxisLabel, int width, int height) {
            // Add sets to XYSeries Collection




            XYPlot plot = new XYPlot();
            XYSplineRenderer splinerenderer1 = new XYSplineRenderer();
            XYSplineRenderer splinerenderer2 = new XYSplineRenderer();

            plot.setDataset(0, dataset);
            plot.setRenderer(0, splinerenderer1);


            plot.setDataset(1, dataset);
            plot.setRenderer(1, splinerenderer2);


            plot.setDomainAxis(new DateAxis(xAxisLabel));
            plot.setRangeAxis(1, new NumberAxis(yAxisLabel));

            plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis
            plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis

            JFreeChart chart = new JFreeChart(root.getAnalysisLabels()[method],
                    new Font("Serif", Font.BOLD, 18), plot, true);

            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new Dimension(width, height));
            chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            chartPanel.setBackground(Color.white);
            root.getCenter().add(chartPanel);
            root.validate();

        }
    // default width = 400, default height = 300, default xAxisLabel = "Years", yAxisLabel = "$US"
        public void createReport(ProgramUI root, String finalMessage,int width, int height) {
            JTextArea report = new JTextArea();
            report.setEditable(false);
            report.setPreferredSize(new Dimension(width, height));
            report.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            report.setBackground(Color.white);

            // check strat7 to see how to get this finalmessage string

            report.setText(finalMessage);
            JScrollPane outputScrollPane = new JScrollPane(report);
            root.getCenter().add(outputScrollPane);
            root.validate();


        }

        public void OutputGraphs(ProgramUI root, ArrayList<ParsedSeries> series, int method){

        }
}

