/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matrix.snapshare.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Sarita
 */
@Entity
@Table (name="Image")
public class Image implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "image_id")
    private Integer imageId;
    
    @Basic(optional = false)
    private String name;
    
    @Basic(optional = false)
    @Column(name = "created_date")
    private Date craetedDate;
    
    @Basic(optional = false)
    @Column(name = "image_url")
    private String imageUrl;
    
    @Basic(optional = false)
    @Column(name = "comments")
    private String comments;
    
    @JoinColumn(name = "user_name", referencedColumnName = "user_name")
    @ManyToOne(optional = false)
    private User user;

    public Integer getImageId() {
        return imageId;
    }   
    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    

    public Date getCraetedDate() {
        return craetedDate;
    }
    public void setCraetedDate(Date craetedDate) {
        this.craetedDate = craetedDate;
    }

    
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
    
     public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (imageId != null ? imageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Image)) {
            return false;
        }
        Image other = (Image) object;
        if ((this.imageId == null && other.imageId != null) || (this.imageId != null && !this.imageId.equals(other.imageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "matrix.snapshare.model.Image[ imageId=" + imageId + " ]";
    }
    
}
