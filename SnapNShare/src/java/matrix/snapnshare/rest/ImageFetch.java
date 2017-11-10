/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matrix.snapnshare.rest;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

/**
 *
 * @author Navi-PC
 */
@RequestScoped
@Path("/imagestore")
public class ImageFetch {

    @GET
    @Path("{name}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response retrieveTimeLine(@PathParam("name") String filename,@Context ServletContext servletContext) {
        
        String filePath = servletContext.getRealPath("/../../imagestore");
        filePath = filePath+ "/" + filename;
        File imageFile = new File(filePath);
        BufferedImage image;
        byte[] imageInByte;
        System.out.println(filePath);
        if (imageFile.exists()) {
            try {
                System.out.println("matrix.snapnshare.rest.ImageFetch.retrieveTimeLine()");
                image = ImageIO.read(imageFile);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write( image, "jpg", baos );
                baos.flush();
                imageInByte = baos.toByteArray();
                baos.close();
            } catch (IOException ex) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            //jsonArray.add("");
            return Response.status(Response.Status.ACCEPTED).header("Cache-Control", "max-age=-1").entity(new StreamingOutput() {
                @Override
                public void write(OutputStream output) {
                    try {
                        output.write(imageInByte);
                    } catch (IOException ex) {
                        Logger.getLogger(ImageFetch.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }).build();
        }
        
        return Response.status(Response.Status.NOT_FOUND).build();
    }

}
