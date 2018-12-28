/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import cinematicket.*;
import database.Database;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import utils.IdNotFoundException;

/**
 *
 * @author dslim
 */
@Entity
@Table(name = "ticket")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ticket.findAll", query = "SELECT t FROM Ticket t")
    , @NamedQuery(name = "Ticket.findByTicketId", query = "SELECT t FROM Ticket t WHERE t.ticketId = :ticketId")
    , @NamedQuery(name = "Ticket.findByPlaceNumber", query = "SELECT t FROM Ticket t WHERE t.placeNumber = :placeNumber")})
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ticket_id")
    private Integer ticketId;
    @Column(name = "place_number")
    private Integer placeNumber;
    @JoinColumn(name = "projection_id", referencedColumnName = "projection_id")
    @ManyToOne
    private int projectionId;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private int userId;

    public Ticket() {
    }

    public Ticket(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public Ticket(Integer ticketId, Integer placeNumber, int projectionId, int userId) {
        this.ticketId = ticketId;
        this.placeNumber = placeNumber;
        this.projectionId = projectionId;
        this.userId = userId;
    }

    public Ticket(Integer placeNumber, int projectionId, int userId) {
        this.placeNumber = placeNumber;
        this.projectionId = projectionId;
        this.userId = userId;
    }
    
    

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public Integer getPlaceNumber() {
        return placeNumber;
    }

    public void setPlaceNumber(Integer placeNumber) {
        this.placeNumber = placeNumber;
    }

    public int getProjectionId() {
        return projectionId;
    }

    public void setProjectionId(int projectionId) {
        this.projectionId = projectionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
      /**
      * 
      */
    public void add() {
        Connection con = Database.connect();
        try {
            Statement statement = con.createStatement();  
            // insert the data
            statement.executeUpdate("INSERT INTO ticket (place_number, projection_id, user_id) " 
                    +"VALUES ("+this.placeNumber
                            + ","+this.projectionId
                            +","+this.userId
                            +")");
            con.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 
     * @return 
     */
    public static ArrayList<Ticket> getAll() {
        ArrayList<Ticket> tickets = new ArrayList<Ticket>();
        Connection con = Database.connect();
        try {
            Statement statement =con.createStatement();  
            ResultSet rs = statement.executeQuery("SELECT * from ticket");
            while(rs.next()) {
                tickets.add(new Ticket(rs.getInt(1), rs.getInt(2),rs.getInt(3),rs.getInt(4)));
            }
            con.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return tickets;
    }
    
     /**
     * 
     * @param id 
     */
    public static Ticket getById(int id) throws Exception{
        Connection con = Database.connect();
        Ticket ticket = null;

            Statement statement =con.createStatement();  
            ResultSet rs = statement.executeQuery("SELECT * from ticket where ticket_id ="+id+"");
 
            if (!rs.next() ) {
                throw new IdNotFoundException();
            } else {
                rs.beforeFirst();
                while(rs.next()) {
                    ticket = new Ticket(rs.getInt(1), rs.getInt(2),rs.getInt(3),rs.getInt(4));
                }
            }
            con.close();
           
        return ticket;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ticketId != null ? ticketId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ticket)) {
            return false;
        }
        Ticket other = (Ticket) object;
        if ((this.ticketId == null && other.ticketId != null) || (this.ticketId != null && !this.ticketId.equals(other.ticketId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cinematicket.Ticket[ ticketId=" + ticketId + " ]";
    }
    
}
