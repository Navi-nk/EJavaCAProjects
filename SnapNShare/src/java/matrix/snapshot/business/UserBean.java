package matrix.snapshot.business;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    
    public void getFriends() 
    {
            //em.persist();
         
    }
}
