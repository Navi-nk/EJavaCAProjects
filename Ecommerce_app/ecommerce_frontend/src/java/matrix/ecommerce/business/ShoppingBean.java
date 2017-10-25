package matrix.ecommerce.business;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import matrix.ecommerce.model.ShoppingCart;
/**
 *
 * @author Sarita
 */
@Stateless
public class ShoppingBean {
    @PersistenceContext private EntityManager em;
   
    public void addCart(ShoppingCart cart)  
    {
        em.persist(cart);
    }
}
