/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinematicket;

import java.util.ArrayList;
import models.Film;
import java.util.Date;
import java.lang.Exception;

/**
 *
 * @author dslim
 */
public class CinemaTicket {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        try {
            Film f = Film.getById(1);
            System.out.println(f.getFilmTitle());
            System.out.println("dali");
            System.out.println("salih");
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
    
}
