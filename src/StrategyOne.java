// importing the libraries required for this class
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

/***
 * this class processes the data required (from parsed series) for strategy 1
 * @author Amaar Hussein
 */
public class StrategyOne{
    // represents the dropdown list
    Vector<String> viewDropdownList;
    // represents the dropdown of analyses
    String[] analysisNames ;
    // represents the name of datasets being processed
    String[] seriesName;
    // the program ui
    ProgramUI root;
    // array checking if the graph at the index is already set on the interface
    Boolean[] GraphAlreadySet;
    // the analysis object representing strategy 1
    Analysis strategyOne;

    /***
     * the constructor for strategy 1
     * @param root the program ui
     * @param series the dataset parsed from ParsedSeries to be used for data processing
     * @param method the index of the analysis on the dropdown menu
     */
    public StrategyOne(ProgramUI root, ArrayList<ParsedSeries> series, int method){
        // the names of the dataset series
        seriesName = new String[]{
                "(metric tons per capita)",
                "(kg of oil equivalent per capita)",
                "(micrograms per cubic meter)",

        };
        // calling the program ui
        this.root = root;
        // getting the analysis labels to the program ui
        analysisNames = root.getAnalysisLabels();

        // creating arraylists to store each dataset given the series' name
        ArrayList<TimeSeriesCollection> timeSeriesList = new ArrayList<>();
        ArrayList<TimeSeriesCollection> scatterSeriesList = new ArrayList<>();
        ArrayList<TimeSeriesCollection> xySeriesList = new ArrayList<>();
        ArrayList<TimeSeriesCollection> barSeriesList = new ArrayList<>();

        // initializing the report message with a title and empty message
        String message = "";
        String title = seriesName[0] +" vs "+seriesName[1]+"\n";
        StringBuilder finalMessage = new StringBuilder();
        finalMessage.append(title+"==========================================\n");

        // looping through the parsed datasets to store information
        for (int i = 0; i < series.size(); i++) {

            // initializing the data collections
            TimeSeriesCollection timeSeriesCollection = new TimeSeriesCollection();
            TimeSeriesCollection scatterSeriesCollection = new TimeSeriesCollection();
            TimeSeriesCollection xySeriesCollection = new TimeSeriesCollection();
            TimeSeriesCollection barSeriesCollection = new TimeSeriesCollection();

            // initializing the series with their appropriate names
            TimeSeries xyseries = new TimeSeries(seriesName[i]);
            TimeSeries scatterseries = new TimeSeries(seriesName[i]);
            TimeSeries timeseries = new TimeSeries(seriesName[i]);
            TimeSeries barseries = new TimeSeries(seriesName[i]);


            for (int j = 0; j < series.get(i).getValues().size(); j++) {
                // adding the datasets to their series
                xyseries.add(new Year(series.get(i).xDelimitation.get(j)),series.get(i).getValues().get(j));
                scatterseries.add(new Year(series.get(i).xDelimitation.get(j)),series.get(i).getValues().get(j));
                barseries.add(new Year(series.get(i).xDelimitation.get(j)),series.get(i).getValues().get(j));
                timeseries.add(new Year(series.get(i).xDelimitation.get(j)), series.get(i).getValues().get(j));

                // creating the report message and adding it to the final report message
                message = seriesName[i] +" had a value in year " + series.get(i).xDelimitation.get(j) + " of "+ series.get(i).getValues().get(j)+"\n";
                finalMessage.append(message);
            }
            // adding the series to their collections
            xySeriesCollection.addSeries(xyseries);
            scatterSeriesCollection.addSeries(scatterseries);
            timeSeriesCollection.addSeries(timeseries);
            barSeriesCollection.addSeries(barseries);

            // adding the collections to their appropriate arraylist
            xySeriesList.add(xySeriesCollection);
            scatterSeriesList.add(scatterSeriesCollection);
            timeSeriesList.add(timeSeriesCollection);
            barSeriesList.add(barSeriesCollection);
        }
        // creates a vector to populate the potential options for the dropdown menu
        viewDropdownList = new Vector<>();

        // adding the options to the vector
        viewDropdownList.add("Line Chart");
        viewDropdownList.add("Scatter Plot");
        viewDropdownList.add("Bar Chart");
        viewDropdownList.add("Time Series");
        viewDropdownList.add("Report");

        // setting the dropdown menu and populating it with options
        GraphAlreadySet = new Boolean[viewDropdownList.size()];
        for(int i = 0; i<viewDropdownList.size(); i++){
            root.viewDropdown.addItem(viewDropdownList.get(i));
            GraphAlreadySet[i] = false;
        }

        // creating the strategy 1 analysis object with the options
        strategyOne = new Analysis(analysisNames,root,timeSeriesList,scatterSeriesList,barSeriesList,xySeriesList);

        // creating the default graphs to display
        strategyOne.CreateLineChart(method,seriesName);
        strategyOne.createScatter(method,seriesName);
        strategyOne.createBar(method,seriesName);
        strategyOne.createTimeSeries(method,seriesName);

        // updates the GraphAlreadySet variable
        GraphAlreadySet[0] = true;
        GraphAlreadySet[1] = true;
        GraphAlreadySet[2] = true;
        GraphAlreadySet[3] = true;

        // getting the action listeners
        root.add_view.removeActionListener(root.add_view.getActionListeners()[0]);
        // listening for the add viewer event
        root.add_view.addActionListener(e -> {
            System.out.println("Add View Clicked");
            // if no country is analyzed, prints error message
            if(root.viewDropdown.getItemCount()==0){
                JOptionPane.showMessageDialog(root,"You have not analyzed a country yet, please do so before adding any viewers");
            }

            // creates the viewers if the event to add the specific viewer occurs
            else{
                if(root.viewDropdown.getSelectedItem().equals(viewDropdownList.get(0)) && !GraphAlreadySet[0]){
                    strategyOne.CreateLineChart(method,seriesName);
                }
                else if(root.viewDropdown.getSelectedItem().equals(viewDropdownList.get(1)) && !GraphAlreadySet[1]){
                    strategyOne.createScatter(method,seriesName);
                }
                else if(root.viewDropdown.getSelectedItem().equals(viewDropdownList.get(2)) && !GraphAlreadySet[2]){
                    strategyOne.createBar(method,seriesName);
                }
                else if(root.viewDropdown.getSelectedItem().equals(viewDropdownList.get(3)) && !GraphAlreadySet[3]){
                    strategyOne.createTimeSeries(method,seriesName);
                }
                else if(root.viewDropdown.getSelectedItem().equals(viewDropdownList.get(4)) && !GraphAlreadySet[4]){
                    strategyOne.createReport(finalMessage.toString());
                }
                // else will not create the graph since it is already displayed
                else{
                    JOptionPane.showMessageDialog(root,"Graph already displayed!");
                }

            }
        });
    }

    /***
     * getter for the analysis object
     * @return the analysis object
     */
    public Analysis getAnalysis() {
        return strategyOne;
    }

    /***
     *  getter for the boolean array variable to check if the graph is already set
     * @return the boolean array graph already set
     */
    public Boolean[] getGraphAlreadySet() {
        return GraphAlreadySet;
    }

}
