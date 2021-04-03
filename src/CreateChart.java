import org.jfree.chart.*;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class CreateChart extends ProgramUI{

    // What I want as a constructor is a series of values and their associated country
    // From the images on OWL we can see three options.
    // 1. One graph , one dataset
    // 2. Two graphs, two datasets -- ( one country )
    // 3. Three graphs, three datasets -- ( one country )

// Constructor takes in 3 datasets, and 3 names.
    public CreateChart(String dataset_1 , ArrayList<Float> value_1, String dataset_2, ArrayList<Float> value_2, String dataset_3, ArrayList<Float> value_3) throws FileNotFoundException {
        super();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    }
}
