package matrix.snapnshare.rest;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Navi-PC
 */
@RequestScoped
@Path("/timeline")
public class Timeline {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public void retrieveTimeLine(@PathParam("username") String name) {
        
        
    
    
    }
    
}
