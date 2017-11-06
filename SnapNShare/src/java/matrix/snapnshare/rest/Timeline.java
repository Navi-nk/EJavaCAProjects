package matrix.snapnshare.rest;

import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import matrix.snapshare.model.Image;
import matrix.snapshot.business.TimeLineBean;

/**
 *
 * @author Navi-PC
 */
@RequestScoped
@Path("/timeline")
public class Timeline {
    @EJB TimeLineBean timeLineBean;
    @GET
    @Path("{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveTimeLine(@PathParam("username") String name) {
       JsonArrayBuilder jsonArray=Json.createArrayBuilder();
       if(name!=null)
           {
               List<Image> imageList= timeLineBean.getImages(name);
               if(imageList!=null){
               for(Image image:imageList)
               {
                   JsonObjectBuilder imageBuilder=Json.createObjectBuilder();
                   imageBuilder.add("postedBy", image.getUser().getUserName());
                   imageBuilder.add("url", image.getImageUrl());
                   imageBuilder.add("comments", image.getComments());
                   imageBuilder.add("postTime", image.getCraetedDate().toString());
                   jsonArray.add(imageBuilder);
               }
               return  Response.ok(jsonArray.build()).build();
               }
           }
           jsonArray.add("No images found");
        return Response.status(Response.Status.FORBIDDEN).entity(jsonArray.build()).build();
    
    
    }
    
}

