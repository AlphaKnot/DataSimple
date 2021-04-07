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

    public boolean minusButtonClicked=false;
    public Boolean plusButtonClicked=false;
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
    public JButton add_view;
    private JComboBox analysisDropDown;
    private JButton recalculate_button;
    public JComboBox viewDropdown;
    private JPanel center;
    private JPanel north;
    private JPanel south;
    private CountryDatabase countDB = null;
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
        AnalysisLabels = new String[analysisDropDown.getItemCount()];
        for(int i = 0; i<AnalysisLabels.length;i++){
            AnalysisLabels[i] = analysisDropDown.getItemAt(i).toString();
        }

        // Same numbers, different implementation.

        center.setLayout(new FlowLayout());
        setSize(1400,1000);
        setTitle("Country Statistics -- Alpha Knot inc");
        add(rootPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Set length for analysis dropdown
        //analysisDropDown.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        countDB = new CountryDatabase("country_list");
        //setting sizes for comboboxes
        add_view.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(rootPanel,"Please Analyze a country before adding a viewer!");
            }
        });
        countryDropdown.addActionListener(new countryDropdownClicked(this, countryDropdown, yearStartDropdown, yearEndDropDown,countDB));
        recalculate_button.addActionListener(new recalculateButtonClicked(this,countryDropdown,yearStartDropdown,yearEndDropDown,analysisDropDown,countDB,viewDropdown));
        remove_view.addActionListener(new removeViewOnClick(this));
        // TODO : Add graphs , add hover listener event to them

    }

    public JPanel getCenter() {
        return center;
    }
    public void refreshCenter(){
         center.removeAll();
         center.setLayout(new FlowLayout());
    }

    public String[] getAnalysisLabels() {
        return AnalysisLabels;
    }

    public String[][] getIndicators() {
        return indicators;
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
        JComboBox viewDropdown;
        CountryDatabase cd;
        ProgramUI main;
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
            // We parse that to populate the "values" array within coutries.
            System.out.println("Analyzing");

            int position = analysisDropdown.getSelectedIndex();

            DataParser dp = new DataParser(main.indicators[position],  country.countryCode, (String) Integer.toString(country.years.getStart()+main.yearStartDropdown.getSelectedIndex()),(String) Integer.toString(country.years.getStart()+main.yearEndDropDown.getSelectedIndex()));

            // NEXT : Hook to dataprocessor
            // series, jframe,
            try {
                DataProcessor dataProcessor = new DataProcessor(main, dp.getSeries(), position);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }

        }
    }

// Action listener , for when the removeView button is clicked ( - / minus button )
    static class removeViewOnClick implements ActionListener{
        ProgramUI root;
        public removeViewOnClick(ProgramUI root){
            this.root = root;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            // This call to method will call the necessary action to calculate the graph required, possibly use jenessa's code? ~ marz
            root.minusButtonClicked = true;
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