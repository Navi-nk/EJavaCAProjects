package matrix.ecommerce.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sarita
 */
@Entity
@Table(name = "shopping_cart_items")
@XmlRootElement

@NamedQueries({
    @NamedQuery(name = "ShoppingCartItem.findAll", query = "SELECT s FROM ShoppingCartItem s")
    , @NamedQuery(name = "ShoppingCartItem.findById", query = "SELECT s FROM ShoppingCartItem s WHERE s.id = :id")
    , @NamedQuery(name = "ShoppingCartItem.findBySelectedQuantity", query = "SELECT s FROM ShoppingCartItem s WHERE s.selectedQuantity = :selectedQuantity")
    , @NamedQuery(name = "ShoppingCartItem.findByCost", query = "SELECT s FROM ShoppingCartItem s WHERE s.cost = :cost")})
public class ShoppingCartItem implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "selected_quantity")
    private int selectedQuantity;
    
    @Basic(optional = false)
    @Column(name = "cost")
    private float cost;
    
    @JoinColumn(name = "fruit_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Fruit fruitId;
    
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Order orderId;

    public ShoppingCartItem() {
    }

    public ShoppingCartItem(Integer id) {
        this.id = id;
    }

    public ShoppingCartItem(Integer id, int selectedQuantity, float cost) {
        this.id = id;
        this.selectedQuantity = selectedQuantity;
        this.cost = cost;
    }
    
     public ShoppingCartItem(Float cost, Integer selectedQuantity) {
        this.cost = cost;
        this.selectedQuantity = selectedQuantity;

    }

   

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getSelectedQuantity() {
        return selectedQuantity;
    }

    public void setSelectedQuantity(int selectedQuantity) {
        this.selectedQuantity += selectedQuantity;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public Fruit getFruitId() {
        return fruitId;
    }

    public void setFruitId(Fruit fruitId) {
        this.fruitId = fruitId;
    }

    public Order getOrderId() {
        return orderId;
    }

    public void setOrderId(Order orderId) {
        this.orderId = orderId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ShoppingCartItem)) {
            return false;
        }
        ShoppingCartItem other = (ShoppingCartItem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "matrix.ecommerce.model.ShoppingCartItem[ id=" + id + " ]";
    }
    
}
