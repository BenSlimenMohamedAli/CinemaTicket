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
public class IdNotFoundException extends Exception{
    public IdNotFoundException() {
        super();
    }
    
    public IdNotFoundException(String message) { super(message); }
    public IdNotFoundException(String message, Throwable cause) { super(message, cause); }
    public IdNotFoundException(Throwable cause) { super(cause); }
    public String getMessage() {
        return Strings.getError("id_not_found");
    }
}
