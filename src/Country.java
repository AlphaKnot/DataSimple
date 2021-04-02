import java.util.ArrayList;

/***
 * Choosing the following indicators
 * EN.ATM.CO2E.PC vs EG.USE.PCAP.KG.OE vs EN.ATM.PM25.MC.M3 | 3 series
 * EN.ATM.PM25.MC.M3 vs AG.LND.FRST.ZS | two series
 * EN.ATM.CO2E.PC vs NY.GDP.PCAP.CD (2 series graph)
 * AG.LND.FRST.ZS | Per all year range (average) (pie chart, or whatever)
 * SE.XPD.TOTL.GD.ZS | Per all year range (average) -- 1 graph
 * SH.MED.BEDS.ZS vs SE.XPD.TOTL.GD.ZS (per 1000 people -- note calculations) | 2 series graph
 * SH.XPD.CHEX.GD.ZS * NY.GDP.PCAP.CD ( USD ) vs  	SP.DYN.IMRT.IN  | 2 series graph
 * SE.XPD.TOTL.GD.ZS vs SH.XPD.CHEX.GD.ZS
 */
public class Country {
    // Contains country name, country values, and country years.
    ArrayList<ArrayList<Float>> value = new ArrayList<>();
    ArrayList<String> indicators = new ArrayList<>();
    Year years;
    String countryName;
    String countryCode;

    /***
     * Parameter to init country values (NOT value, set after query)
     * @param countryName
     * @param countryCode
     * @param startYear
     * @param endYear
     */
    public Country(String countryName, String countryCode, int startYear, int endYear){
        this.years = new Year(startYear,endYear);
        this.countryName = countryName;
        this.countryCode = countryCode;

    }
    // Overloaded constructor, containing indicator
    public Country(String countryName, String countryCode, String indicator, int startYear, int endYear){

    }

    @Override
    public String toString() {
       return countryName;
    }
    // How would we retrieve the parsed info? Data parser call!

}
