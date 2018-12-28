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
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
    , @NamedQuery(name = "User.findByUserId", query = "SELECT u FROM User u WHERE u.userId = :userId")
    , @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email")
    , @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password")
    , @NamedQuery(name = "User.findByName", query = "SELECT u FROM User u WHERE u.name = :name")})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "userId")
    private Collection<Ticket> ticketCollection;

    public User() {
    }

    public User(Integer userId, String email, String password, String name) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
    
    public User(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
       /**
      * 
      */
    public void add() {
        Connection con = Database.connect();
        try {
            Statement statement = con.createStatement();  
            // insert the data
            statement.executeUpdate("INSERT INTO user (email, password, name) " 
                    +"VALUES ('"+this.email
                            + "','"+this.password
                            +"','"+this.name
                            +"')");
            con.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 
     * @return 
     */
    public static ArrayList<User> getAll() {
        ArrayList<User> users = new ArrayList<User>();
        Connection con = Database.connect();
        try {
            Statement statement =con.createStatement();  
            ResultSet rs = statement.executeQuery("SELECT * from user");
            while(rs.next()) {
                users.add(new User(rs.getString(1),rs.getString(2),rs.getString(3)));
            }
            con.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return users;
    }
    
        /**
     * 
     * @param id 
     */
    public static User getById(int id) throws Exception{
        Connection con = Database.connect();
        User user = null;

            Statement statement =con.createStatement();  
            ResultSet rs = statement.executeQuery("SELECT * from user where user_id ="+id+"");
 
            if (!rs.next() ) {
                throw new IdNotFoundException();
            } else {
                rs.beforeFirst();
                while(rs.next()) {
                    user = new User(rs.getString(1),rs.getString(2),rs.getString(3));
                }
            }
            con.close();
           
        return user;
    }


    @XmlTransient
    public Collection<Ticket> getTicketCollection() {
        return ticketCollection;
    }

    public void setTicketCollection(Collection<Ticket> ticketCollection) {
        this.ticketCollection = ticketCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cinematicket.User[ userId=" + userId + " ]";
    }
    
}
