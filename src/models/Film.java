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
@Table(name = "film")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Film.findAll", query = "SELECT f FROM Film f")
    , @NamedQuery(name = "Film.findByFilmId", query = "SELECT f FROM Film f WHERE f.filmId = :filmId")
    , @NamedQuery(name = "Film.findByFilmTitle", query = "SELECT f FROM Film f WHERE f.filmTitle = :filmTitle")
    , @NamedQuery(name = "Film.findByFilmDirector", query = "SELECT f FROM Film f WHERE f.filmDirector = :filmDirector")
    , @NamedQuery(name = "Film.findByPoster", query = "SELECT f FROM Film f WHERE f.poster = :poster")
    , @NamedQuery(name = "Film.findByNationality", query = "SELECT f FROM Film f WHERE f.nationality = :nationality")
    , @NamedQuery(name = "Film.findByReleaseDate", query = "SELECT f FROM Film f WHERE f.releaseDate = :releaseDate")})
public class Film implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "film_id")
    private Integer filmId;
    @Column(name = "film_title")
    private String filmTitle;
    @Column(name = "film_director")
    private String filmDirector;
    @Column(name = "poster")
    private String poster;
    @Column(name = "nationality")
    private String nationality;
    @Column(name = "release_date")
    @Temporal(TemporalType.DATE)
    private Date releaseDate;
    @JoinColumn(name = "theme_id", referencedColumnName = "theme_id")
    @ManyToOne
    private int themeId;
    @OneToMany(mappedBy = "filmId")
    private Collection<Projection> projectionCollection;

    public Film() {
    }

    public Film(Integer filmId, String filmTitle, String filmDirector, String poster, String nationality, Date releaseDate, int themeId) {
        this.filmId = filmId;
        this.filmTitle = filmTitle;
        this.filmDirector = filmDirector;
        this.poster = poster;
        this.nationality = nationality;
        this.releaseDate = releaseDate;
        this.themeId = themeId;
    }

    public Film(String filmTitle, String filmDirector, String poster, String nationality, Date releaseDate, int themeId) {
        this.filmTitle = filmTitle;
        this.filmDirector = filmDirector;
        this.poster = poster;
        this.nationality = nationality;
        this.releaseDate = releaseDate;
        this.themeId = themeId;
    }

    public Film(Integer filmId) {
        this.filmId = filmId;
    }

    public Integer getFilmId() {
        return filmId;
    }

    public void setFilmId(Integer filmId) {
        this.filmId = filmId;
    }

    public String getFilmTitle() {
        return filmTitle;
    }

    public void setFilmTitle(String filmTitle) {
        this.filmTitle = filmTitle;
    }

    public String getFilmDirector() {
        return filmDirector;
    }

    public void setFilmDirector(String filmDirector) {
        this.filmDirector = filmDirector;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }
    
     /**
      * 
      */
    public void add() {
        Connection con = Database.connect();
        java.sql.Date date  = new java.sql.Date(this.releaseDate.getTime());
        try {
            Statement statement = con.createStatement();  
            // insert the data
            statement.executeUpdate("INSERT INTO film (film_title, film_director, poster, nationality, release_date, theme_id) " 
                    +"VALUES ('"+this.filmTitle
                            + "','"+this.filmDirector
                            +"','"+this.poster
                            +"','"+this.nationality
                            +"','"+date
                            +"',"+this.themeId+")");
            con.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 
     * @return 
     */
    public static ArrayList<Film> getAll() {
        ArrayList<Film> films = new ArrayList<Film>();
        Connection con = Database.connect();
        try {
            Statement statement =con.createStatement();  
            ResultSet rs = statement.executeQuery("SELECT * from film");
            while(rs.next()) {
                films.add(new Film(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6), rs.getInt(7)));
            }
            con.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return films;
    }
    
    /**
     * 
     * @param id 
     */
    public static Film getById(int id) throws Exception{
        Connection con = Database.connect();
        Film film = null;

            Statement statement =con.createStatement();  
            ResultSet rs = statement.executeQuery("SELECT * from film where film_id ="+id+"");
 
            if (!rs.next() ) {
                throw new IdNotFoundException();
            } else {
                rs.beforeFirst();
                while(rs.next()) {
                    film = new Film(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6), rs.getInt(7));
                }
            }
            con.close();
           
        return film;
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
        hash += (filmId != null ? filmId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Film)) {
            return false;
        }
        Film other = (Film) object;
        if ((this.filmId == null && other.filmId != null) || (this.filmId != null && !this.filmId.equals(other.filmId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cinematicket.Film[ filmId=" + filmId + " ]";
    }
    
}
