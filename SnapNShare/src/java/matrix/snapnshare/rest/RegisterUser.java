package matrix.snapnshare.rest;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
    @Produces(MediaType.APPLICATION_JSON)
    public Response resgister(@PathParam("username") String name) {
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        try{
            System.out.println(name);
            userBean.addUser(name);
            return (Response.ok(arrBuilder.add("success").build()).build());
        }
        catch(Exception ex){
            ex.printStackTrace();
            return (Response.status(Response.Status.FORBIDDEN).entity(arrBuilder.add("user exists").build()).build());   
        }
    }
}
