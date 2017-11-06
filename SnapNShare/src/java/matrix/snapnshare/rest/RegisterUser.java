package matrix.snapnshare.rest;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author Navi-PC
 */
@RequestScoped
@Path("/register")
public class RegisterUser {
    
    @POST
    @Path("{username}")
    public Response resgister(@PathParam("username") String name) {
            
        try{
            System.out.println(name);
            return (Response.ok().build());
        }
        catch(Exception ex){
            ex.printStackTrace();
            return (Response.status(Response.Status.FORBIDDEN).entity("user exists").build());
            
        }
    }
}
