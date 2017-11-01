package matrix.ecommerce.web;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpSession;
import matrix.ecommerce.business.CustomerBean;
import matrix.ecommerce.business.FruitBean;
import matrix.ecommerce.business.ShoppingBean;
import matrix.ecommerce.model.Customer;
import matrix.ecommerce.model.Fruit;
import matrix.ecommerce.model.ShoppingCartItem;
import matrix.ecommerce.model.ShoppingCartPK;
import matrix.ecommerce.model.Order;
/**
 *
 * @author Sarita Sethy
 */

@ViewScoped
@Named
public class CustomerView implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @EJB private CustomerBean customerBean;
    @Inject ShoppingView shoppingView;
    @EJB private FruitBean fruitBean;
    @EJB private ShoppingBean shoppingBean;
    
    private List<ShoppingCartItem> shoppingCartItems = new LinkedList<>();
    private Float fullAmount = new Float(0);
    
    @Resource(lookup = "jms/factory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "jms/warehouse")
    private Queue warehouseQueue;
    
    private Integer id;
    private String name;
    private String address;
    private String phone;
    private String comments;
  
    @PostConstruct
	private void init() {
            
            shoppingCartItems = (List<ShoppingCartItem>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("shoppingCart");
            if(shoppingCartItems != null){
            for(ShoppingCartItem cItem : shoppingCartItems){
            System.out.println(cItem.getFruit().getName());
            fullAmount += cItem.getCost();
            }
        }
            
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

    public List<ShoppingCartItem> getShoppingCartItems() {
        return shoppingCartItems;
    }
    public void setShoppingCartItems(List<ShoppingCartItem> shoppingCartItems) {
        this.shoppingCartItems = shoppingCartItems;
    }

    public Float getFullAmount() {
        return fullAmount;
    }

    public void setFullAmount(Float fullAmount) {
        this.fullAmount = fullAmount;
    }

     
     
    public String addCustomer()
    {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setAddress(address);
        customer.setPhone(phone);
        customerBean.addCustomer(customer);
        
        List<Fruit> fruits = shoppingView.getFruits();
         Order cart =  new Order();
        if(fruits.size()>0)
        {
            cart.setComments(comments);
         //   cart.setCustomerId(customer.getId());
            shoppingBean.addCart(cart);
        }
        
        for(Fruit fruit:fruits)
        {
            ShoppingCartPK fruitCartPK = new ShoppingCartPK(cart.getId(),fruit.getId().intValue());
            ShoppingCartItem fruitCart = new ShoppingCartItem();
           // fruitCart.setFruitCartPK(fruitCartPK);
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
    
    public String endSession(){
        
        try (JMSContext jmsCtx = connectionFactory.createContext()) {
            JMSProducer producer = jmsCtx.createProducer();
            TextMessage txtMsg = jmsCtx.createTextMessage();
            //txtMsg.setText(new Date() + ">> " + shoppingCartItems.get(0).getFruit().getName());
            String msg = createJSONMessage();
            if(null != msg){
                txtMsg.setText(msg);
                producer.send(warehouseQueue, txtMsg);
            }
            else{
                System.out.println("Cannot send message - order details is not found");
                return null;
            }
        } catch (JMSException ex) {
            ex.printStackTrace();
        }
        
        HttpSession sess = (HttpSession) FacesContext.getCurrentInstance().
                getExternalContext().getSession(false);

		sess.invalidate();
                
                return ("welcome?faces-redirect=true");
    } 
    
    public String continueShopping(){
        return ("shopping?faces-redirect=true");
    }
    
    public String createJSONMessage(){
        if(shoppingCartItems != null){
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            //objectBuilder.add("name",name)
            //         .add("address", address)
            //         .add("comment", comments);
            objectBuilder.add("name","naval")
                        .add("address", "holland drive")
                        .add("comment", "testing");
        
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        
            shoppingCartItems.forEach( items -> {
                arrayBuilder.add(Json.createObjectBuilder()
                .add("item", items.getFruit().getName())
                .add("quantity", items.getSelectedQuantity()));
            });
            
            objectBuilder.add("cart", arrayBuilder.build());
            return objectBuilder.build().toString();
        }
        return null;
    }
}
