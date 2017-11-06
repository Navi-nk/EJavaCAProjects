
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
try{
        
           if(name!=null)
           {
               friends= userBean.getFriends(name);
               for(String friend:friends)
               {
                   jsonArray.add(friend);
               }
           }
           
        return  Response.ok(jsonArray.build()).build();
}catch(Exception ex){
            jsonArray.add("No friends found");
            return Response.status(Response.Status.FORBIDDEN).entity(jsonArray.build()).build();
        }
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
        
        List<String> friends = new ArrayList<>();
        friends= userBean.getFriends(name);
        int check = 0;
        String friendToInsert = null;
        for (int i =0 ; i<object.size(); i++ ){
            String friendName = object.getString(i);
            
            for(String s : friends){
                if(s.equals(friendName)){
                    check = 1;
                    break;
                }
            }
            
            if(check == 0){
                friendToInsert = friendName;
                break;
            }
        }
        
        try{
            User user = userBean.findUser(name);
            User friendUser = userBean.findUser(friendToInsert);
            if(friendUser != null){
                userBean.addFriend(friendToInsert, user, friendUser); 
                
                
                JsonArrayBuilder jsonArray=Json.createArrayBuilder();
        
         
               friends= userBean.getFriends(name);
               for(String friend:friends)
               {
                   jsonArray.add(friend);
               }
               return Response.ok(jsonArray.build()).build();
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
    }
}
