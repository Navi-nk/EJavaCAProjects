/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matrix.warehouse.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Navi-PC
 */
@Entity
@Table(name = "orders")
public class Order implements Serializable{

    @Id @GeneratedValue(strategy=IDENTITY)
    private Long id;
    @Column(name = "cust_name")
    private String customerName;
    @Column(name = "comment")
    private String comment;
    @Column(name = "address")
    private String address;
    
    @ElementCollection
    @CollectionTable(
            name="cart_items",
    joinColumns=@JoinColumn(name="orders_id")
    )
    private List<CartItem> cartItems;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    
    public List<CartItem> getCartItems() {
        return cartItems;
    }
    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
    
    public JsonObject toJson(){
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("name", customerName)
                .add("address", address)
                .add("comment", comment);
        
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        cartItems.forEach( item -> {
            JsonObjectBuilder b = Json.createObjectBuilder();
            b.add("item", item.getItemName())
                    .add("quantity",item.getQuantity());
            arrayBuilder.add(b);
        });
        JsonObject obj = builder.add("cart", arrayBuilder).build();
        System.out.println(obj.toString());
        return obj;
    }
             
}
