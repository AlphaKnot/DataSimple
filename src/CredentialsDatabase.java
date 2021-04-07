// importing the libraries required for this class
import java.io.*;
import java.util.*;

/***
 * this class implements the database of the login system using a hash table
 * @author Jenessa Lu, Amaar Hussein
 */
public class CredentialsDatabase{
    Hashtable database = new Hashtable<>();

    /***
     * the constructor for the database
     * @throws Exception if the file could not be read
     */
    public CredentialsDatabase() throws Exception{
        // reads the credentials text file
        BufferedReader br = new BufferedReader((new FileReader("credentials.txt")));

        String line;
        String username = "";
        String password = "";

        // reading the file; first line is the username (key) and the second line is the password (value)
        while ((line = br.readLine()) != null){
            username = line;
            password = br.readLine();
            User user = new User(username,password);
            // puts the information into the database
            database.put(user.getUsername(),user.getPassword());
        }
    }

    /***
     * getter method for the value
     * @param username the key
     * @return the value of the key
     */
    public String getValue(String username){
        return (String) database.get(username);
    }

}
