/***
 * this class implements the database of the login system using a hash table
 */

import java.io.*;
import java.util.*;

public class CredentialsDatabase{
    Hashtable database = new Hashtable<>();

    /***
     * constructs the database
     * @throws Exception if the file could not be read
     */
    public CredentialsDatabase() throws Exception{
        BufferedReader br = new BufferedReader((new FileReader("credentials.txt")));

        String line;
        String username = "";
        String password = "";

        // reading the file; first line is the username (key) and the second line is the password (value)
        while ((line = br.readLine()) != null){
            username = line;
            password = br.readLine();
            User user = new User(username,password);
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
