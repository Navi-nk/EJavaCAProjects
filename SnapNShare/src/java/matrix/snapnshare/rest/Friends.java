
package matrix.snapnshare.rest;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Navi-PC
 */

@RequestScoped
@Path("/friends")
public class Friends {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public void retrieveFriends(@PathParam("username") String name) {
    
    
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public void addFriend(@PathParam("username") String name) {
    
    
    }
}
