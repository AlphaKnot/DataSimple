import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StrategyOne {
    public StrategyOne(ProgramUI root, ArrayList<ParsedSeries> series, int method){
        ArrayList<XYSeries> sets = new ArrayList<>();

        // Loop through values, creating an arrayList of defaultCategory Datasets. used to later make 3 series graph.
        for(int i = 0; i<series.size();i++){
            XYSeries xyseries = new XYSeries(series.get(i).seriesIndicator);

            for(int j=0; j<series.get(i).getValues().size();j++){
                // Getting year will need to be managed for the series some how
                xyseries.add(series.get(i).xDelimitation.get(j),series.get(i).getValues().get(j));
            }
            sets.add(xyseries);
        }
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(sets.get(0));
        dataset.addSeries(sets.get(1));
        dataset.addSeries(sets.get(2));
        JFreeChart chart = ChartFactory.createXYLineChart(
                "XY Chart", // Title
                "x-axis", // x-axis Label
                "y-axis", // y-axis Label
                dataset, // Dataset
                PlotOrientation.VERTICAL, // Plot Orientation
                true, // Show Legend
                true, // Use tooltips
                false // Configure chart to generate URLs?
        );


        ChartPanel chartPanel = new ChartPanel(chart);
        //chartPanel.setPreferredSize(new Dimension(100, 100));
        chartPanel.setBackground(Color.white);
        root.add(chartPanel);
       
        System.out.println("chart made");




    }
}
