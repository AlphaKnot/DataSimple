import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

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

                     st1 = new StrategyOne(root, series, method);


                break;
            case 1:
                strategyTwo = new StrategyTwo(root,series,method);
                break;
            case 2:
                 strategyThree = new StrategyThree(root, series, method);
                 break;
            case 3:
                 strategyFour = new StrategyFour(root, series, method);
                break;
            case 4:
                 strategyFive = new StrategyFive(root,series,method);
                break;
            case 5:
                // working on that
                break;
            case 6:
                 strategySeven = new StrategySeven(root,series,method);
                break;
            case 7:
                 strategyEight = new StrategyEight(root, series, method);
                break;
            default:
                JOptionPane.showMessageDialog(root, "Analysis method not valid, please select another");
        }
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(root, "Country has no usable data, please select another country or another analysis");
        }

    }

}
