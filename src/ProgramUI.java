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
    private JComboBox view_type_dropdown;
    private JTextPane Country_Synopsis;
    private JPanel viewer3;
    private JPanel viewer1;
    private JPanel viewer2;
    private JLabel viewer1_label;
    private JLabel viewer3_label;
    private JLabel viewer2_label;
    private JComboBox analysisDropDown;
    private JButton recalculate_button;
    private JPanel viewer4;
    private JLabel viewerLabel4;
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

    public ProgramUI(){
        setSize(900,700);
        setTitle("Country Statistics -- Alpha Knot inc");
        add(rootPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        countryDropdown.addActionListener(new countryDropdownClicked(this, countryDropdown, yearStartDropdown, yearEndDropDown,countDB));
        recalculate_button.addActionListener(new recalculateButtonClicked());
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

                if(cd ==null){
                    try {
                        cd = new CountryDatabase("country_list");
                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    }
                    // iterates through all countries and adds them to drop down menu, refreshes dates and
                    for (int i = 0; i < Objects.requireNonNull(cd).Countrydatabase.size(); i++) {
                        // Add first element (Country) to first drop down
                        this.main.countDB = cd;
                        countryDropdown.addItem(cd.getCountrydatabase().get(i).countryName);

                    }
                    countryDropdown.removeItem(countryDropdown.getSelectedItem());
                }
            }

            // In this case a country has been selected , populate both year start and year end with val
            else{
                // Remove all previous years
                yearEndDropdown.removeAllItems();
                yearStartDropdown.removeAllItems();
                // Does this item have country properties
                // Use this to get country Object
                Country selected = cd.getCountrydatabase().get(countryDropdown.getSelectedIndex());
                /**
                System.out.println("Start year of selected country: "+ selected.years.getStart());
                System.out.println("End year of selected country: "+selected.years.getEnd());
                For diagnostics
                 **/
                for(int i = selected.years.getStart() ; i<selected.years.getEnd(); i++){
                    yearEndDropdown.addItem((Integer) i);
                    yearStartDropdown.addItem((Integer) i);
                }
                /***
                 * Small thing here where you get the last year and update the EndYear button to have the last year
                 */
                yearEndDropdown.setSelectedIndex(selected.years.getEnd()-selected.years.getStart()-1);
            }
        }
    }

// Action listener for when recalculate is clicked
    static class recalculateButtonClicked implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            // This call to method will call the necessary action to calculate the graph required, possibly use jenessa's code? ~ marz
            System.out.println("Recalculating!");

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
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        ProgramUI GUI = new ProgramUI();
        GUI.setVisible(true);



    }

}