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
import org.jfree.chart.util.TableOrder;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.PieDataset;
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

public class StrategyEight {

    String[] analysisNames;

    // Ratio of Government expenditure on education,
    // total (% of GDP) vs Current health expenditure (% of GDP).
    public StrategyEight(ProgramUI root, ArrayList<ParsedSeries> series, int method){
        createPieS8(root, series, method);
        createScatter(root,series,method);

    }

    public void createPieS8(ProgramUI root, ArrayList<ParsedSeries> series, int method){

        String[] seriesName = new String[]{
                "Ratio of Education Expenditure to Total GDP",
                "Ratio of Health Expenditure to Total GDP",
        };

        analysisNames = root.getAnalysisLabels();

        ArrayList<DefaultCategoryDataset> datasets = new ArrayList<>();

        for (int i = 0; i<series.size(); i++) {
            DefaultCategoryDataset pieseries = new DefaultCategoryDataset();

            for (int j = 0; j < series.get(i).getValues().size(); j++) {
                // Getting year will need to be managed for the series some how

                // adds the float value, the spending and the indicator name
                // idk how it will add up to 100 OOOP
                pieseries.addValue(series.get(i).getValues().get(j),"",seriesName[i]);
            }
            datasets.add(pieseries);
        }

        //JFreeChart pieChart = ChartFactory.createMultiplePieChart(analysisNames[method], datasets.get(),
                //TableOrder.BY_COLUMN, true, true, false);

        // i frankly have no idea if this works i fucking hate pie charts
        JFreeChart pieChart = ChartFactory.createMultiplePieChart(analysisNames[method], datasets.get(0), TableOrder.BY_COLUMN, true, true, false);
        JFreeChart pieChart2 = ChartFactory.createMultiplePieChart(analysisNames[method], datasets.get(1), TableOrder.BY_COLUMN, true, true, false);

        ChartPanel chartPanel = new ChartPanel(pieChart);
        ChartPanel chartPanel1 = new ChartPanel(pieChart2);
        chartPanel.setPreferredSize(new Dimension(400, 300));
        chartPanel1.setPreferredSize(new Dimension(400, 300));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel1.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        chartPanel1.setBackground(Color.white);
        //west.add(chartPanel);
        root.getCenter().add(chartPanel);
        root.getCenter().add(chartPanel1);
        root.validate();
    }

    public void createScatter(ProgramUI root, ArrayList<ParsedSeries> series, int method){

    }
}

