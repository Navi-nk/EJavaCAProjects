package matrix.ecommerce.business;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import matrix.ecommerce.model.Customer;

/**
 *
 * @author Sarita
 */
@Stateless
public class CustomerBean {
    
    @PersistenceContext private EntityManager em;  
   
    public void addCustomer(Customer customer) 
    {
            em.persist(customer);
         
    }
    public void updateCustomer(Customer customer) 
    {
        
              em.merge(customer);
        
    }
    public Customer findCustomer(String name)
    {
        TypedQuery<Customer> query =  em.createNamedQuery("Customer.findByName", Customer.class);
        query.setParameter("name", name);
        query.setMaxResults(1);
        List<Customer> list = query.getResultList();
        if (list == null || list.isEmpty()) {
        return null;
        }
        return list.get(0);
    }
    
}
