package matrix.ecommerce.web;

import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
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
            System.out.println(cItem.getFruitId().getName() + " " + selectedFruit.getName());
            if ((cItem.getFruitId()).equals(selectedFruit)) {
                cartItem = cItem;
                System.out.println("fruit found");
                return;
            }
        }
        System.out.println("new fruit added");
        cartItem.setFruitId(selectedFruit);
        shoppingCartItems.add(cartItem);
    }

    public void storeSelectedQuantity() {
        System.out.println("inside increase");
        cartItem.setSelectedQuantity(newQuantity);
        System.out.println(cartItem.getSelectedQuantity());

    }

    public void calculateCost() {
        System.out.println("inside cost");
        cartItem.setCost(
                (cartItem.getSelectedQuantity() * selectedFruit.getPrice())
        );
        System.out.println(cartItem.getCost());
    }

    public void calculateFullAmmount() {
        System.out.println("inside Full Amount");
        shoppingCartItems.forEach(item -> {
            fullAmount += item.getCost();
        });
        System.out.println(cartItem.getCost());
    }

    public void populateView() {
        fruits = fruitBean.getAllFruits();
    }

    public void fruitSelected() {
        System.out.println("inside fruitSelected");
        String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("fruit_id");
        System.out.println("id:" + id);

        fruits.forEach(fruit -> {
            if (String.valueOf(fruit.getId()).equals(id)) {
                setSelectedFruit(fruit);
            }
        });
        System.out.println("name:" + selectedFruit.getName());
        Map<String, Object> options = new HashMap<>();
        options.put("resizable", false);
        options.put("contentHeight", 300);
        options.put("height", 150);
        options.put("width", 650);
        options.put("modal", true);
        RequestContext.getCurrentInstance().openDialog("enterQuantity", options, null);
    }

    public void removeItem() {
        System.out.println("matrix.ecommerce.web.ShoppingView.removeItem()");
        String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selected_fruit_id");
        System.out.println("id:" + id);

        for (Fruit f : fruits) {
            if (String.valueOf(f.getId()).equals(id)) {
                for (Iterator<ShoppingCartItem> iter = shoppingCartItems.listIterator(); iter.hasNext(); ) { 
                    ShoppingCartItem item = iter.next();
                    System.out.println(item.getFruitId().getName());
                    System.out.println(f.getName());
                    if ((item.getFruitId()).equals(f)) {
                        iter.remove();
                        return;
                    }
                }
            }
        }
    }

    public void closeDialog() {
        System.out.println("matrix.ecommerce.web.ShoppingView.addToCart()");
        RequestContext.getCurrentInstance().closeDialog(null);
    }

    public void fruitAddedTocart() {
        storeSelectedFruit();
        storeSelectedQuantity();
        calculateCost();
        cartItem = new ShoppingCartItem(Float.valueOf(0), 0);
        newQuantity = null;
        selectedFruit = null;
        System.out.println("size of cart " + shoppingCartItems.size());
    }

    public String checkOut() {
        return ("checkout?faces-redirect=true");
    }
}
