package matrix.ecommerce.web;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import matrix.ecommerce.model.Order;
import matrix.ecommerce.model.ShoppingCartItem;
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
    
    @Resource(lookup = "jms/warehouse")
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "jms/warehouseQueue")
    private Queue warehouseQueue;
    
    private Integer id;
    private String name;
    private String address;
    private String phone;
    private String comments;
    private String email;
  
    @PostConstruct
	private void init() {
            shoppingCartItems = (List<ShoppingCartItem>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("shoppingCart");
            if(shoppingCartItems != null){
            for(ShoppingCartItem cItem : shoppingCartItems){
            System.out.println(cItem.getFruitId().getName());
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
    
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
    
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
    
    
    public String addCustomer()
    {
            Customer customer = customerBean.findCustomer(name);
            if(customer==null)
            {
                customer = new Customer();
                customer.setName(name);
                customer.setAddress(address);
                customer.setPhone(phone);
                customer.setEmail(email);
                customerBean.addCustomer(customer);
            }
            else
            {
                customer.setAddress(address);
                customer.setPhone(phone);
                customer.setEmail(email);
                customerBean.updateCustomer(customer);
            }
                
                      
            
            List<ShoppingCartItem> shoppingCart = shoppingView.getShoppingCartItems();
            
            Order order =  new Order();
            if(shoppingCart.size()>0)
            {
                float totalCost =0;
                for(ShoppingCartItem cart:shoppingCart)
                {
                    totalCost = totalCost + cart.getCost();
                }
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                order.setComments(comments);
                order.setTotalCost(totalCost);
                order.setCreatedDate(new Date());
                order.setCustomerId(customer);
                shoppingBean.addCart(order);
            }
            
            
            if(shoppingCart.size()>0)
            {
                for(ShoppingCartItem cart:shoppingCart)
                {
                    cart.setOrderId(order);
                    shoppingBean.addFruitCart(cart);
                }
            }
           
                     
        return ("thankyou");
    }
    public String checkCustomer()
    {
        //Check if customer exist
        Customer existCustomer;
        if(this.name.length()>0)
        {
            existCustomer = customerBean.findCustomer(name);
            if(existCustomer!=null)
            {
                this.setAddress(existCustomer.getName());
                this.setPhone(existCustomer.getPhone());
                this.setEmail(existCustomer.getEmail());
                this.setId(existCustomer.getId());
                        
            }
        }
        
        return "checkout";
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
                .add("item", items.getFruitId().getName())
                .add("quantity", items.getSelectedQuantity()));
            });
            
            objectBuilder.add("cart", arrayBuilder.build());
            return objectBuilder.build().toString();
        }
        return null;
    }
}
