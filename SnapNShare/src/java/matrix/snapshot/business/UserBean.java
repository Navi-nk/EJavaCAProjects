package matrix.snapshot.business;


import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
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
    
    public void addUser(String name) 
    { 
            User u = new User();
            u.setUserName(name);
            em.persist(u);     
    }
    
    public User findUser(String name) {
      
        TypedQuery<User> query =  em.createNamedQuery("User.findByName", User.class);
        query.setParameter("name", name);
        query.setMaxResults(1);
        List<User> list = query.getResultList();
        
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    public void addFriend(String name, User u, User friendUser) 
    {
        
        UserFriend friend = new UserFriend();
   
        friend.setUser(u);
        friend.setUserFriend(friendUser);
        
           em.persist(friend);
         
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
