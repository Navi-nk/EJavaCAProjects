package matrix.ecommerce.web;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import javax.inject.Named;
import matrix.ecommerce.business.CustomerBean;
import matrix.ecommerce.business.FruitBean;
import matrix.ecommerce.model.Customer;
import matrix.ecommerce.model.Fruit;
/**
 *
 * @author Sarita Sethy
 */

@RequestScoped
@Named
public class CustomerView implements Serializable{
    @EJB private CustomerBean customerBean;
    @Inject ShoppingView shoppingCart;
    @EJB private FruitBean fruitBean;
    
    private Integer id;
    private String name;
    private String address;
    private String phone;
    private String comments;
  
    @PostConstruct
	private void init() {
		System.out.println(">> Creating Customer");
	}

	@PreDestroy
	private void destroy() {
		System.out.println(">> Destroying Customer Details");
	}

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    

    public String getName() {
        return name;
    }     
    public void setName(String name) {
        this.name = name;
    }
    
    
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    
    
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
    
    
    public String addCustomer()
    {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setAddress(address);
        customer.setPhone(phone);
       // customer.setComments(comments);
        customerBean.addCustomer(customer);
        List<Fruit> fruits = shoppingCart.getFruits();
        if(fruits.size()>0)
        {
            
        }
        for(Fruit fruit:fruits)
        {
            //em.per
        }
        return ("thankyou");
    }
    public boolean checkCustomer()
    {
        //Check if customer exist
        Customer customer = customerBean.findCustomer(name);
        if(customer==null)
        return false;
        return true;
    }
    
    
}
