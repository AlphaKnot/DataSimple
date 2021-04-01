
import java.io.IOException;

import java.net.HttpURLConnection;

import java.net.URL;

import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.JsonArray;

import com.google.gson.JsonParser;

/***
 * This class should be constantly reinitalized through use of the *new* keyword (ie regularly create these class) to retrieve parsed data for any
 * Particular query, note that these must be stored somewhere for possible future use but this may impede memory -- it should however be noted that indicators will
 * be required for proper usage of this class, and its advised that we store
 *
 * ~ AmmarH
 */

public class DataParser {
    // Repurposed as a constructor, take in the following parameters,
    /*
    Indicator -- String // TODO: Figure out how we'll get the indicators
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
    float cum_value = 0;
    float cum_avg = 0;
    String indicator;
    int yearStart;
    int yearEnd;
    String country_code;

    public DataParser(String indicator, String country_code, int yearStart, int yearEnd) {
        // Formats url with parameters of constructor

        // Setting attributes;
        this.indicator = indicator;
        this.country_code = country_code;
        this.yearStart = yearStart;
        this.yearEnd = yearEnd;
        String urlString = String.format("http://api.worldbank.org/v2/"+country_code+"/indicator/"+indicator+"?date="+ yearStart +":"+ yearEnd +"&format=json", "can");

        System.out.println(urlString);
        System.out.println(urlString);
        // Since the example code uses population , we generalize it for all types of indicators.
        int valueForYear = 0;

        int cumulativeValue = 0;

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

                int sizeOfResults = jsonArray.get(1).getAsJsonArray().size();

                int year = 0;

                for (int i = 0; i < sizeOfResults; i++) {

                        // GET FOR EACH ENTRY THE YEAR FROM THE “date” FIELD

                    year = jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("date").getAsInt();

                    // CHECK IF THERE IS A VALUE FOR THE POPULATION FOR A

                    //  GIVEN YEAR

                    if(jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").isJsonNull())

                        valueForYear = 0;

                    else

                        // GET THE POPULATION FOR THE GIVEN YEAR FROM THE

                        // “value” FIELD

                        valueForYear = jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").getAsInt();

                    System.out.println("Population for : " + year + " is " +  valueForYear); // Debugging print line;
                    // Add value to arrayList
                    values.add((float) valueForYear);

                    cumulativeValue += valueForYear;

                }

                System.out.println("The average population over the selected years is " + cumulativeValue / sizeOfResults);
                cum_value = cumulativeValue;
                cum_avg = cum_value/sizeOfResults;

            }

        } catch (IOException e) {

          e.printStackTrace();

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
    public int getYearEnd() {
        return yearEnd;
    }

    /***
     * returns YearStart value
     * @return
     */
    public int getYearStart() {
        return yearStart;
    }

    /***
     * gets indicator
     * @return String
     */
    public String getIndicator() {
        return indicator;
    }
}

