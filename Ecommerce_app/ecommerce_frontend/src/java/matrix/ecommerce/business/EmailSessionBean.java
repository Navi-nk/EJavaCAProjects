/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matrix.ecommerce.business;

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
    
    
    public void sendEmail(Customer c) throws Exception {
        
        try {
            toCustomer = c;
            toGmail.setDebug(true);
            System.out.println(">> after add");
            Message message = new MimeMessage(toGmail);
            message.setSubject("Hi This is a test message");
            message.setRecipient(Message.RecipientType.TO , new InternetAddress(toCustomer.getEmail(),"Gautam"));
            Multipart body = new MimeMultipart(); 
            BodyPart part = new MimeBodyPart();
            part.setText("Thank you for your order");
            body.addBodyPart(part); 
            message.setContent(body);
            Transport transport = toGmail.getTransport();
            transport.connect();
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();      
        } catch (Exception ex) {
            
            ex.printStackTrace();
        }
    }
}
