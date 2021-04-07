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

        // User Label
        user_label = new JLabel();
        user_label.setText("User Name :");
        userName_text = new JTextField();

        // Password

        password_label = new JLabel();
        password_label.setText("Password :");
        password_text = new JPasswordField();

        submit = new JButton("SUBMIT");

        panel = new JPanel(new GridLayout(3, 1));

        panel.add(user_label);
        panel.add(userName_text);
        panel.add(password_label);
        panel.add(password_text);

        message = new JLabel();
        panel.add(message);
        panel.add(submit);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // Adding the listeners to components..
        submit.addActionListener(this);
        password_text.addActionListener(this);
        add(panel, BorderLayout.CENTER);
        setTitle("Login -- Alpha Knot Inc.");
        setSize(450, 150);
        setVisible(true);

    }

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        new LoginInterface();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        CredentialsDatabase database = null;
        try {
            // getting the credentials database
            database = new CredentialsDatabase();
        } catch (Exception e) {
            e.printStackTrace();
            message.setText("credentials database failed to sync");
        }
        // getting the inputs from the user
        String userName = userName_text.getText();
        String password = password_text.getText();

        // comparing the user input to the credentials database
        if (database.getValue(userName.trim()) != null && database.getValue(userName.trim()).equals(password.trim())){
            ProgramUI mainUI = null;
            try {
                mainUI = new ProgramUI();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            mainUI.setVisible(true);
           this.setVisible(false);
        }
        // if there is an error, will send a message that it is invalid
        else{
            message.setText("Invalid username or password.");
        }

    }
}


