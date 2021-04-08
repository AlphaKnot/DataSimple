// importing the required libraries for this class
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.util.Objects;

/***
 * this class represents the program interface
 * @author Amaar Hussein
 */
public class ProgramUI extends JFrame{

    public boolean minusButtonClicked=false;
    public Boolean plusButtonClicked=false;

    // initializing all the panels, dropdowns and buttons
    private JComboBox countryDropdown;
    private JComboBox yearStartDropdown;
    private JComboBox yearEndDropDown;
    private JPanel rootPanel;
    private JButton remove_view;
    public JButton add_view;
    private JComboBox analysisDropDown;
    private JButton recalculate_button;
    public JComboBox viewDropdown;
    private JPanel center;
    private JPanel north;
    private JPanel south;
    private CountryDatabase countDB = null;

    // the array of indicators and analysis labels
    private String[][] indicators;
    private String[] AnalysisLabels;

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
        indicators = new String[][]{
                {"EN.ATM.CO2E.PC", "EG.USE.PCAP.KG.OE", "EN.ATM.PM25.MC.M3"},
                {"EN.ATM.PM25.MC.M3", "AG.LND.FRST.ZS"},
                {"EN.ATM.CO2E.PC", "NY.GDP.PCAP.CD"},
                {"AG.LND.FRST.ZS"},
                {"SE.XPD.TOTL.GD.ZS"},
                {"SH.MED.BEDS.ZS", "SE.XPD.TOTL.GD.ZS"},
                {"SH.XPD.CHEX.GD.ZS","NY.GDP.PCAP.CD", "SP.DYN.IMRT.IN"},
                {"SE.XPD.TOTL.GD.ZS", "SH.XPD.CHEX.GD.ZS"},

        };
        // getting the analysis labels
        AnalysisLabels = new String[analysisDropDown.getItemCount()];
        for(int i = 0; i<AnalysisLabels.length;i++){
            AnalysisLabels[i] = analysisDropDown.getItemAt(i).toString();
        }


