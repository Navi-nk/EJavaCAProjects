/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matrix.ecommerce.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Sarita
 */
@Embeddable
public class ShoppingCartPK implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Basic(optional = false)
    @Column(name = "fruit_id")
    private int fruitId;
    @Basic(optional = false)
    @Column(name = "order_id")
    private int orderId;

    public ShoppingCartPK() {
    }

    public ShoppingCartPK(int fruitId, int orderId) {
        this.fruitId = fruitId;
        this.orderId = orderId;
    }

    public int getFruitId() {
        return fruitId;
    }
    public void setFruitId(int fruitId) {
        this.fruitId = fruitId;
    }

    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) fruitId;
        hash += (int) orderId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ShoppingCartPK)) {
            return false;
        }
        ShoppingCartPK other = (ShoppingCartPK) object;
        if (this.fruitId != other.fruitId) {
            return false;
        }
        if (this.orderId != other.orderId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "matrix.ecommerce.business.FruitCartPK[ fruitId=" + fruitId + ", shoppingcartId=" + orderId + " ]";
    }
    
}
