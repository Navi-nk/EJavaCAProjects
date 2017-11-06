
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
    @Path("{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public void retrieveFriends(@PathParam("username") String name) {
    
        System.out.println(name);
    }
    
    @POST
    @Path("{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public void addFriend(@PathParam("username") String name) {
    
    
    }
}
