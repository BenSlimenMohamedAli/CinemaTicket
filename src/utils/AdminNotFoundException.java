/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;
import java.lang.Exception;
import strings.Strings;

/**
 *
 * @author dslim
 */
public class AdminNotFoundException extends Exception{
    public AdminNotFoundException() {
        super();
    }
    
    public AdminNotFoundException(String message) { super(message); }
    public AdminNotFoundException(String message, Throwable cause) { super(message, cause); }
    public AdminNotFoundException(Throwable cause) { super(cause); }
    public String getMessage() {
        return Strings.getError("admin_not_found");
    }
}
