/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matrix.warehouse.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;

/**
 *
 * @author Navi-PC
 */
@Embeddable
@Table(name = "cart_items")
public class CartItem implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "item_name")
    private String itemName;
    @Column(name = "quantity")
    private Integer quantity;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
