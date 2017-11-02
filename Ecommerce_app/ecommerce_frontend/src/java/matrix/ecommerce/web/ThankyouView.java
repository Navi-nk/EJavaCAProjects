
package matrix.ecommerce.web;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import matrix.ecommerce.model.Customer;

/**
 *
 * @author Navi-PC
 */
@ViewScoped
@Named
public class ThankyouView implements Serializable {

   private static final long serialVersionUID = 1L;
    
   private Customer customer = new Customer();

    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
   
   @PostConstruct
    private void init() {
        customer = (Customer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("customer");
        System.out.println(">> Creating ThankyouView");
    }

    @PreDestroy
    private void destroy() {
        System.out.println(">> Destroying ThankyouView Details");
    } 
    
    public String endSession() {
        HttpSession sess = (HttpSession) FacesContext.getCurrentInstance().
                getExternalContext().getSession(false);
        sess.invalidate();
        return ("welcome?faces-redirect=true");
    }
}
