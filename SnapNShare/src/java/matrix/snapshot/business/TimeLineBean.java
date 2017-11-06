package matrix.snapshot.business;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import matrix.snapshare.model.Image;
import matrix.snapshare.model.User;
import matrix.snapshare.model.UserFriend;

/**
 *
 * @author Sarita
 */
@Stateless
public class TimeLineBean {

     @PersistenceContext private EntityManager em; 
     @EJB UserBean userBean;
     public List<Image> getImages(String name) 
     {
        String queryString = "SELECT u FROM User u where u.userName=:name";
        TypedQuery<User> query =  em.createQuery(queryString, User.class);
        query.setParameter("name", name);
        User user = query.getSingleResult();      
        
        queryString = "SELECT i FROM Image i join UserFriend uf where uf.user=:name and i.user=uf.userFriend order by i.craetedDate desc";
        TypedQuery<Image> query1 =  em.createQuery(queryString, Image.class).setMaxResults(5); 
        query1.setParameter("name", user);
      
        List<Image> list = query1.getResultList();
        if (list == null || list.isEmpty()) {
         return null;
        }
        
        return list;
         
    }
     
     public void add(Image i){
         em.persist(i);
     }
     
}
