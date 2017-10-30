package matrix.ecommerce.business;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import matrix.ecommerce.model.ShoppingCartItem;
import matrix.ecommerce.model.Order;
/**
 *
 * @author Sarita
 */
@Stateless
public class ShoppingBean {
    @PersistenceContext private EntityManager em;
   
    public void addCart(Order cart)  
    {
        em.persist(cart);
    }
    public void addFruitCart(ShoppingCartItem fruitCart)  
    {
        em.persist(fruitCart);
    }
}
