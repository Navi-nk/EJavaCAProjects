/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matrix.ecommerce.model;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sarita
 */
@Entity
@Table(name = "fruit_cart")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FruitCart.findAll", query = "SELECT f FROM FruitCart f")
    , @NamedQuery(name = "FruitCart.findByFruitId", query = "SELECT f FROM FruitCart f WHERE f.fruitCartPK.fruitId = :fruitId")
    , @NamedQuery(name = "FruitCart.findByShoppingcartId", query = "SELECT f FROM FruitCart f WHERE f.fruitCartPK.shoppingcartId = :shoppingcartId")})
public class FruitCart implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FruitCartPK fruitCartPK;

    public FruitCart() {
    }

    public FruitCart(FruitCartPK fruitCartPK) {
        this.fruitCartPK = fruitCartPK;
    }

    public FruitCart(int fruitId, int shoppingcartId) {
        this.fruitCartPK = new FruitCartPK(fruitId, shoppingcartId);
    }

    public FruitCartPK getFruitCartPK() {
        return fruitCartPK;
    }

    public void setFruitCartPK(FruitCartPK fruitCartPK) {
        this.fruitCartPK = fruitCartPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fruitCartPK != null ? fruitCartPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FruitCart)) {
            return false;
        }
        FruitCart other = (FruitCart) object;
        if ((this.fruitCartPK == null && other.fruitCartPK != null) || (this.fruitCartPK != null && !this.fruitCartPK.equals(other.fruitCartPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "matrix.ecommerce.business.FruitCart[ fruitCartPK=" + fruitCartPK + " ]";
    }
    
}
