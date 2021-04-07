import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.chart.urls.StandardXYURLGenerator;
import org.jfree.chart.util.Args;
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
        public void CreateLineChart(ProgramUI root, int method, XYSeriesCollection dataset, String yAxisLabel){


            //JFreeChart chart =  ChartFactory.createXYLineChart(
                    //analysisNames[method], // Title
                    //"Years", // x-axis Label
                    //yAxisLabel, // y-axis Label
                    //dataset, // Dataset
                    //PlotOrientation.VERTICAL, // Plot Orientation
                    //true, // Show Legend
                    //true, // Use tooltips
                    //false // Configure chart to generate URLs?
            //);

            Args.nullNotPermitted(PlotOrientation.VERTICAL, "orientation");
            NumberAxis xAxis = new NumberAxis("Years");
            xAxis.setAutoRangeIncludesZero(false);
            NumberAxis yAxis = new NumberAxis(yAxisLabel);
            XYItemRenderer renderer = new XYLineAndShapeRenderer(true, false);
            XYPlot plot = new XYPlot(dataset, xAxis, yAxis, renderer);
            plot.setOrientation(PlotOrientation.VERTICAL);
            renderer.setDefaultToolTipGenerator(new StandardXYToolTipGenerator());

            JFreeChart chart = new JFreeChart(analysisNames[method], JFreeChart.DEFAULT_TITLE_FONT, plot, true);


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
        public void creatPieChart(ProgramUI root, int method, DefaultCategoryDataset piechart){

        }
    // default width = 600, default height = 400, default xAxisLabel = "Years", yAxisLabel = "$US"
        public void createScatter(ProgramUI root, int method, TimeSeriesCollection scatterDataSet, String yAxisLabel) {

            // add the data to the chart
            XYPlot plot = new XYPlot();
            XYItemRenderer itemrenderer = new XYLineAndShapeRenderer(false, true);

            plot.setDataset(0, scatterDataSet);
            plot.setRenderer(0, itemrenderer);

            DateAxis domainAxis = new DateAxis("Years");
            plot.setDomainAxis(domainAxis);
            plot.setRangeAxis(new NumberAxis(""));

            plot.setRangeAxis(0, new NumberAxis(yAxisLabel));

            plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis

            plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis

            JFreeChart scatterChart = new JFreeChart(
                    analysisNames[method],
                    new Font("Serif", Font.BOLD, 18),
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
    // default width = 600, default height = 400, default xAxisLabel = "Years", yAxisLabel = "$US"
        public void createBar(ProgramUI root,int method, ArrayList<DefaultCategoryDataset> datasets, String[] yAxisLabel){

            // add the data to the chart
            CategoryPlot plot = new CategoryPlot();
            CategoryAxis domainAxis = new CategoryAxis("Years");
            plot.setDomainAxis(0,domainAxis);
            for (int i = 0; i < datasets.size(); i++){
                BarRenderer barrenderer = new BarRenderer();
                plot.setDataset(i,datasets.get(i));
                plot.setRenderer(i,barrenderer);
                plot.mapDatasetToRangeAxis(i,i);

                plot.setRangeAxis(new NumberAxis(""));
                plot.setRangeAxis(i, new NumberAxis(yAxisLabel[i]));
            }




            JFreeChart barChart = new JFreeChart(analysisNames[method],
                    new Font("Serif", Font.BOLD, 18), plot, true);


            ChartPanel chartPanel = new ChartPanel(barChart);
            chartPanel.setPreferredSize(new Dimension(600, 400));
            chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            chartPanel.setBackground(Color.white);

            // to add to the program ui
            root.getCenter().add(chartPanel);
            root.validate();
        }
        // default width = 600, default height = 400, default xAxisLabel = "Years", yAxisLabel = "$US"
        public void createTimeSeries(ProgramUI root,int method, TimeSeriesCollection dataset, String yAxisLabel) {
            // Add sets to XYSeries Collection




            XYPlot plot = new XYPlot();

            XYSplineRenderer splinerenderer1 = new XYSplineRenderer();
            XYSplineRenderer splinerenderer2 = new XYSplineRenderer();

            plot.setDataset(0, dataset);
            plot.setRenderer(0, splinerenderer1);


            plot.setDataset(1, dataset);
            plot.setRenderer(1, splinerenderer2);


            plot.setDomainAxis(new DateAxis("Years"));
            plot.setRangeAxis(1, new NumberAxis(yAxisLabel));

            plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis
            plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis

            JFreeChart chart = new JFreeChart(root.getAnalysisLabels()[method],
                    new Font("Serif", Font.BOLD, 18), plot, true);

            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new Dimension(600, 400));
            chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            chartPanel.setBackground(Color.white);
            root.getCenter().add(chartPanel);
            root.validate();

        }
    // default width = 400, default height = 300, default xAxisLabel = "Years", yAxisLabel = "$US"
        public void createReport(ProgramUI root, String finalMessage) {
            JTextArea report = new JTextArea(5,40);
            report.setEditable(false);
            report.setPreferredSize(new Dimension(width, height));
            report.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            report.setBackground(Color.white);

            report.setText(finalMessage);

            report.setLineWrap(true);
            report.setWrapStyleWord(true);

            JPanel container = new JPanel();
            container.add(report);
            JScrollPane scrollPane = new JScrollPane(container);

            root.getCenter().add(scrollPane);
            root.validate();
        }

        public void OutputGraphs(ProgramUI root, ArrayList<ParsedSeries> series, int method){

        }
}

