package matrix.snapshare.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Sarita
 */
@Entity
@Table(name = "user")
@NamedQueries({
     @NamedQuery(name = "User.findByName", query = "SELECT u FROM User u WHERE u.name = :name")
    })
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "user_id")
    private Integer userId;
    
    @Basic(optional = false)
    @Column(name = "user_name")
    private String userName;
    
   
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<UserFriend> userFriendsCollection;


    @XmlTransient
    public Collection<UserFriend> getUserFriendsCollection() {
        return userFriendsCollection;
    }

    public void setUserFriendsCollection(Collection<UserFriend> userFriendsCollection) {
        this.userFriendsCollection = userFriendsCollection;
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
        return "matrix.snapshare.model.User[ id=" + userId + " ]";
    }
    
}
