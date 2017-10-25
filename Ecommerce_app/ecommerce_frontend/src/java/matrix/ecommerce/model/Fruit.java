package matrix.ecommerce.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Sarita
 */
@Entity
@Table(name = "fruit")
public class Fruit implements Serializable {

    @Id
    private Long id;
    private String name;
    private String description;
    private Float price;
    private String image_uri;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    
     public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }
    public void setPrice(Float price) {
        this.price = price;
    }

    public String getImage_uri() {
        return image_uri;
    }
    public void setImage_uri(String image_uri) {
        this.image_uri = image_uri;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }  
}
