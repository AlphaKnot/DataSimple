// importing the required libraries for this class
import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/***
 * this class processes the data into multiple strategies depending on the analysis type
 * @author Jenessa Lu🤡, Amaar Hussein🍩, Ishaan Kumar🍆, Nathan Halsey🐵
 */
public class DataProcessor {
    // Step one parse data.
    // Step two create a country object
    // Step three get that country object here
    // Step four get that result object back to program UI
    ProgramUI root;
    StrategyOne st1;
    StrategyThree strategyThree;
    StrategyFour strategyFour;
    StrategyFive strategyFive;
    StrategySeven strategySeven;
    StrategyEight strategyEight;
    StrategyTwo strategyTwo;
    StrategySix strategySix;
    ArrayList<ParsedSeries> series;
    int method; // This will be the method by which we process.
    Country country; // Country is at this point should be complete for what analysis it will be doing

    /***
     * the constructor for data processor
     * @param root the program ui
     * @param series the data set from the parsed series
     * @param method the index of the method
     * @throws FileNotFoundException if the file is not found
     */
    public DataProcessor(ProgramUI root, ArrayList<ParsedSeries> series, int method) throws FileNotFoundException {
        // calling the parameters
        this.root = root;
        this.series = series;
        this.method = method;

        // tries to call the different strategies depending on the index of the strategy
        try {
            switch (method) {
                case 0:
                    st1 = new StrategyOne(root, series, method);
                    break;
                case 1:
                    strategyTwo = new StrategyTwo(root, series, method);
                    break;
                case 2:
                    strategyThree = new StrategyThree(root, series, method);
                    break;
                case 3:
                    strategyFour = new StrategyFour(root, series, method);
                    break;
                case 4:
                    strategyFive = new StrategyFive(root, series, method);
                    break;
                case 5:
                    strategySix = new StrategySix(root, series, method);
                    break;
                case 6:
                    strategySeven = new StrategySeven(root, series, method);
                    break;
                case 7:
                    strategyEight = new StrategyEight(root, series, method);
                    break;
                default:
                    JOptionPane.showMessageDialog(root, "Analysis method not valid, please select another");
                    break;
            }
        }
        catch (DataProcessorException f){
            JOptionPane.showMessageDialog(root, "Country has no usable data for the selected year range.");
        }
        // if the country has no usable data
        catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(root, "Country has no usable data, please select another country or another analysis");
        }
    }
}