        // the program layout
        center.setLayout(new FlowLayout());
        setSize(1400,1000);
        setTitle("DatSimp -- Alpha Knot inc");
        add(rootPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // getting the country database
        countDB = new CountryDatabase("country_list");
        //setting sizes for comboboxes
        add_view.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(rootPanel,"Please Analyze a country before adding a viewer!");
            }
        });
        // adding action listeners
        countryDropdown.addActionListener(new countryDropdownClicked(this, countryDropdown, yearStartDropdown, yearEndDropDown,countDB));
        recalculate_button.addActionListener(new recalculateButtonClicked(this,countryDropdown,yearStartDropdown,yearEndDropDown,analysisDropDown,countDB,viewDropdown));
        remove_view.addActionListener(new removeViewOnClick(this));


    }

    /***
     * the getter method for the center panel
     * @return center the center panel
     */
    public JPanel getCenter() {
        return center;
    }

    /***
     * the method that refreshes the center panel when another selection is picked
     */
    public void refreshCenter(){
        center.removeAll();
        center.setLayout(new FlowLayout());
    }

    /***
     * the getter method for the analysis labels
     * @return AnalysisLabels the analysis labels
     */
    public String[] getAnalysisLabels() {
        return AnalysisLabels;
    }

    /***
     * the getter method for indicators
     * @return indicators the World Bank Indicators
     */
    public String[][] getIndicators() {
        return indicators;
    }

    /***
     * the event listener if the country dropdown was clicked
     */
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

        /***
         * overrides the action performed method and refreshes the list
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            // Executes only the first time the action is performed
            if (this.countryDropdown.getItemCount() == 1) {
                // This call to method will refresh years
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

                // Use this to get country Object
                Country selected = cd.getCountrydatabase().get(countryDropdown.getSelectedIndex());


                for(int i = selected.years.getStart() ; i<selected.years.getEnd(); i++){
                    yearEndDropdown.addItem((Integer) i);
                    yearStartDropdown.addItem((Integer) i);
                }
                // Small thing here where you get the last year and update the EndYear button to have the last year

                yearEndDropdown.setSelectedIndex(selected.years.getEnd()-selected.years.getStart()-1);
            }
        }
    }

    /***
     * this static class represents the event if the recalculate button is clicked
     */
    static class recalculateButtonClicked implements ActionListener{

        // setting the dropdowns and menus
        JComboBox countryDropdown;
        JComboBox yearStartDropdown;
        JComboBox yearEndDropdown;
        JComboBox analysisDropdown;
        JComboBox viewDropdown;
        CountryDatabase cd;
        ProgramUI main;

        /***
         * the constructor for the event if the recalculate button is clicked
         * @param main the program ui
         * @param countryDropdown the country dropdown menu
         * @param yearStartDropdown the starting year dropdown menu
         * @param yearEndDropdown the ending year dropdown menu
         * @param analysisDropdown the analysis dropdown menu
         * @param cd the country database
         * @param viewDropdown the viewer dropdown
         */
        public recalculateButtonClicked(ProgramUI main, JComboBox countryDropdown, JComboBox yearStartDropdown, JComboBox yearEndDropdown, JComboBox analysisDropdown,CountryDatabase cd, JComboBox viewDropdown) {
            this.countryDropdown = countryDropdown;
            this.yearStartDropdown = yearStartDropdown;
            this.yearEndDropdown = yearEndDropdown;
            this.analysisDropdown = analysisDropdown;
            this.cd = cd;
            this.main = main;
            this.viewDropdown = viewDropdown;
            // Required , year end year start, Country name for query
            // Country code for parsing
            // Create -> JFreeChart Object.
            // Uses constructor to take in these parameters.
        }

        /***
         * gets the user inputs for data processing
         * @param e the recalculate event
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            main.center.removeAll();
            main.refreshCenter();
            main.repaint();
            main.revalidate();
            viewDropdown.removeAllItems();
            // This call to method will call the necessary action to calculate the graph required, possibly use jenessa's code? ~ marz

            System.out.println("Recalculating!");
            // This gives us the country selected.
            Country country = cd.getCountrydatabase().get(countryDropdown.getSelectedIndex());
            // We parse that to populate the "values" array within countries.
            System.out.println("Analyzing");

            int position = analysisDropdown.getSelectedIndex();

            DataParser dp = new DataParser(main.indicators[position],  country.countryCode, (String) Integer.toString(country.years.getStart()+main.yearStartDropdown.getSelectedIndex()),(String) Integer.toString(country.years.getStart()+main.yearEndDropDown.getSelectedIndex()));

            // connects to data processor
            try {
                DataProcessor dataProcessor = new DataProcessor(main, dp.getSeries(), position);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }

        }
    }

    /***
     * the action listener class, for when the removeView button is clicked. (- / minus button)
     */
    static class removeViewOnClick implements ActionListener{
        ProgramUI root;
        public removeViewOnClick(ProgramUI root){
            this.root = root;
        }

        /***
         * the action listener for the remove click button click
         * @param e the remove viewer event
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            // This call to method will call the necessary action to calculate the graph required, possibly use jenessa's code? ~ marz
            root.minusButtonClicked = true;
            System.out.println("Remove mode on");

        }
    }

    /***
     * the class represents the zoom in and zoom out function on the viewer
     */
    static class ModifyViewerHover extends MouseAdapter{
        JPanel Viewer;
        public ModifyViewerHover(JPanel Viewer){
            this.Viewer = Viewer;
        }

        /***
         *  the action for when the mouse clicks on the viewer
         * @param e the event that the mouse click on the viewer
         */
        @Override
        public void mouseEntered(MouseEvent e) {
            Viewer.setBorder(BorderFactory.createRaisedBevelBorder());
            super.mouseEntered(e);
        }

        /***
         * the action for when the mouse clicks off the viewer
         * @param e the event that the mouse clicks off the viewer
         */
        public void mouseExited(MouseEvent e){
            Viewer.setBorder(BorderFactory.createEmptyBorder());
            super.mouseExited(e);
        }
    }
}
