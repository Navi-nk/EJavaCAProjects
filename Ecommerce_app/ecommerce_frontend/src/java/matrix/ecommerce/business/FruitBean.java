package matrix.ecommerce.business;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import matrix.ecommerce.model.Fruit;

/**
 *
 * @author Navi-PC
 */
@Stateless
public class FruitBean {
    
    @PersistenceContext private EntityManager em;

	public List<Fruit> getAllFruits() {

		TypedQuery<Fruit> query = em.createNamedQuery("Fruit.findAll",Fruit.class);
		return (query.getResultList());
	}	
}
