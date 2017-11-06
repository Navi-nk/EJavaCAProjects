package matrix.snapshot.business;

import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import matrix.snapshare.model.User;
import matrix.snapshare.model.UserFriend;

/**
 *
 * @author Navi-PC
 */
@Stateless
public class UserBean {
    @PersistenceContext private EntityManager em;  
   
    public void addUser() 
    {
            //em.persist();
         
    }
    
    public void addFriend() 
    {
            //em.persist();
         
    }
    
    public List<String> getFriends(String name) 
    {
        List<String> friends = new LinkedList<>();
       
        String queryString = "SELECT u FROM User u where u.userName=:name";
        TypedQuery<User> query =  em.createQuery(queryString, User.class);
        query.setParameter("name", name);
         User user = query.getSingleResult();
        
        queryString = "SELECT u FROM UserFriend u where u.user=:name";
        TypedQuery<UserFriend> query1 =  em.createQuery(queryString, UserFriend.class);
        query1.setParameter("name", user);
        
        List<UserFriend> list = query1.getResultList();
        if (list == null || list.isEmpty()) {
         return null;
        }
        else
        {
            for(UserFriend friend:list)
            {
                friends.add(friend.getUserFriend().getUserName());
            }
        }
            
        return friends;
         
    }
}
