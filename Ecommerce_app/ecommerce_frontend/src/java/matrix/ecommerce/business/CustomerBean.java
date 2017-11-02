package matrix.ecommerce.business;

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
        Customer customer =  (Customer) query.getSingleResult();
        return customer;
    }
    
}
