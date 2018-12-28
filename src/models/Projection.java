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
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import utils.IdNotFoundException;

/**
 *
 * @author dslim
 */
@Entity
@Table(name = "projection")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Projection.findAll", query = "SELECT p FROM Projection p")
    , @NamedQuery(name = "Projection.findByProjectionId", query = "SELECT p FROM Projection p WHERE p.projectionId = :projectionId")
    , @NamedQuery(name = "Projection.findByProjectionTime", query = "SELECT p FROM Projection p WHERE p.projectionTime = :projectionTime")
    , @NamedQuery(name = "Projection.findByPrice", query = "SELECT p FROM Projection p WHERE p.price = :price")
    , @NamedQuery(name = "Projection.findByPlacesSold", query = "SELECT p FROM Projection p WHERE p.placesSold = :placesSold")})
public class Projection implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "projection_id")
    private Integer projectionId;
    @Column(name = "projection_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date projectionTime;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "price")
    private Float price;
    @Column(name = "places_sold")
    private Integer placesSold;
    @OneToMany(mappedBy = "projectionId")
    private Collection<Ticket> ticketCollection;
    @JoinColumn(name = "admin_id", referencedColumnName = "admin_id")
    @ManyToOne
    private int adminId;
    @JoinColumn(name = "film_id", referencedColumnName = "film_id")
    @ManyToOne
    private int filmId;
    @JoinColumn(name = "room_id", referencedColumnName = "room_id")
    @ManyToOne
    private int roomId;

    public Projection() {
    }

    public Projection(Integer projectionId) {
        this.projectionId = projectionId;
    }

    public Projection(Integer projectionId, Date projectionTime, Float price, Integer placesSold, int adminId, int filmId, int roomId) {
        this.projectionId = projectionId;
        this.projectionTime = projectionTime;
        this.price = price;
        this.placesSold = placesSold;
        this.adminId = adminId;
        this.filmId = filmId;
        this.roomId = roomId;
    }

    public Projection(Date projectionTime, Float price, Integer placesSold, int adminId, int filmId, int roomId) {
        this.projectionTime = projectionTime;
        this.price = price;
        this.placesSold = placesSold;
        this.adminId = adminId;
        this.filmId = filmId;
        this.roomId = roomId;
    }
    
    

    public Integer getProjectionId() {
        return projectionId;
    }

    public void setProjectionId(Integer projectionId) {
        this.projectionId = projectionId;
    }

    public Date getProjectionTime() {
        return projectionTime;
    }

    public void setProjectionTime(Date projectionTime) {
        this.projectionTime = projectionTime;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getPlacesSold() {
        return placesSold;
    }

    public void setPlacesSold(Integer placesSold) {
        this.placesSold = placesSold;
    }

    @XmlTransient
    public Collection<Ticket> getTicketCollection() {
        return ticketCollection;
    }

    public void setTicketCollection(Collection<Ticket> ticketCollection) {
        this.ticketCollection = ticketCollection;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    
     /**
      * 
      */
    public void add() {
        Connection con = Database.connect();
        java.sql.Timestamp datetime  = new java.sql.Timestamp(this.projectionTime.getTime());
        try {
            Statement statement = con.createStatement();  
            // insert the data
            statement.executeUpdate("INSERT INTO projection (projection_time, price, places_sold, admin_id, film_id, room_id) " 
                    +"VALUES ('"+datetime
                            + "',"+this.price
                            +","+this.placesSold
                            +","+this.adminId
                            +","+this.filmId
                            +","+this.roomId
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
    public static ArrayList<Projection> getAll() {
        ArrayList<Projection> projections = new ArrayList<Projection>();
        Connection con = Database.connect();
        try {
            Statement statement =con.createStatement();  
            ResultSet rs = statement.executeQuery("SELECT * from projection");
            while(rs.next()) {
                projections.add(new Projection(rs.getInt(1), rs.getTimestamp(2), rs.getFloat(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7)));
            }
            con.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return projections;
    }
    
    /**
     * 
     * @param id 
     */
    public static Projection getById(int id) throws Exception{
        Connection con = Database.connect();
        Projection projection = null;

            Statement statement =con.createStatement();  
            ResultSet rs = statement.executeQuery("SELECT * from projection where projection_id ="+id+"");
 
            if (!rs.next() ) {
                throw new IdNotFoundException();
            } else {
                rs.beforeFirst();
                while(rs.next()) {
                    projection = new Projection(rs.getInt(1), rs.getTimestamp(2), rs.getFloat(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7));
                }
            }
            con.close();
           
        return projection;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (projectionId != null ? projectionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Projection)) {
            return false;
        }
        Projection other = (Projection) object;
        if ((this.projectionId == null && other.projectionId != null) || (this.projectionId != null && !this.projectionId.equals(other.projectionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cinematicket.Projection[ projectionId=" + projectionId + " ]";
    }
    
}
