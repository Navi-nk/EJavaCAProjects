package matrix.snapnshare.rest;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import matrix.snapshot.business.UserBean;

/**
 *
 * @author Navi-PC
 */
@RequestScoped
@Path("/register")
public class RegisterUser {
    @EJB 
    private UserBean userBean;
    @POST
    @Path("{username}")
    public Response resgister(@PathParam("username") String name) {
            
        try{
            userBean.addUser(name);
            return (Response.ok().build());
        }
        catch(Exception ex){
            ex.printStackTrace();
            return (Response.status(Response.Status.FORBIDDEN).entity("user exists").build());
            
        }
    }
}
