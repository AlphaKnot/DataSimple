/**
 * this class represents an object of User
 */
public class User {
    public String CDusername;
    public String CDpassword;
   // public boolean CDpermission;

    /***
     * the constructor
     * @param username the username
     * @param password the password
     * //@param permission the permission
     */
    public User(String username, String password){
        CDusername = username;
        CDpassword = password;
       // CDpermission = permission;
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
