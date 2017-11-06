
package matrix.snapnshare.rest;


import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonReader;
import javax.ws.rs.Consumes;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import matrix.snapshare.model.User;

import matrix.snapshot.business.UserBean;

/**
 *
 * @author Navi-PC
 */

@RequestScoped
@Path("/friends")
public class Friends {

    @EJB 
    private UserBean userBean;

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
             //  friends= userBean.getFriends(name);
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
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addFriend(@PathParam("username") String name, String userName) {
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        
        JsonReader jsonReader = Json.createReader(new StringReader(userName));
        JsonArray object = jsonReader.readArray();
        jsonReader.close();
        
        String friendName = object.getString(0);
        try{
            User user = userBean.findUser(name);
            User friendUser = userBean.findUser(friendName);
            if(friendUser != null){
                userBean.addFriend(friendName, user, friendUser);    
            }
            else{
                System.out.println(name + "+" + userName);
                arrBuilder.add("user not found");
                return Response.status(Response.Status.NOT_FOUND).entity(arrBuilder.build()).build();
            }
        }
        catch(Exception ex){
            arrBuilder.add("Friend already added");
            return Response.status(Response.Status.FORBIDDEN).entity(arrBuilder.build()).build();
        }
       return null;
    }
}
