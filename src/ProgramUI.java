import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.util.Objects;

import org.jfree.chart.*;
import org.jfree.data.category.DefaultCategoryDataset;


public class ProgramUI extends JFrame{

    /**
     * Initalize UI components with class attributes to be manipulated later.
     *
     * TO-DO: Change attribute name for the combo-boxes to be more descript.
     */
    private JComboBox countryDropdown;
    private JComboBox yearStartDropdown;
    private JComboBox yearEndDropDown;
    private JPanel rootPanel;
    private JButton remove_view;
    private JButton add_view;
    private JComboBox analysisDropDown;
    private JButton recalculate_button;
    private JComboBox viewDropdown;
    private JPanel center;
    private CountryDatabase countDB = null;

    /***
     * Constructor does the following :
     * Set size of the window, title , adds the JPanel created in the ProgramUI.form file
     * Set texts for default CountrySynopsis box
     * Sets the default function for the close operation
     * adds listeners to :
     *  countryDropDown
     *  recalculate
     *  add_view
     *  remove_view
     */

    public ProgramUI() throws FileNotFoundException {
        setSize(1400,1000);
        setTitle("Country Statistics -- Alpha Knot inc");
        add(rootPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Set length for analysis dropdown
        //analysisDropDown.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        countDB = new CountryDatabase("country_list");
        //setting sizes for comboboxes
        countryDropdown.addActionListener(new countryDropdownClicked(this, countryDropdown, yearStartDropdown, yearEndDropDown,countDB));
        recalculate_button.addActionListener(new recalculateButtonClicked(this,countryDropdown,yearStartDropdown,yearEndDropDown,analysisDropDown,countDB));
        add_view.addActionListener(new addViewOnClick());
        remove_view.addActionListener(new removeViewOnClick());
        // TODO : Add graphs , add hover listener event to them

    }

// We will need multiple actionListeners for each task, I'll try renaming each class to be indicative of what needs to be done ~ marz
    static class countryDropdownClicked implements ActionListener{
        JComboBox countryDropdown;
        JComboBox yearStartDropdown;
        JComboBox yearEndDropdown;
        CountryDatabase cd;
        ProgramUI main;
        public countryDropdownClicked(ProgramUI main, JComboBox countryDropdown, JComboBox yearStartDropdown, JComboBox yearEndDropdown, CountryDatabase cd){
            this.countryDropdown = countryDropdown;
            this.yearStartDropdown = yearStartDropdown;
            this.yearEndDropdown = yearEndDropdown;
            this.cd = cd;
            this.main = main;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            // Executes only the first time the action is performed
            if (this.countryDropdown.getItemCount() == 1) {
                // This call to method will refresh years, possibly use jenessa's code? ~ marz
                System.out.println("Refreshing country lists");


                    // iterates through all countries and adds them to drop down menu, refreshes dates and
                    for (int i = 0; i < Objects.requireNonNull(cd).Countrydatabase.size(); i++) {
                        // Add first element (Country) to first drop down
                        this.main.countDB = cd;
                        countryDropdown.addItem(cd.getCountrydatabase().get(i).countryName);

                    }
                    countryDropdown.removeItem(countryDropdown.getSelectedItem());
                    //Sets max length of dropdown
                    countryDropdown.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX");

            }


            // In this case a country has been selected , populate both year start and year end with val
            else{
                // Remove all previous years
                yearEndDropdown.removeAllItems();
                yearStartDropdown.removeAllItems();
                // Does this item have country properties
                // Use this to get country Object
                Country selected = cd.getCountrydatabase().get(countryDropdown.getSelectedIndex());
                /*
                 System.out.println("Start year of selected country: "+ selected.years.getStart());
                 System.out.println("End year of selected country: "+selected.years.getEnd());
                For diagnostics
                 */

                for(int i = selected.years.getStart() ; i<selected.years.getEnd(); i++){
                    yearEndDropdown.addItem((Integer) i);
                    yearStartDropdown.addItem((Integer) i);
                }
                /*
                 * Small thing here where you get the last year and update the EndYear button to have the last year
                 */
                yearEndDropdown.setSelectedIndex(selected.years.getEnd()-selected.years.getStart()-1);
            }
        }
    }

// Action listener for when recalculate is clicked
    static class recalculateButtonClicked implements ActionListener{
        JComboBox countryDropdown;
        JComboBox yearStartDropdown;
        JComboBox yearEndDropdown;
        JComboBox analysisDropdown;
        CountryDatabase cd;
        ProgramUI main;
    public recalculateButtonClicked(ProgramUI main, JComboBox countryDropdown, JComboBox yearStartDropdown, JComboBox yearEndDropdown, JComboBox analysisDropdown,CountryDatabase cd) {
        this.countryDropdown = countryDropdown;
        this.yearStartDropdown = yearStartDropdown;
        this.yearEndDropdown = yearEndDropdown;
        this.analysisDropdown = analysisDropdown;
        this.cd = cd;
        this.main = main;
        // Required , year end year start, Country name for query
        // Country code for parsing
        // Create -> JFreeChart Object.
        // Uses constructor to take in these parameters.
    }
        @Override
        public void actionPerformed(ActionEvent e) {
            // This call to method will call the necessary action to calculate the graph required, possibly use jenessa's code? ~ marz
            System.out.println("Recalculating!");
            // This gives us the country selected.
            Country country = cd.getCountrydatabase().get(countryDropdown.getSelectedIndex());
            // We parse that to populate the "values" array within coutries.
            System.out.println("Analyzing");
            //TODO: Indicator from analysisdropdown needed for parsing, possible unhardcode

            // FRICK I HATE HARD-CODING THIS SOLUTION UGH SOMONE END THIS SHIT ALREADY IM MAD
            int position = analysisDropdown.getSelectedIndex();
            String[] indicators = new String[0];
            // There are 8 options here:
            if(position == 0){
                indicators = new String[]{"EN.ATM.CO2E.PC", "EG.USE.PCAP.KG.OE", "EN.ATM.PM25.MC.M3"};
            }
            else if(position ==1){
                indicators = new String[]{"EN.ATM.PM25.MC.M3", "AG.LND.FRST.ZS"};
            }
            else if(position == 2){
                indicators = new String[]{"EN.ATM.CO2E.PC", "NY.GDP.PCAP.CD"};
            }
            else if(position ==3){
                indicators = new String[]{"AG.LND.FRST.ZS"};
            }
            else if(position == 4){
                indicators = new String[]{"SE.XPD.TOTL.GD.ZS"};
            }
            else if(position ==5){
                indicators = new String[]{"SH.MED.BEDS.ZS", "SE.XPD.TOTL.GD.ZS"};
            }
            else if(position == 6){
                indicators = new String[]{"SH.XPD.CHEX.GD.ZS","NY.GDP.PCAP.CD", "SP.DYN.IMRT.IN"};
            }
            else if(position ==7){
                indicators = new String[]{"SE.XPD.TOTL.GD.ZS", "SH.XPD.CHEX.GD.ZS"};
            }


            DataParser dp = new DataParser(indicators,  country.countryCode, Integer.toString(country.years.getStart()),  Integer.toString(country.years.getEnd()));

            // NEXT : Hook to dataprocessor
            // series, jframe,
            DataProcessor dataProcessor = new DataProcessor(main.center, dp.getSeries(), position);

        }
    }
    // Action listener , for when the addView button is clicked ( + / plus button )
    static class addViewOnClick implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            // This call to method will call the necessary action to calculate the graph required, possibly use jenessa's code? ~ marz
            System.out.println("Add mode on");

        }
    }
// Action listener , for when the removeView button is clicked ( - / minus button )
    static class removeViewOnClick implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            // This call to method will call the necessary action to calculate the graph required, possibly use jenessa's code? ~ marz
            System.out.println("Remove mode on");

        }
    }
    static class ModifyViewerHover extends MouseAdapter{
        JPanel Viewer;
        public ModifyViewerHover(JPanel Viewer){
            this.Viewer = Viewer;
        }
        @Override
        public void mouseEntered(MouseEvent e) {
            Viewer.setBorder(BorderFactory.createRaisedBevelBorder());
            super.mouseEntered(e);
        }
        public void mouseExited(MouseEvent e){
            Viewer.setBorder(BorderFactory.createEmptyBorder());
            super.mouseExited(e);
        }
    }


// Main method, calls the window
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, FileNotFoundException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        ProgramUI GUI = new ProgramUI();
        GUI.setVisible(true);
    }
}