/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matrix.ecommerce.business;

import java.util.List;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import matrix.ecommerce.model.Customer;
import matrix.ecommerce.model.Order;
import matrix.ecommerce.model.ShoppingCartItem;

/**
 *
 * @author Gautam
 *
 */

@Stateless
public class EmailSessionBean {
    
    @Resource (lookup="mail/shoppingcart")
    private Session toGmail; 
    private Customer toCustomer;
    private List<ShoppingCartItem> currentCart;
    private Order newOrder;
        
    public void sendEmail(Customer c, List<ShoppingCartItem> shoppingCart, Order order) throws Exception {
        
        try {
            toCustomer = c;
            currentCart = shoppingCart;
            newOrder = order;
            toGmail.setDebug(true);
            System.out.println(">> after add");
            Message message = new MimeMessage(toGmail);
            message.setSubject("FruitMart Order Summary for Order No: " + newOrder.getId() );
            message.setRecipient(Message.RecipientType.TO , new InternetAddress(toCustomer.getEmail(),toCustomer.getName()));
            
            StringBuilder content = new StringBuilder();
            content.append( 
                        "<head>" +
                        "<style type=\"text/css\">" +
                        "  .blue { color: #00F; }" +
                        "  table.GeneratedTable { width: 100%; background-color: #ffffff; border-collapse: collapse; border-width: 3px; border-color: #ffcc00; border-style: solid; color: #000000;}" +
                        "  table.GeneratedTable td, table.GeneratedTable th { border-width: 3px; border-color: #ffcc00; border-style: solid; padding: 3px;}" +
                        "  table.GeneratedTable thead { background-color: #ffcc00;}" +    
                        "</style>" +
                        "</head>" +
                        "<h2 class=\"blue\">" + "Order Summary for OrderNo " + newOrder.getId() + " dated " + newOrder.getCreatedDate() + "</h2>" +
                        "<p>" + "Dear <em>" + toCustomer.getName() + "</em>," + "</p>" +
                        "<p>Thanks for Shopping with us. Please find below a summary of your order.</p>" +
                        "<table class=\"GeneratedTable\">" +
                        "<tr align=\"center\" bgcolor=\"#F77F4E\" multiple><th>" + "Order Details" + "</th></tr>" +
                        "<tr>" + "<p>Name: " + toCustomer.getName() + "</p>" +
                                 "<p>Address: " + toCustomer.getAddress() + "</p>" +
                                 "<p>Contact: " + toCustomer.getPhone() + "</p>" +
                                 "<p>Email: " + toCustomer.getEmail() + "</p>" +
                                 "<p>Comments: " + newOrder.getComments() + "</p>" + "</tr>" +
                        "<tr><table class=\"GeneratedTable\">" +
                        "<tr align=\"center\" bgcolor=\"#EBEBE9\" multiple><th>Name</th><th>Price</th><th>Quantity</th><th>Cost</th></tr>");
            
            for (ShoppingCartItem item : currentCart) {
                
                content.append("<tr>");
                content.append("<td>" + item.getFruit().getName() + "</td>");
                content.append("<td>" + item.getFruit().getPrice() + "</td>");
                content.append("<td>" + item.getSelectedQuantity() + "</td>");
                content.append("<td>" + item.getCost() + "</td>");
                content.append("</tr>");
                
            }
            
            content.append(
                        "</table></tr>" +
                        "<tr align=\"right\"> Total Amount: " + order.getTotalCost() + "SGD</tr>" +
                        "</table>" +
                        "<p>Your Order shall be dispatched shortly</p>" +        
                        "<p><strong>Thanks & Regards</strong></p>" +
                        "<p><strong>TeamMatrix@FruitMart</strong></p>");
            message.setContent(content.toString(), "text/html; charset=utf-8");
            
            Transport transport = toGmail.getTransport();
            transport.connect();
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();      
        } catch (Exception ex) {
            
            ex.printStackTrace();
        }
    }
}
