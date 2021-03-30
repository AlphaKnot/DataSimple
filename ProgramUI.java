import javax.swing.*;

public class ProgramUI extends JFrame{
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
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

    public ProgramUI(){
        setSize(900,700);
        setTitle("Country Statistics -- Alpha Knot inc");
        add(rootPanel);
        Country_Synopsis.setText("Country Synopsis -- Nothing here yet");

    }
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        ProgramUI GUI = new ProgramUI();
        GUI.setVisible(true);

    }
}