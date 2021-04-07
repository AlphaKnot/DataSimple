import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class DataProcessor {
    // Step one parse data.
    // Step two create a country object
    // Step three get that country object here
    // Step four get that result object back to program UI
    ProgramUI root;
    ArrayList<ParsedSeries> series;
    int method; // This will be the method by which we process. is it a piechart, a barchart? etc.
    Country country; // Country is at this point should be complete for what analysis it will be doing
    public DataProcessor(ProgramUI root, ArrayList<ParsedSeries> series, int method) throws FileNotFoundException {
        // Init from constructor
        // I should really do 2208 its due really soon but NEIT
        this.root = root;
        this.series = series;
        this.method = method;
        /*
        ArrayList<DefaultCategoryDataset> sets = new ArrayList<>();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for(int i = 0; i<series.size();i++){
            dataset = new DefaultCategoryDataset();
            for(int j=0; j<series.get(i).values.size();j++){
                dataset.setValue(series.get(i).values.get(j),series.get(i).seriesIndicator,Integer.toString(2020-j));
            }
            sets.add(dataset);
        }
        root.add()
        Call for all strats.
        */
        try {
        switch(method){
            case 0:

                    StrategyOne st1 = new StrategyOne(root, series, method);


                break;
            case 1:
                //11
                break;
            case 2:
                StrategyThree strategyThree = new StrategyThree(root, series, method);
            case 3:
                StrategyFour strategyFour = new StrategyFour(root, series, method);
                break;
            case 4:
                StrategyFive strategyFive = new StrategyFive(root,series,method);
                break;
            case 5:
                //No
                break;
            case 6:
                StrategySeven strategySeven = new StrategySeven(root,series,method);
                break;
            case 7:
                StrategyEight strategyEight = new StrategyEight(root, series, method);
                break;
            default:
                System.out.println("Mistake code -- 3312");

        }

        }catch (DataProcessorException f){
            JOptionPane.showMessageDialog(root, "Country has no usable data for the selected years. Please select another range of years.");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(root, "Country has no usable data, please select another country or another analysis");
        }

    }

}
