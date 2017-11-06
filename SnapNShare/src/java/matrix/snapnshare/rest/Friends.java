
package matrix.snapnshare.rest;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import matrix.snapshot.business.UserBean;

/**
 *
 * @author Navi-PC
 */

@RequestScoped
@Path("/friends")
public class Friends {
    @EJB UserBean bean;
    
    @GET
    @Path("{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveFriends(@PathParam("username") String name) 
    {   
        
        List<String> friends = new ArrayList<>(); 
       // JsonObjectBuilder friendsBuilder=Json.createObjectBuilder();
        JsonArrayBuilder jsonArray=Json.createArrayBuilder();

        
           if(name!=null)
           {
               friends= bean.getFriends(name);
               for(String friend:friends)
               {
                   jsonArray.add(friend);
               }
              // friendsBuilder.add("friends", builder);
           }
           
        return  Response.ok(jsonArray.build()).build();
          
    }
                    
    
    @POST
    @Path("{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public void addFriend(@PathParam("username") String name) {
    
    
    }
}
