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
@Table(name = "theme")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Theme.findAll", query = "SELECT t FROM Theme t")
    , @NamedQuery(name = "Theme.findByThemeId", query = "SELECT t FROM Theme t WHERE t.themeId = :themeId")
    , @NamedQuery(name = "Theme.findByThemeName", query = "SELECT t FROM Theme t WHERE t.themeName = :themeName")})
public class Theme implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "theme_id")
    private Integer themeId;
    @Column(name = "theme_name")
    private String themeName;
    @OneToMany(mappedBy = "themeId")
    private Collection<Film> filmCollection;

    public Theme() {
    }

    public Theme(Integer themeId, String themeName) {
        this.themeId = themeId;
        this.themeName = themeName;
    }

    public Theme(Integer themeId) {
        this.themeId = themeId;
    }

    public Integer getThemeId() {
        return themeId;
    }

    public void setThemeId(Integer themeId) {
        this.themeId = themeId;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }
    
    /**
      * 
      */
    public void add() {
        Connection con = Database.connect();
        
        try {
            Statement statement =con.createStatement();  
            // insert the data
            statement.executeUpdate("INSERT INTO theme(theme_name) " + "VALUES ('"+this.themeName+"' )");
            con.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
      * 
      */
    public void delete() {
        Connection con = Database.connect();
        
        try {
            Statement statement =con.createStatement();  
            // insert the data
            statement.executeUpdate("delete from theme where theme_id = "+this.themeId);
            con.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 
     * @return 
     */
    public static ArrayList<Theme> getAll() {
        ArrayList<Theme> themes = new ArrayList<Theme>();
        Connection con = Database.connect();
        try {
            Statement statement =con.createStatement();  
            ResultSet rs = statement.executeQuery("SELECT * from theme");
            while(rs.next()) {
                themes.add(new Theme(rs.getInt(1),rs.getString(2)));
            }
            con.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return themes;
    }
    
    /**
     * 
     * @param id 
     */
    public static Theme getById(int id) throws Exception{
        Connection con = Database.connect();
        Theme theme = null;

            Statement statement =con.createStatement();  
            ResultSet rs = statement.executeQuery("SELECT * from theme where theme_id ="+id+"");
 
            if (!rs.next() ) {
                throw new IdNotFoundException();
            } else {
                rs.beforeFirst();
                while(rs.next()) {
                    theme = new Theme(rs.getInt(1),rs.getString(2));
                }
            }
            con.close();
           
        return theme;
    }

    @XmlTransient
    public Collection<Film> getFilmCollection() {
        return filmCollection;
    }

    public void setFilmCollection(Collection<Film> filmCollection) {
        this.filmCollection = filmCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (themeId != null ? themeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Theme)) {
            return false;
        }
        Theme other = (Theme) object;
        if ((this.themeId == null && other.themeId != null) || (this.themeId != null && !this.themeId.equals(other.themeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cinematicket.Theme[ themeId=" + themeId + " ]";
    }
    
}
