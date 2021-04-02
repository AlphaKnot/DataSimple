import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private JComboBox yearEnddropDown;
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

        countryDropdown.addActionListener(new countryDropdownClicked());
        recalculate_button.addActionListener(new recalculateButtonClicked());
        add_view.addActionListener(new addViewOnClick());
        remove_view.addActionListener(new removeViewOnClick());
        // TODO : Add graphs , add hover listener event to them

    }

// We will need multiple actionListeners for each task, I'll try renaming each class to be indicative of what needs to be done ~ marz
    static class countryDropdownClicked implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            // This call to method will refresh years, possibly use jenessa's code? ~ marz
            System.out.println("Country dropdown clicked!");


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