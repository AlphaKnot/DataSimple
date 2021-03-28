package credentialsDatabase;

import java.io.*;
import java.util.*;

public class CredentialsDatabase{

    public static void main(String[] args) throws Exception{
        Hashtable<String,String> database = new Hashtable<>();

        BufferedReader br = new BufferedReader(new FileReader("credentials.txt"));

        String st;
        while ((st = br.readLine()) != null){
            //System.out.println(st);
            database.put(st,st);
        }

        System.out.println("database : " + database);



    }
}
