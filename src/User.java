/**
 * this class represents an object of User
 * @auhor Jenessa Lu
 */
public class User {
    public String CDusername;
    public String CDpassword;

    /***
     * the constructor
     * @param username the username
     * @param password the password
     * //@param permission the permission
     */
    public User(String username, String password){
        CDusername = username;
        CDpassword = password;
    }

    /***
     * the getter method for username
     * @return the username
     */
    public String getUsername(){
        return CDusername;
    }

    /***
     * the getter method for password
     * @return the password
     */
    public String getPassword(){
        return CDpassword;
    }

}
