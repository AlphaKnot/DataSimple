// imported the libraries required for this class
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import javax.swing.*;

/***
 * this class represents the login interface of the software
 * @author Jenessa Lu, Nathan Halsey, Ammar Hussein
 */
public class LoginInterface extends JFrame implements ActionListener{

    // creating instance panels and fields
    JPanel panel;
    JLabel user_label, password_label, message;
    JTextField userName_text;
    JPasswordField password_text;
    JButton submit;

    /***
     * the constructor for the login interface
     */
    public LoginInterface() {

        // the username fields
        user_label = new JLabel();
        user_label.setText("User Name :");
        userName_text = new JTextField();

        // the password fields
        password_label = new JLabel();
        password_label.setText("Password :");
        password_text = new JPasswordField();

        // the submit button
        submit = new JButton("SUBMIT");

        // setting the dimensions of the JPanel
        panel = new JPanel(new GridLayout(3, 1));

        // adding the username, password fields to the login panel
        panel.add(user_label);
        panel.add(userName_text);
        panel.add(password_label);
        panel.add(password_text);

        // the message if there's any errors
        message = new JLabel();
        panel.add(message);
        panel.add(submit);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // adding the listeners to components..
        submit.addActionListener(this);
        password_text.addActionListener(this);
        add(panel, BorderLayout.CENTER);
        setTitle("Login -- Alpha Knot Inc.");
        setSize(450, 150);
        setVisible(true);
    }

    /***
     * the main method to run the software program
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        new LoginInterface();
    }

    /***
     * the action listener method
     * @param ae
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        // initializing the credentials database
        CredentialsDatabase database = null;
        try {
            // getting the credentials database
            database = new CredentialsDatabase();
        }
        // catch block for any errors
        catch (Exception e) {
            e.printStackTrace();
            message.setText("Credentials Database failed to sync");
        }
        // getting the inputs from the user
        String userName = userName_text.getText();
        String password = password_text.getText();

        // comparing the user input to the credentials database
        // if the login credentials are valid, it opens the main interface
        if (database.getValue(userName.trim()) != null && database.getValue(userName.trim()).equals(password.trim())){
            ProgramUI mainUI = null;
            // tries to get the main interface
            try {
                mainUI = new ProgramUI();
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
                message.setText("Program Interface failed to sync");
            }
            mainUI.setVisible(true);
           this.setVisible(false);
        }
        // if the login credentials are invalid, sends message that it is invalid
        else{
            message.setText("Invalid username or password.");
        }

    }
}


