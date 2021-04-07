import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

public class StrategyTwo {
    Analysis strategyTwo;
    String[] analysisNames;
    String[] seriesName;
    ProgramUI root;
    ArrayList<TimeSeries> timeSeriesDatasets;
    ArrayList<TimeSeries> scatterSeriesDatasets;
    DefaultCategoryDataset barSeries;
    ArrayList<XYSeries> XYSeriesSets;
    Vector<String> viewDropdownList;
    Boolean[] GraphAlreadySet;
    public StrategyTwo(ProgramUI root, ArrayList<ParsedSeries> series, int method){

        seriesName = new String[]{
                "(micrograms per cubic meter)",
                "(% of land area)",
        };
        this.root = root;

        analysisNames = root.getAnalysisLabels();

        ArrayList<TimeSeriesCollection> timeSeriesList = new ArrayList<>();
        ArrayList<TimeSeriesCollection> scatterSeriesList = new ArrayList<>();
        ArrayList<TimeSeriesCollection> xySeriesList = new ArrayList<>();
        ArrayList<TimeSeriesCollection> barSeriesList = new ArrayList<>();




        for (int i = 0; i < series.size(); i++) {

            TimeSeriesCollection timeSeriesCollection = new TimeSeriesCollection();
            TimeSeriesCollection scatterSeriesCollection = new TimeSeriesCollection();
            TimeSeriesCollection xySeriesCollection = new TimeSeriesCollection();
            TimeSeriesCollection barSeriesCollection = new TimeSeriesCollection();

            TimeSeries xyseries = new TimeSeries(seriesName[i]);
            TimeSeries scatterseries = new TimeSeries(seriesName[i]);
            TimeSeries timeseries = new TimeSeries(seriesName[i]);
            TimeSeries barseries = new TimeSeries(seriesName[i]);


            for (int j = 0; j < series.get(i).getValues().size(); j++) {
                xyseries.add(new Year(series.get(i).xDelimitation.get(j)),series.get(i).getValues().get(j));
                scatterseries.add(new Year(series.get(i).xDelimitation.get(j)),series.get(i).getValues().get(j));
                barseries.add(new Year(series.get(i).xDelimitation.get(j)),series.get(i).getValues().get(j));
                timeseries.add(new Year(series.get(i).xDelimitation.get(j)), series.get(i).getValues().get(j));
            }

            xySeriesCollection.addSeries(xyseries);
            scatterSeriesCollection.addSeries(scatterseries);
            timeSeriesCollection.addSeries(timeseries);
            barSeriesCollection.addSeries(barseries);

            xySeriesList.add(xySeriesCollection);
            scatterSeriesList.add(scatterSeriesCollection);
            timeSeriesList.add(timeSeriesCollection);
            barSeriesList.add(barSeriesCollection);



        }
        /*
        Create a vector to populate the potential options for your strategy.
         */
        viewDropdownList = new Vector<>();
        viewDropdownList.add("Line Chart");
        viewDropdownList.add("Scatter Plot");
        viewDropdownList.add("Bar Chart");
        viewDropdownList.add("Time Series");
        viewDropdownList.add("Report");
        GraphAlreadySet = new Boolean[viewDropdownList.size()];
        for(int i = 0; i<viewDropdownList.size(); i++){
            root.viewDropdown.addItem(viewDropdownList.get(i));
            GraphAlreadySet[i] = false;
        }
        strategyTwo = new Analysis(analysisNames,root,timeSeriesList,scatterSeriesList,barSeriesList,xySeriesList);
        strategyTwo.CreateLineChart(method,seriesName);
        // Update the "GraphAlreadySet Variable"
        strategyTwo.createScatter(method,seriesName);
        strategyTwo.createBar(method,seriesName);
        strategyTwo.createTimeSeries(method,seriesName);
        GraphAlreadySet[0] = true;
        GraphAlreadySet[1] = true;
        GraphAlreadySet[2] = true;
        GraphAlreadySet[3] = true;
        root.add_view.removeActionListener(root.add_view.getActionListeners()[0]);
        root.add_view.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("add view clicked");
                if(root.viewDropdown.getItemCount()==0){
                    JOptionPane.showMessageDialog(root,"You have not analyzed  a country yet, please do so before adding any viewers");
                }
                else{
                    if(root.viewDropdown.getSelectedItem().equals(viewDropdownList.get(0)) && !GraphAlreadySet[0]){
                        strategyTwo.CreateLineChart(method,seriesName);
                    }
                    else if(root.viewDropdown.getSelectedItem().equals(viewDropdownList.get(1)) && !GraphAlreadySet[1]){
                        strategyTwo.createScatter(method,seriesName);
                    }
                    else if(root.viewDropdown.getSelectedItem().equals(viewDropdownList.get(2)) && !GraphAlreadySet[2]){
                        strategyTwo.createBar(method,seriesName);
                    }
                    else if(root.viewDropdown.getSelectedItem().equals(viewDropdownList.get(3)) && !GraphAlreadySet[3]){
                        strategyTwo.createTimeSeries(method,seriesName);
                    }
                    else if(root.viewDropdown.getSelectedItem().equals(viewDropdownList.get(4)) && !GraphAlreadySet[4]){
                        strategyTwo.createReport("poop");
                    }
                    else{
                        JOptionPane.showMessageDialog(root,"Graph already displayed!");
                    }

                }
            }
        });
    }

    }

