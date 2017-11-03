package matrix.ecommerce.web;

import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import matrix.ecommerce.business.FruitBean;
import matrix.ecommerce.model.Fruit;
import matrix.ecommerce.model.ShoppingCartItem;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Navi
 */
@SessionScoped
@Named
public class ShoppingView implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private FruitBean fruitBean;

    private List<Fruit> fruits;
    private List<ShoppingCartItem> shoppingCartItems = new LinkedList<>();
    private ShoppingCartItem cartItem;
    private Integer newQuantity;
    private Fruit selectedFruit;
    private Float fullAmount = new Float(0);

    public ShoppingView() {
        this.cartItem = new ShoppingCartItem(Float.valueOf(0), 0);
    }

    @PostConstruct
    private void init() {
        populateView();
        System.out.println(">> Creating ShoppingView");
    }

    @PreDestroy
    private void destroy() {
        System.out.println(">> Destroying ShoppingView");
    } 

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

    public Float getFullAmount() {
        return fullAmount;
    }

    public void setFullAmount(Float fullAmount) {
        this.fullAmount = fullAmount;
    }

    public void storeSelectedFruit() {
        for (ShoppingCartItem cItem : shoppingCartItems) {
            
            if ((cItem.getFruit()).equals(selectedFruit)) {
                cartItem = cItem;
                return;
            }
        }
        
        cartItem.setFruit(selectedFruit);
        shoppingCartItems.add(cartItem);
    }

    public void storeSelectedQuantity() {
        cartItem.setSelectedQuantity(newQuantity);
    }

    public void calculateCost() {
        cartItem.setCost(
                (cartItem.getSelectedQuantity() * selectedFruit.getPrice())
        );
    }

    public void calculateFullAmmount() {
        shoppingCartItems.forEach(item -> {
            fullAmount += item.getCost();
        });
    }

    public void populateView() {
        fruits = fruitBean.getAllFruits();
    }

    public void fruitSelected() {
        String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("fruit_id");

        fruits.forEach(fruit -> {
            if (String.valueOf(fruit.getId()).equals(id)) {
                setSelectedFruit(fruit);
            }
        });
        Map<String, Object> options = new HashMap<>();
        options.put("resizable", false);
        options.put("contentHeight", 300);
        options.put("height", 150);
        options.put("width", 650);
        options.put("modal", true);
        RequestContext.getCurrentInstance().openDialog("enterQuantity", options, null);
    }

    public void removeItem() {

        String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selected_fruit_id");

        for (Fruit f : fruits) {
            if (String.valueOf(f.getId()).equals(id)) {
                for (Iterator<ShoppingCartItem> iter = shoppingCartItems.listIterator(); iter.hasNext(); ) { 
                    ShoppingCartItem item = iter.next();
                    if ((item.getFruit()).equals(f)) {
                        iter.remove();
                        return;
                    }
                }
            }
        }
    }

    public void closeDialog() {
        RequestContext.getCurrentInstance().closeDialog(null);
    }

    public void fruitAddedTocart() {
        storeSelectedFruit();
        storeSelectedQuantity();
        calculateCost();
        cartItem = new ShoppingCartItem(Float.valueOf(0), 0);
        newQuantity = null;
        selectedFruit = null;
    }

    public String checkOut() {
        return ("checkout?faces-redirect=true");
    }
}
