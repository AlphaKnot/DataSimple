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
        public void CreateLineChart(ProgramUI root, int method, XYSeriesCollection dataset, String[] seriesNames){



            XYPlot plot = new XYPlot();
            XYItemRenderer itemrenderer = new XYLineAndShapeRenderer(true, false);

            plot.setOrientation(PlotOrientation.VERTICAL);

            itemrenderer.setDefaultToolTipGenerator(new StandardXYToolTipGenerator());


            plot.setDataset(dataset);
            plot.setRenderer(itemrenderer);
            plot.mapDatasetToDomainAxis(0,0);

            plot.setDomainAxis(new DateAxis("Years"));
            plot.setRangeAxis(new NumberAxis(""));

            for (int i = 0; i < seriesNames.length; i++) {
                plot.mapDatasetToRangeAxis(i,i);
                plot.setRangeAxis(i, new NumberAxis(seriesNames[i]));
            }

            JFreeChart chart = new JFreeChart(analysisNames[method], JFreeChart.DEFAULT_TITLE_FONT, plot, true);
            addToUI(root,chart);


        }
        public void creatPieChart(ProgramUI root, int method, DefaultCategoryDataset piechart){

        }
        public void createScatter(ProgramUI root, int method, TimeSeriesCollection scatterDataSet, String[] seriesNames) {

            XYPlot plot = new XYPlot();
            XYItemRenderer itemrenderer = new XYLineAndShapeRenderer(false, true);

            plot.setDataset(0, scatterDataSet);
            plot.setRenderer(0, itemrenderer);

            DateAxis domainAxis = new DateAxis("Years");
            plot.setDomainAxis(domainAxis);
            plot.setRangeAxis(new NumberAxis(""));
            for (int i = 0; i < seriesNames.length; i++){
                plot.setRangeAxis(i,new NumberAxis(seriesNames[i]));
                plot.mapDatasetToRangeAxis(i,i);
            }

            JFreeChart scatterChart = new JFreeChart(analysisNames[method], new Font("Serif", Font.BOLD, 18), plot, true);
            addToUI(root,scatterChart);

        }
        public void createBar(ProgramUI root,int method, ArrayList<DefaultCategoryDataset> datasets, String[] seriesNames){


            CategoryPlot plot = new CategoryPlot();
            CategoryAxis domainAxis = new CategoryAxis("Years");
            plot.setDomainAxis(0,domainAxis);
            for (int i = 0; i < datasets.size(); i++){
                BarRenderer barrenderer = new BarRenderer();
                plot.setDataset(i,datasets.get(i));
                plot.setRenderer(i,barrenderer);
                plot.mapDatasetToRangeAxis(i,i);

                plot.setRangeAxis(new NumberAxis(""));
                plot.setRangeAxis(i, new NumberAxis(seriesNames[i]));
            }
            JFreeChart barChart = new JFreeChart(analysisNames[method], new Font("Serif", Font.BOLD, 18), plot, true);
            addToUI(root,barChart);

        }
        public void createTimeSeries(ProgramUI root,int method, TimeSeriesCollection dataset, String[] seriesNames) {

            XYPlot plot = new XYPlot();

            XYSplineRenderer splinerenderer1 = new XYSplineRenderer();
            XYSplineRenderer splinerenderer2 = new XYSplineRenderer();

            plot.setDataset(0, dataset);
            plot.setRenderer(0, splinerenderer1);


            plot.setDataset(1, dataset);
            plot.setRenderer(1, splinerenderer2);


            plot.setDomainAxis(new DateAxis("Years"));
            plot.setRangeAxis(0, new NumberAxis(seriesNames[0]));

            plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis
            plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis



            JFreeChart chart = new JFreeChart(root.getAnalysisLabels()[method],new Font("Serif", Font.BOLD, 18), plot, true);
            addToUI(root,chart);

        }
        public void createReport(ProgramUI root, String finalMessage) {
            JTextArea report = new JTextArea();
            report.setEditable(false);
            report.setPreferredSize(new Dimension(600, 400));
            report.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            report.setBackground(Color.white);

            report.setText(finalMessage);
            JScrollPane outputScrollPane = new JScrollPane(report);
            root.getCenter().add(outputScrollPane);
            root.validate();

        }
        private void addToUI(ProgramUI root,JFreeChart chart){
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new Dimension(600, 400));
            chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            chartPanel.setBackground(Color.white);
            root.getCenter().add(chartPanel);
            root.validate();
        }
        public void OutputGraphs(ProgramUI root, ArrayList<ParsedSeries> series, int method){

        }
}

