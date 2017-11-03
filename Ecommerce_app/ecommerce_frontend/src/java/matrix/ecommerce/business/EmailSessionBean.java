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
            message.setSubject("Hi This is a test message");
            message.setRecipient(Message.RecipientType.TO , new InternetAddress(toCustomer.getEmail(),"Gautam"));
//            Multipart body = new MimeMultipart(); 
//            BodyPart part = new MimeBodyPart();
//            part.setText("Thank you for your order");
//            body.addBodyPart(part); 
//            message.setContent(body);

            String sb = "<head>" +
                        "<style type=\"text/css\">" +
                        "  .blue { color: #00F; }" +
                        "</style>" +
                        "</head>" +
                        "<h2 class=\"blue\">" + "Order Summary for OrderNo " + newOrder.getId() + " dated " + newOrder.getCreatedDate() + "</h2>" +
                        "<p>" + "Dear <em>" + toCustomer.getName() + "</em>," + "</p>" +
                        "<p>Thanks for Shopping with us. Please find below a summary of your order.</p>" +
                        "<p>Your Order shall be dispatched shortly</p>" +
                        "<p><strong>Thanks & Regards</strong></p>" +
                        "<p><strong>TeamMatrix@FruitMart</strong></p>";
            message.setContent(sb, "text/html; charset=utf-8");
            
            Transport transport = toGmail.getTransport();
            transport.connect();
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();      
        } catch (Exception ex) {
            
            ex.printStackTrace();
        }
    }
}
