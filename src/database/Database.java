/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;
import java.sql.*; 
import java.util.ArrayList;
import models.*;
/**
 *
 * @author dslim
 */
 
public class Database{  
    /**
     * 
     * @return 
     */
    public static Connection connect() {
        Connection con = null;
        try{  
            Class.forName("com.mysql.jdbc.Driver");  
            con=DriverManager.getConnection(  
            "jdbc:mysql://localhost:3306/cinema","root","");    
        }catch(Exception e){ System.out.println(e);}  
        return con;
    }
    
}  