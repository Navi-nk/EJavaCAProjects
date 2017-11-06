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
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author Gautam
 */

@MultipartConfig
public class UploadServlet extends HttpServlet {

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
        
        response.setContentType("text/html;charset=UTF-8");
        try {
            
            
            String userName = getUserNameFromRequest(request);
            Part p1 = request.getPart("file");
            InputStream is = p1.getInputStream();
        
            String filePath = getServletContext().getInitParameter("file-upload");  // get path on the server
            FileOutputStream os = new FileOutputStream (filePath);
     
       
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UploadServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UploadServlet at !" + request.getContextPath() + "</h1>");
            out.println("<h1>Request Username : " + userName + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
        catch(Exception ex) {
           out.println("Exception -->" + ex.getMessage());
        }
        finally { 
           out.close();
        }
    }
    
    public static String getUserNameFromRequest(HttpServletRequest request)
    {
        StringBuffer requestURL = request.getRequestURL();
        String info = request.getPathInfo();
        info = info.replace("/", "");
        
        if (info == null)
            return "NULL";

        //return requestURL.append('?').append(queryString).toString();
        return info;
    }
    
    private byte[] readPart(Part p) throws IOException 
    { 
        byte[] buffer = new byte[1024 * 8];
        int sz = 0; try (InputStream is = p.getInputStream()) 
        { 
            BufferedInputStream bis = new BufferedInputStream(is);
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) 
            {
                while (-1 != (sz = bis.read(buffer))) baos.write(buffer, 0, sz);
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
