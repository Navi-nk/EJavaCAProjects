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
import matrix.ecommerce.business.ShoppingBean;
import matrix.ecommerce.model.Customer;
import matrix.ecommerce.model.Fruit;
import matrix.ecommerce.model.FruitCart;
import matrix.ecommerce.model.FruitCartPK;
import matrix.ecommerce.model.ShoppingCart;
/**
 *
 * @author Sarita Sethy
 */

@RequestScoped
@Named
public class CustomerView implements Serializable{
    @EJB private CustomerBean customerBean;
    @Inject ShoppingView shoppingView;
    @EJB private FruitBean fruitBean;
    @EJB private ShoppingBean shoppingBean;
    
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
        customerBean.addCustomer(customer);
        
        List<Fruit> fruits = shoppingView.getFruits();
         ShoppingCart cart =  new ShoppingCart();
        if(fruits.size()>0)
        {
            cart.setComments(comments);
            cart.setCustomerId(customer.getId());
            shoppingBean.addCart(cart);
        }
        
        for(Fruit fruit:fruits)
        {
            FruitCartPK fruitCartPK = new FruitCartPK(cart.getId(),fruit.getId().intValue());
            FruitCart fruitCart = new FruitCart();
            fruitCart.setFruitCartPK(fruitCartPK);
            shoppingBean.addFruitCart(fruitCart);
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
