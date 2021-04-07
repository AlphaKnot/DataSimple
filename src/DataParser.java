
import java.io.IOException;

import java.net.HttpURLConnection;

import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import com.google.gson.JsonArray;

import com.google.gson.JsonParser;

import javax.swing.*;

/***
 * This class should be constantly reinitalized through use of the *new* keyword (ie regularly create these class) to retrieve parsed data for any
 * Particular query, note that these must be stored somewhere for possible future use but this may impede memory -- it should however be noted that indicators will
 * be required for proper usage of this class, and its advised that we store
 *
 * @author Ammar Hussein
 */

public class DataParser {
    // Repurposed as a constructor, take in the following parameters,
    /*
    Indicator -- String
    year start
    year end
     */
    /***
     * Class attributes,
     * Values parsed from API are stored in arrayList,
     * the indicator to which the API was called is stored in a string called indicator
     * the start and end years and stored in two integer values.
     */
    ArrayList<Float> values = new ArrayList<>();
    ArrayList<ParsedSeries> series = new ArrayList<>();
    float cum_value = 0;
    float cum_avg = 0;
    String[] indicator; // These will be used as series or a means to create another series
    String yearStart;
    String yearEnd;
    String country_code;

    public DataParser(String[] indicator, String country_code, String yearStart, String yearEnd) {
        // Formats url with parameters of constructor
        // Setting attributes;
        this.indicator = indicator;
        this.country_code = country_code;
        this.yearStart = yearStart;
        this.yearEnd = yearEnd;
        for (int j = 0; j < indicator.length; j++) {
            // Reinit if needed
            ArrayList<Float> values = new ArrayList<>();
            ArrayList<Integer> valid_years = new ArrayList<>();
            cum_avg = 0;
            // String URL generated from attributes
            String urlString = String.format("http://api.worldbank.org/v2/country/" + country_code + "/indicator/" + indicator[j] + "?date=" + yearStart + ":" + yearEnd + "&format=json", "can");

            System.out.println(urlString);
            System.out.println(urlString);
            // Since the example code uses population , we generalize it for all types of indicators.
            float valueForYear = 0;

            float cumulativeValue = 0;

            try {

                URL url = new URL(urlString);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("GET");

                conn.connect();

                int responsecode = conn.getResponseCode();

                // IF THE RESPONSE IS 200 OK GET THE LINE WITH THE RESULTS

                if (responsecode == 200) {

                    String inline = "";

                    Scanner sc = new Scanner(url.openStream());

                    while (sc.hasNext()) {

                        inline += sc.nextLine();

                    }

                    sc.close();

                    // PROCESS THE JSON AS ONE LINE

                    JsonArray jsonArray = new JsonParser().parse(inline).getAsJsonArray();

                    int size = jsonArray.size();
                    if (size > 1 && !jsonArray.isJsonNull()) {
                        int sizeOfResults = jsonArray.get(1).getAsJsonArray().size();

                        int year = 0;

                        for (int i = 0; i < sizeOfResults; i++) {

                            // GET FOR EACH ENTRY THE YEAR FROM THE “date” FIELD

                            year = jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("date").getAsInt();

                            // CHECK IF THERE IS A VALUE FOR THE POPULATION FOR A

                            //  GIVEN YEAR

                            if (jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").isJsonNull()) {
                               // Null point found, skipping value
                                continue;
                            } else {

                                // GET THE POPULATION FOR THE GIVEN YEAR FROM THE

                                // “value” FIELD
                                valid_years.add(year);
                                valueForYear = jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").getAsFloat();

                                System.out.println(indicator[j] + " value for " + year + " is " + valueForYear); // Debugging print line;
                                // Add value to arrayList
                                values.add(valueForYear);

                                cumulativeValue += valueForYear;
                            }
                        }

                        System.out.println("The average population over the selected years is " + cumulativeValue / sizeOfResults);
                        cum_value = cumulativeValue;
                        cum_avg = cum_value / sizeOfResults;

                    } else {

                        JOptionPane.showMessageDialog(new JFrame(),"The country you are trying to analyze is not available");
                    }


                    System.out.println("Data successfully parsed in var values");
                    ParsedSeries p = new ParsedSeries(indicator[j], values, valid_years, cum_avg);

                    // Adding series here
                    series.add(p);
                }


            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }
    /***
     * Getter functions for the class attributes.
     * @return ArrayList<Float>
     */
    public ArrayList<Float> getValues() {
        return values;
    }



    /***
     * get Cumulative average
     * @return float
     */
    public float getCum_avg() {
        return cum_avg;
    }

    /***
     * get cumulative value
     * @return float
     */
    public float getCum_value() {
        return cum_value;
    }

    /***
     * get YearEnd Value
     * @return Integer
     */
    public String getYearEnd() {
        return yearEnd;
    }

    /***
     * returns YearStart value
     * @return
     */
    public String getYearStart() {
        return yearStart;
    }

    /***
     * gets indicator
     * @return String
     */
    public String[] getIndicator() {
        return indicator;
    }

    /***
    * gets the series
    * @return an array of the parsed series
    */
    public ArrayList<ParsedSeries> getSeries() {
        return series;
    }
}

