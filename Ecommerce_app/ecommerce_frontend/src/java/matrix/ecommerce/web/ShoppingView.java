package matrix.ecommerce.web;

import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import matrix.ecommerce.business.FruitBean;
import matrix.ecommerce.model.Fruit;
import org.primefaces.context.RequestContext;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sarita
 */
@SessionScoped
@Named
public class ShoppingView implements Serializable{
    @EJB private FruitBean fruitBean;
    
    
    private List<Fruit> fruits;

    @PostConstruct
    private void init() {
    populateView(); }

    public List<Fruit> getFruits() {
        return fruits;
    }
    public void setFruits(List<Fruit> fruits) {
        this.fruits = fruits;
    }

   public void populateView(){
       fruits = fruitBean.getAllFruits();
   }
    
}
