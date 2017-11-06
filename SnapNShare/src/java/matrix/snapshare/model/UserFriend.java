package matrix.snapshare.model;

import java.io.Serializable;
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

/**
 *
 * @author Sarita
 */
@Entity
@Table(name = "user_friend")
@NamedQueries({
     @NamedQuery(name = "UserFriend.findById", query = "SELECT friends FROM UserFriend friends WHERE friends.user = :user")
    })
public class UserFriend implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "friend_id")
    private Integer friendId;
    
       
    @JoinColumn(name = "user", referencedColumnName = "user_name")
    @ManyToOne(optional = false)
    private User  user;
    
    @JoinColumn(name = "friend", referencedColumnName = "user_name")
    @ManyToOne(optional = false)
    private User userFriend;
    
     public Integer getFriendId() {
        return friendId;
    }
    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }
    
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public User getUserFriend() {
        return userFriend;
    }
    public void setUserFriend(User userFriend) {
        this.userFriend = userFriend;
    }
    

   
    

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (friendId != null ? friendId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the friendId fields are not set
        if (!(object instanceof UserFriend)) {
            return false;
        }
        UserFriend other = (UserFriend) object;
        if ((this.friendId == null && other.friendId != null) || (this.friendId != null && !this.friendId.equals(other.friendId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "matrix.snapshare.model.UserFriend[ id=" + friendId + " ]";
    }
    
}
