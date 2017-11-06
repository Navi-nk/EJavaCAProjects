package matrix.snapnshare.rest;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 *
 * @author Navi-PC
 */
@RequestScoped
@Path("/register")
public class RegisterUser {
    
    @POST
    public void resgister(@PathParam("username") String name) {
    
    
    }
}
