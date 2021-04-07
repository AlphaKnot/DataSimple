import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.urls.StandardXYURLGenerator;
import org.jfree.chart.util.Args;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.*;
import java.util.List;

/**
 * Note: series.get(0).xDelimitation.get(0) will return the first year
 *
 *
 *
 */


public class Analysis extends JFrame {
        String[] analysisNames ;
        ProgramUI root;
        ArrayList<TimeSeriesCollection> timeSeriesCollection;
        ArrayList<TimeSeriesCollection> scatterSeriesCollection;
        ArrayList<TimeSeriesCollection> barSeriesCollection;
        ArrayList<TimeSeriesCollection> xySeriesCollection;
        public Analysis(String[] analysisNames,ProgramUI root, ArrayList<TimeSeriesCollection> timeSeriesCollection, ArrayList<TimeSeriesCollection> scatterSeriesCollection, ArrayList<TimeSeriesCollection> barSeriesCollection, ArrayList<TimeSeriesCollection> xySeriesCollection){
            this.root = root;
            this.analysisNames = analysisNames;
            this.timeSeriesCollection = timeSeriesCollection;
            this.scatterSeriesCollection = scatterSeriesCollection;
            this.barSeriesCollection = barSeriesCollection;
            this.xySeriesCollection = xySeriesCollection;

        }
        public void CreateLineChart(int method, String[] seriesNames){



            XYPlot plot = new XYPlot();

            plot.setOrientation(PlotOrientation.VERTICAL);
            plot.mapDatasetToDomainAxis(0,0);
            plot.setDomainAxis(new DateAxis("Years"));
            plot.setRangeAxis(new NumberAxis(""));

            for (int i = 0; i < seriesNames.length; i++) {
                XYItemRenderer itemrenderer = new XYLineAndShapeRenderer(true, false);
                itemrenderer.setDefaultToolTipGenerator(new StandardXYToolTipGenerator());
                plot.setDataset(i,xySeriesCollection.get(i));
                plot.setRenderer(i,itemrenderer);
                plot.mapDatasetToRangeAxis(i,i);
                plot.setRangeAxis(i, new NumberAxis(seriesNames[i]));
            }

            JFreeChart chart = new JFreeChart(analysisNames[method], JFreeChart.DEFAULT_TITLE_FONT, plot, true);

            addToUI(chart);


        }
        public void creatPieChart(ProgramUI root, int method, DefaultCategoryDataset piechart){

        }
        public void createScatter(int method, String[] seriesNames) {

            XYPlot plot = new XYPlot();



            DateAxis domainAxis = new DateAxis("Years");
            plot.setDomainAxis(domainAxis);
            plot.setRangeAxis(new NumberAxis(""));

            for (int i = 0; i < seriesNames.length; i++){
                plot.setDataset(i,scatterSeriesCollection.get(i));
                XYItemRenderer itemrenderer = new XYLineAndShapeRenderer(false, true);
                plot.setRenderer(i,itemrenderer);
                plot.setRangeAxis(i,new NumberAxis(seriesNames[i]));
                plot.mapDatasetToRangeAxis(i,i);
            }

            JFreeChart scatterChart = new JFreeChart(analysisNames[method], new Font("Serif", Font.BOLD, 18), plot, true);
            addToUI(scatterChart);

        }
        public void createBar(int method,String[] seriesNames){


            XYPlot plot = new XYPlot();
            plot.setDomainAxis(new DateAxis("Years"));
            for (int i = 0; i < barSeriesCollection.size(); i++){
                XYBarRenderer renderer = new XYBarRenderer();
                plot.setDataset(i,barSeriesCollection.get(i));
                plot.setRenderer(i,renderer);
                plot.mapDatasetToRangeAxis(i,i);
                plot.setRangeAxis(i, new NumberAxis(seriesNames[i]));
            }

            JFreeChart barChart = new JFreeChart(analysisNames[method], new Font("Serif", Font.BOLD, 18), plot, true);
            addToUI(barChart);

        }
        public void createTimeSeries(int method,String[] seriesNames) {

            XYPlot plot = new XYPlot();

            plot.setDomainAxis(new DateAxis("Years"));
            plot.setRangeAxis(new NumberAxis(""));


            for (int i = 0; i < seriesNames.length; i++){
                plot.setDataset(i,timeSeriesCollection.get(i));
                XYSplineRenderer splinerenderer = new XYSplineRenderer();
                plot.setRenderer(i,splinerenderer);
                NumberAxis current_axis = new NumberAxis(seriesNames[i]);
                current_axis.setAutoRange(true);
                plot.setRangeAxis(i,current_axis);
                plot.mapDatasetToRangeAxis(i,i);

            }

            JFreeChart chart = new JFreeChart(root.getAnalysisLabels()[method],new Font("Serif", Font.BOLD, 18), plot, true);

            addToUI(chart);

        }
        public void createReport(String finalMessage) {
            JTextArea report = new JTextArea();

            report.setEditable(false);
            report.setText(finalMessage);

            JScrollPane outputScrollPane = new JScrollPane(report);
            outputScrollPane.createVerticalScrollBar();
            outputScrollPane.setPreferredSize(new Dimension(600,400));
            outputScrollPane.setBackground(Color.white);
            outputScrollPane.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            root.getCenter().add(outputScrollPane);

            root.validate();

        }
        private void addToUI(JFreeChart chart){
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

