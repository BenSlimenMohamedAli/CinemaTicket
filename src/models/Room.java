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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import utils.IdNotFoundException;

/**
 *
 * @author dslim
 */
@Entity
@Table(name = "room")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Room.findAll", query = "SELECT r FROM Room r")
    , @NamedQuery(name = "Room.findByRoomId", query = "SELECT r FROM Room r WHERE r.roomId = :roomId")
    , @NamedQuery(name = "Room.findByRoomName", query = "SELECT r FROM Room r WHERE r.roomName = :roomName")
    , @NamedQuery(name = "Room.findByRoomNumber", query = "SELECT r FROM Room r WHERE r.roomNumber = :roomNumber")
    , @NamedQuery(name = "Room.findByCapacity", query = "SELECT r FROM Room r WHERE r.capacity = :capacity")})
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "room_id")
    private Integer roomId;
    @Column(name = "room_name")
    private String roomName;
    @Column(name = "room_number")
    private Integer roomNumber;
    @Column(name = "capacity")
    private Integer capacity;
    @OneToMany(mappedBy = "roomId")
    private Collection<Projection> projectionCollection;

    public Room() {
    }

    public Room(String roomName, Integer roomNumber, Integer capacity) {
        this.roomName = roomName;
        this.roomNumber = roomNumber;
        this.capacity = capacity;
    }

    public Room(Integer roomId, String roomName, Integer roomNumber, Integer capacity) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomNumber = roomNumber;
        this.capacity = capacity;
    }

    public Room(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
    
     /**
      * 
      */
    public void add() {
        Connection con = Database.connect();
        try {
            Statement statement = con.createStatement();  
            // insert the data
            statement.executeUpdate("INSERT INTO room (room_name, room_number, capacity) " 
                    +"VALUES ('"+this.roomName
                            + "',"+this.roomNumber
                            +","+this.capacity
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
    public static ArrayList<Room> getAll() {
        ArrayList<Room> rooms = new ArrayList<Room>();
        Connection con = Database.connect();
        try {
            Statement statement =con.createStatement();  
            ResultSet rs = statement.executeQuery("SELECT * from room");
            while(rs.next()) {
                rooms.add(new Room(rs.getInt(1), rs.getString(2),rs.getInt(3),rs.getInt(4)));
            }
            con.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return rooms;
    }
    
    /**
     * 
     * @param id 
     */
    public static Room getById(int id) throws Exception{
        Connection con = Database.connect();
        Room room = null;

            Statement statement =con.createStatement();  
            ResultSet rs = statement.executeQuery("SELECT * from room where room_id ="+id+"");
 
            if (!rs.next() ) {
                throw new IdNotFoundException();
            } else {
                rs.beforeFirst();
                while(rs.next()) {
                    room = new Room(rs.getInt(1), rs.getString(2),rs.getInt(3),rs.getInt(4));
                }
            }
            con.close();
           
        return room;
    }

    @XmlTransient
    public Collection<Projection> getProjectionCollection() {
        return projectionCollection;
    }

    public void setProjectionCollection(Collection<Projection> projectionCollection) {
        this.projectionCollection = projectionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roomId != null ? roomId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Room)) {
            return false;
        }
        Room other = (Room) object;
        if ((this.roomId == null && other.roomId != null) || (this.roomId != null && !this.roomId.equals(other.roomId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cinematicket.Room[ roomId=" + roomId + " ]";
    }
    
}
