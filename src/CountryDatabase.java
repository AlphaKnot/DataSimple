// imports the required libraries for this class
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/***
 * This class takes in a file, parses its country code and country name, addes them to an arrayList of an array of size 2 and uses them where need be.
 * @author Amaar Hussein
 */
public class CountryDatabase {
    ArrayList<Country> Countrydatabase = new ArrayList<>();
    String[] country = new String[4];
    public CountryDatabase(String countrycsvpath) throws FileNotFoundException {
        // Country path must NOT include extension.csv
        File file = new File(countrycsvpath +".csv");
        
        // opens the file and reads it
        Scanner myReader = new Scanner(file);
        myReader.nextLine();
        while(myReader.hasNextLine()){
            String line = myReader.nextLine();
            String[] uncleanedCountry = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            country[0] = uncleanedCountry[1]; // Country name
            country[1] = uncleanedCountry[4]; // Country code
            if(!uncleanedCountry[6].equals("N/A")) {country[2] = uncleanedCountry[6];}// Year start
            else{
                country[2] = "-1"; // Place holder
            }
            if(uncleanedCountry[7].equals("Now")){
                country[3] = "2021"; // Change needed for queries through API
            }
            else if(uncleanedCountry[7].equals("N/A")){
                country[3] = "-1"; // Placeholder
            }
            else{
                country[3] = uncleanedCountry[7]; // Not a character , change to literal
            }
            System.out.println(Arrays.toString(country));
            // Some notes to the file in question ;
            // Country Code,"Country Name, Full ","Country Name, Abbreviation",Country Comments,ISO2-digit Alpha,ISO3-digit Alpha,Start Valid Year,End Valid Year
            // ^ Format of CSV
            // Positions needed [1],[3][5][6] (Check if 7 is "Now" and change to 2021)
            Country country_ = new Country(country[0],country[1],Integer.parseInt(country[2]), Integer.parseInt(country[3]));
            Countrydatabase.add(country_); // Country database set
        }
    }

    /***
     * the getter method for the country database
     * @return the array list of countries
     */
    public ArrayList<Country> getCountrydatabase() {
        return Countrydatabase;
    }
}

