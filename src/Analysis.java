import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.time.TimeSeriesCollection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * @author Nathan Halsey
 * @author Amaar Hussein
 * Analysis class creates the charts to be added to the viewer
 */
public class Analysis extends JFrame {
    String[] analysisNames;
    ProgramUI root;
    int method;
    String[] seriesName;
    Viewer myView;
    ArrayList<TimeSeriesCollection> timeSeriesCollection;
    ArrayList<TimeSeriesCollection> scatterSeriesCollection;
    ArrayList<TimeSeriesCollection> barSeriesCollection;
    ArrayList<TimeSeriesCollection> xySeriesCollection;

    /**
     * Constructor, initializes global variables and creates a viewer object
     */
    public Analysis(ProgramUI root, int method, String[] seriesName, String[] analysisNames, String[] viewerTypes, String finalMessage, ArrayList<TimeSeriesCollection> timeSeriesCollection, ArrayList<TimeSeriesCollection> scatterSeriesCollection, ArrayList<TimeSeriesCollection> barSeriesCollection, ArrayList<TimeSeriesCollection> xySeriesCollection) {
        this.root = root;
        this.method = method;
        this.seriesName = seriesName;
        this.analysisNames = analysisNames;
        this.timeSeriesCollection = timeSeriesCollection;
        this.scatterSeriesCollection = scatterSeriesCollection;
        this.barSeriesCollection = barSeriesCollection;
        this.xySeriesCollection = xySeriesCollection;

        myView = myView = new Viewer(root, this, analysisNames, method, finalMessage, seriesName, viewerTypes);

    }

    /***
     * method to create a line chart dynamically given the data
     */
    public void CreateLineChart() {
        XYPlot plot = new XYPlot();

        plot.setOrientation(PlotOrientation.VERTICAL);
        plot.mapDatasetToDomainAxis(0, 0);
        plot.setDomainAxis(new DateAxis("Years"));
        plot.setRangeAxis(new NumberAxis(""));


        for (int i = 0; i < seriesName.length; i++) {
            XYItemRenderer itemrenderer = new XYLineAndShapeRenderer(true, false);
            itemrenderer.setDefaultToolTipGenerator(new StandardXYToolTipGenerator());
            plot.setDataset(i, xySeriesCollection.get(i));
            plot.setRenderer(i, itemrenderer);
            plot.mapDatasetToRangeAxis(i, i);
            plot.setRangeAxis(i, new NumberAxis(seriesName[i]));
            plot.getRangeAxis().setAutoRange(true);
            plot.getDomainAxis().setAutoRange(true);
        }

        JFreeChart chart = new JFreeChart(analysisNames[method], JFreeChart.DEFAULT_TITLE_FONT, plot, true);

        myView.addToUI(chart);


    }

    /***
     * creates a scatter chart
     */
    public void createScatter() {

        XYPlot plot = new XYPlot();


        DateAxis domainAxis = new DateAxis("Years");
        plot.setDomainAxis(domainAxis);
        plot.setRangeAxis(new NumberAxis(""));

        for (int i = 0; i < seriesName.length; i++) {
            plot.setDataset(i, scatterSeriesCollection.get(i));
            XYItemRenderer itemrenderer = new XYLineAndShapeRenderer(false, true);
            plot.setRenderer(i, itemrenderer);
            plot.setRangeAxis(i, new NumberAxis(seriesName[i]));
            plot.mapDatasetToRangeAxis(i, i);
            plot.getRangeAxis().setAutoRange(true);
            plot.getDomainAxis().setAutoRange(true);
        }

        JFreeChart scatterChart = new JFreeChart(analysisNames[method], new Font("Serif", Font.BOLD, 18), plot, true);
        myView.addToUI(scatterChart);

    }

    /***
     *  creates a bar chart
     */
    public void createBar() {
        
        XYPlot plot = new XYPlot();
        plot.setDomainAxis(new DateAxis("Years"));
        for (int i = 0; i < barSeriesCollection.size(); i++) {
            XYBarRenderer renderer = new XYBarRenderer();
            plot.setDataset(i, barSeriesCollection.get(i));
            plot.setRenderer(i, renderer);
            plot.mapDatasetToRangeAxis(i, i);
            plot.setRangeAxis(i, new NumberAxis(seriesName[i]));
            plot.getRangeAxis().setAutoRange(true);
        }

        JFreeChart barChart = new JFreeChart(analysisNames[method], new Font("Serif", Font.BOLD, 18), plot, true);
        myView.addToUI(barChart);

    }

    /***
     * creates a time series
     */
    public void createTimeSeries() {

        XYPlot plot = new XYPlot();

        plot.setDomainAxis(new DateAxis("Years"));
        plot.setRangeAxis(new NumberAxis(""));

        for (int i = 0; i < seriesName.length; i++) {
            plot.setDataset(i, timeSeriesCollection.get(i));
            XYSplineRenderer splinerenderer = new XYSplineRenderer();
            plot.setRenderer(i, splinerenderer);
            NumberAxis current_axis = new NumberAxis(seriesName[i]);
            current_axis.setAutoRange(true);
            plot.setRangeAxis(i, current_axis);
            plot.mapDatasetToRangeAxis(i, i);
            plot.getRangeAxis().setAutoRange(true);
            plot.getDomainAxis().setAutoRange(true);

        }

        JFreeChart chart = new JFreeChart(root.getAnalysisLabels()[method], new Font("Serif", Font.BOLD, 18), plot, true);

        myView.addToUI(chart);

    }

    /***
     * creates a report
     * @param finalMessage the message to be displayed
     */
    public void createReport(String finalMessage) {
        JTextArea report = new JTextArea();

        report.setEditable(false);
        report.setText(finalMessage);
        report.setLineWrap(true);
        report.setWrapStyleWord(true);

        JScrollPane outputScrollPane = new JScrollPane(report);
        outputScrollPane.createVerticalScrollBar();
        outputScrollPane.setPreferredSize(new Dimension(600, 400));
        outputScrollPane.setBackground(Color.white);
        outputScrollPane.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        report.addMouseListener(new removeJScrollPane(root,report, outputScrollPane, myView.viewDropdownList, root.viewDropdown.getSelectedIndex(), myView.GraphAlreadySet));
        root.getCenter().add(outputScrollPane);
        root.validate();

    }

}

