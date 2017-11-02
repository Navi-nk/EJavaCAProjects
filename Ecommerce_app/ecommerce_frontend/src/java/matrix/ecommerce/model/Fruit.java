package matrix.ecommerce.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Sarita
 */
@Entity
@Table(name = "fruit")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fruit.findAll", query = "SELECT f FROM Fruit f")
    , @NamedQuery(name = "Fruit.findById", query = "SELECT f FROM Fruit f WHERE f.id = :id")
    , @NamedQuery(name = "Fruit.findByName", query = "SELECT f FROM Fruit f WHERE f.name = :name")
    , @NamedQuery(name = "Fruit.findByPrice", query = "SELECT f FROM Fruit f WHERE f.price = :price")
    , @NamedQuery(name = "Fruit.findByDescription", query = "SELECT f FROM Fruit f WHERE f.description = :description")
    , @NamedQuery(name = "Fruit.findByImageUri", query = "SELECT f FROM Fruit f WHERE f.imageUri = :imageUri")})
public class Fruit implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "price")
    private float price;
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "image_uri")
    private String imageUri;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fruitId")
    private Collection<ShoppingCartItem> shoppingCartItemsCollection;

    public Fruit() {
    }

    public Fruit(Integer id) {
        this.id = id;
    }

    public Fruit(Integer id, String name, float price, String imageUri) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUri = imageUri;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    @XmlTransient
    public Collection<ShoppingCartItem> getShoppingCartItemsCollection() {
        return shoppingCartItemsCollection;
    }

    public void setShoppingCartItemsCollection(Collection<ShoppingCartItem> shoppingCartItemsCollection) {
        this.shoppingCartItemsCollection = shoppingCartItemsCollection;
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
        if (!(object instanceof Fruit)) {
            return false;
        }
        Fruit other = (Fruit) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "matrix.ecommerce.model.Fruit[ id=" + id + " ]";
    }
    
}
