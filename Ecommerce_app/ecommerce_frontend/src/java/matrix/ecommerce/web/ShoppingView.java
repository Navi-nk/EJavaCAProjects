package matrix.ecommerce.web;

import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import matrix.ecommerce.business.FruitBean;
import matrix.ecommerce.model.Fruit;
import matrix.ecommerce.model.ShoppingCartItem;
import matrix.ecommerce.model.ShoppingCartItem_;
import org.primefaces.context.RequestContext;
import static org.primefaces.context.RequestContext.getCurrentInstance;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sarita
 */
@SessionScoped
@Named
public class ShoppingView implements Serializable{
    @EJB private FruitBean fruitBean;
        
    private List<Fruit> fruits;
    private List<ShoppingCartItem> shoppingCartItems = new LinkedList<>();
    private ShoppingCartItem cartItem;
    private Integer newQuantity;
    private Fruit selectedFruit;

    public ShoppingView() {
        this.cartItem = new ShoppingCartItem(Float.valueOf(0), 0);
    }

    @PostConstruct
    private void init() {
    populateView(); }
    
    public List<Fruit> getFruits() {
        return fruits;
    }
    public void setFruits(List<Fruit> fruits) {
        this.fruits = fruits;
    }

    public List<ShoppingCartItem> getShoppingCartItems() {
        return shoppingCartItems;
    }
    public void setShoppingCartItems(List<ShoppingCartItem> shoppingCartItems) {
        this.shoppingCartItems = shoppingCartItems;
    }

    public ShoppingCartItem getCartItem() {
        return cartItem;
    }
    public void setCartItem(ShoppingCartItem cartItem) {
        this.cartItem = cartItem;
    }

    public Integer getNewQuantity() {
        return newQuantity;
    }
    public void setNewQuantity(Integer newQuantity) {
        this.newQuantity = newQuantity;
    }

    public Fruit getSelectedFruit() {
        return selectedFruit;
    }
    public void setSelectedFruit(Fruit selectedFruit) {
        this.selectedFruit = selectedFruit;
    }
    
    
    
    public void storeSelectedFruit(Fruit selectedFruit) { 
        for(ShoppingCartItem cItem : shoppingCartItems){
            System.out.println(cItem.getFruit().getName() +" "+selectedFruit.getName());
            if((cItem.getFruit()).equals(selectedFruit)){
                cartItem = cItem;
                System.out.println("fruit found");
                return;
            }
        }
        System.out.println("new fruit added");
        cartItem.setFruit(selectedFruit);
        shoppingCartItems.add(cartItem);
    }

    public void storeSelectedQuantity(Integer selectedQuantity) {
        System.out.println("inside increase");
        cartItem.increaseQuantity(selectedQuantity);  
        System.out.println(cartItem.getSelectedQuantity());
    }
    
    public void calculateCost(){
        
    }

   public void populateView(){
       fruits = fruitBean.getAllFruits();
   }
   
   public void fruitSelected(){
       System.out.println("inside fruitSelected");
       String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("fruit_id");
       System.out.println("id:"+id);
       
       fruits.forEach(f -> { 
           if (String.valueOf(f.getId()).equals(id))
                   setSelectedFruit(f);
           });
      System.out.println("name:"+selectedFruit.getName());
      
       Map<String,Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
        
        RequestContext.getCurrentInstance().openDialog("enterQuantity", options, null);
   }
   
   public void addToCart(){
       System.out.println("matrix.ecommerce.web.ShoppingView.addToCart()");
       RequestContext.getCurrentInstance().closeDialog(null);
       storeSelectedFruit(selectedFruit);
       storeSelectedQuantity(newQuantity);
       calculateCost();
                
   }
   
   public void fruitAddedTocart(){
              
   }
    
}
