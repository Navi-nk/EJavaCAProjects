/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matrix.ecommerce.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Sarita
 */
@Entity
@Table(name = "shopping_cart_items")
public class ShoppingCartItem implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private Long id;
    //protected ShoppingCartPK shoppingCartPK;
    
    @Column(name = "cost")
    private Float cost;
    @Column(name = "selected_quantity")
    private Integer selectedQuantity;
    
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")	
    private Order order;
    
    @ManyToOne
    @JoinColumn(name = "fruit_id", referencedColumnName = "id")	
    private Fruit fruit;
    
    public ShoppingCartItem() {
    }

    public ShoppingCartItem(Float cost, Integer selectedQuantity) {
        this.cost = cost;
        this.selectedQuantity = selectedQuantity;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    

   /* public ShoppingCartItem(ShoppingCartPK shoppingCartPK) {
        this.shoppingCartPK = shoppingCartPK;
    }
    public ShoppingCartItem(int fruitId, int shoppingcartId) {
        this.shoppingCartPK = new ShoppingCartPK(fruitId, shoppingcartId);
    }

    public ShoppingCartPK getShoppingCartPK() {
        return shoppingCartPK;
    }
    public void setShoppingCartPK(ShoppingCartPK shoppingCartPK) {
        this.shoppingCartPK = shoppingCartPK;
    }*/

    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }

    public Fruit getFruit() {
        return fruit;
    }
    public void setFruit(Fruit fruit) {
        this.fruit = fruit;
    }

    public Float getCost() {
        return cost;
    }
    public void setCost(Float cost) {
        this.cost = cost;
    }

    public Integer getSelectedQuantity() {
        return selectedQuantity;
    }
    public void setSelectedQuantity(Integer selectedQuantity) {
        this.selectedQuantity = selectedQuantity;
    }
    
    

  /*  @Override
    public int hashCode() {
        int hash = 0;
        hash += (shoppingCartPK != null ? shoppingCartPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ShoppingCartItem)) {
            return false;
        }
        ShoppingCartItem other = (ShoppingCartItem) object;
        if ((this.shoppingCartPK == null && other.shoppingCartPK != null) || (this.shoppingCartPK != null && !this.shoppingCartPK.equals(other.shoppingCartPK))) {
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        return "matrix.ecommerce.business.FruitCart[ fruitCartPK=" + shoppingCartPK + " ]";
    }
*/
    public void increaseQuantity(Integer selectedQuantity) {
        this.selectedQuantity += selectedQuantity;
    }
    
}
