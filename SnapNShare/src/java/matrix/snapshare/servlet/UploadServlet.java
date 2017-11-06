/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matrix.snapshare.servlet;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.UUID;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import matrix.snapshare.model.Image;
import static matrix.snapshare.model.Image_.user;
import matrix.snapshare.model.User;
import matrix.snapshot.business.TimeLineBean;
import matrix.snapshot.business.UserBean;

/**
 *
 * @author Gautam
 */
@MultipartConfig
public class UploadServlet extends HttpServlet {

    @EJB
    TimeLineBean bean;
    @EJB
    UserBean uBean;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        ServletContext context = getServletContext();

        System.out.println("Inside Servlet");

        response.setContentType("text/html;charset=UTF-8");
        try {

            String userName = getUserNameFromRequest(request);
            String userComment = new String(readPart(request.getPart("comment")));
            byte[] file = readPart(request.getPart("image"));
            String imageName = (UUID.randomUUID()).toString();
            imageName = imageName.substring(0, 7);
            System.out.println("UserName retrieved:" + userName);
            System.out.println("The comment is:" + userComment);

            String filePath = context.getRealPath("/../../web/resources/images"); // get path on the server  
            DateFormat dateFormat = new SimpleDateFormat("yy_MM_dd_HH-mm-ss");
            Date date = new Date();
            imageName = imageName + dateFormat.format(date)+".jpg";
            filePath = filePath.concat("/" + imageName );

            System.out.println("UserName: " + imageName);
            System.out.println("Comments: " + userComment);
            System.out.println("File Path: " + filePath);

            FileOutputStream os = new FileOutputStream(filePath);
            os.write(file);
            out.append("File Uploaded Successfully");
            os.close();

            Image i = new Image();

           
            String ip = Inet4Address.getLocalHost().getHostAddress();
            
            String newurl = "http://" + ip + ":8080/SnapNShare/resources/images/";
            newurl = newurl + imageName;
            
            User u = uBean.findUser(userName);
            i.setComments(userComment);
            i.setCraetedDate(date);
            i.setName(imageName);
            i.setImageUrl(newurl);
            i.setUser(u);
 
            bean.add(i);
            JsonObjectBuilder jsonObject = Json.createObjectBuilder();
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.setContentType("application/json");
            jsonObject.add("imageName", imageName);
            out.print(jsonObject);
            out.flush();

        } catch (Exception ex) {
            out.append("Exception -->" + ex.getMessage());
            System.out.println("Exception:" + ex.getMessage());
        } finally {
            out.close();
        }
    }

    public static String getUserNameFromRequest(HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        String info = request.getPathInfo();
        info = info.replace("/", "");

        if (info == null) {
            return "NULL";
        }

        //return requestURL.append('?').append(queryString).toString();
        return info;
    }

    private byte[] readPart(Part p) throws IOException {
        byte[] buffer = new byte[1024 * 8];
        int sz = 0;
        try (InputStream is = p.getInputStream()) {
            BufferedInputStream bis = new BufferedInputStream(is);
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                while (-1 != (sz = bis.read(buffer))) {
                    baos.write(buffer, 0, sz);
                }
                buffer = baos.toByteArray();
            }
        }
        return (buffer);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
