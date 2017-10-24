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
public class FruitCartPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "fruit_id")
    private int fruitId;
    @Basic(optional = false)
    @Column(name = "shoppingcart_id")
    private int shoppingcartId;

    public FruitCartPK() {
    }

    public FruitCartPK(int fruitId, int shoppingcartId) {
        this.fruitId = fruitId;
        this.shoppingcartId = shoppingcartId;
    }

    public int getFruitId() {
        return fruitId;
    }

    public void setFruitId(int fruitId) {
        this.fruitId = fruitId;
    }

    public int getShoppingcartId() {
        return shoppingcartId;
    }

    public void setShoppingcartId(int shoppingcartId) {
        this.shoppingcartId = shoppingcartId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) fruitId;
        hash += (int) shoppingcartId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FruitCartPK)) {
            return false;
        }
        FruitCartPK other = (FruitCartPK) object;
        if (this.fruitId != other.fruitId) {
            return false;
        }
        if (this.shoppingcartId != other.shoppingcartId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "matrix.ecommerce.business.FruitCartPK[ fruitId=" + fruitId + ", shoppingcartId=" + shoppingcartId + " ]";
    }
    
}
