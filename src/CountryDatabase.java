import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// This class takes in a file, parses its country code and country name, addes them to an arrayList of an array of size 2 and uses them where need be.
public class CountryDatabase {
    ArrayList<String[]> Countrydatabase = new ArrayList<>();
    String[] country = new String[2];
    public CountryDatabase(String countrycsvpath) throws FileNotFoundException {
        // Country path must NOT include extension.csv
        File file = new File(countrycsvpath +".csv");
        Scanner myReader = new Scanner(file);
        while(myReader.hasNextLine()){
            String line = myReader.nextLine();
            country = line.split(",");
            // Country of the form Country:CountryCode [0:1]
            Countrydatabase.add(country);
        }

    }

    public ArrayList<String[]> getCountrydatabase() {
        return Countrydatabase;
    }
}

