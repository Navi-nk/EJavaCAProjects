package matrix.snapshot.business;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import matrix.snapshare.model.User;

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
    
    public boolean findUser(String name) {
      
        TypedQuery<User> query =  em.createNamedQuery("User.findByName", User.class);
        query.setParameter("name", name);
        query.setMaxResults(1);
        List<User> list = query.getResultList();
        
        if(list == null || list.isEmpty()) 
            return false;
        
        return true;
    
    }
    
    public void addFriend(String name) 
    {
        
        
            //em.persist();
         
    }
    
    public void getFriends() 
    {
            //em.persist();
         
    }
}
