/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import cinematicket.*;
import java.io.Serializable;
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
    private Admin adminId;
    @JoinColumn(name = "film_id", referencedColumnName = "film_id")
    @ManyToOne
    private Film filmId;
    @JoinColumn(name = "room_id", referencedColumnName = "room_id")
    @ManyToOne
    private Room roomId;

    public Projection() {
    }

    public Projection(Integer projectionId) {
        this.projectionId = projectionId;
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

    public Admin getAdminId() {
        return adminId;
    }

    public void setAdminId(Admin adminId) {
        this.adminId = adminId;
    }

    public Film getFilmId() {
        return filmId;
    }

    public void setFilmId(Film filmId) {
        this.filmId = filmId;
    }

    public Room getRoomId() {
        return roomId;
    }

    public void setRoomId(Room roomId) {
        this.roomId = roomId;
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
