/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.*; 
import database.Database;
import cinematicket.*;
import java.io.Serializable;
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
import strings.Strings;
import utils.IdNotFoundException;
import utils.AdminNotFoundException;

/**
 *
 * @author dslim
 */
@Entity
@Table(name = "admin")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Admin.findAll", query = "SELECT a FROM Admin a")
    , @NamedQuery(name = "Admin.findByAdminId", query = "SELECT a FROM Admin a WHERE a.adminId = :adminId")
    , @NamedQuery(name = "Admin.findByEmail", query = "SELECT a FROM Admin a WHERE a.email = :email")
    , @NamedQuery(name = "Admin.findByPassword", query = "SELECT a FROM Admin a WHERE a.password = :password")
    , @NamedQuery(name = "Admin.findByName", query = "SELECT a FROM Admin a WHERE a.name = :name")})
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "admin_id")
    private Integer adminId;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "adminId")
    private Collection<Projection> projectionCollection;

    public Admin(Integer adminId, String email, String password, String name) {
        this.adminId = adminId;
        this.email = email;
        this.password = password;
        this.name = name;
    }
    
    

    public Admin() {
    }

    public Admin(Integer adminId) {
        this.adminId = adminId;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
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
            Statement statement =con.createStatement();  
            // insert the data
            statement.executeUpdate("INSERT INTO admin(email,password,name) " + "VALUES ('"+this.email+"','"+this.password+"','"+this.name+"' )");
            con.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 
     * @return 
     */
    public static ArrayList<Admin> getAll() {
        ArrayList<Admin> admins = new ArrayList<Admin>();
        Connection con = Database.connect();
        try {
            Statement statement =con.createStatement();  
            ResultSet rs = statement.executeQuery("SELECT * from admin");
            while(rs.next()) {
                admins.add(new Admin(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4)));
            }
            con.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return admins;
    }
    
    /**
     * 
     * @param id 
     */
    public static Admin getById(int id) throws Exception{
        Connection con = Database.connect();
        Admin admin = null;

            Statement statement =con.createStatement();  
            ResultSet rs = statement.executeQuery("SELECT * from admin where admin_id ="+id+"");
 
            if (!rs.next() ) {
                throw new IdNotFoundException();
            } else {
                rs.beforeFirst();
                while(rs.next()) {
                    admin = new Admin(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4));
                }
            }
            con.close();
           
        return admin;
    }
    
    public static boolean verifyExistense(String email , String password) throws Exception{
        Connection con = Database.connect();
        Statement statement =con.createStatement();  
        ResultSet rs = statement.executeQuery("SELECT * from admin where email ='"+email+"' and password ='"+password+"'");
        
        if (!rs.next() ) {
            throw new AdminNotFoundException();
        } 
        con.close();
            
        return true;
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
        hash += (adminId != null ? adminId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Admin)) {
            return false;
        }
        Admin other = (Admin) object;
        if ((this.adminId == null && other.adminId != null) || (this.adminId != null && !this.adminId.equals(other.adminId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cinematicket.Admin[ adminId=" + adminId + " ]";
    }
    
}
