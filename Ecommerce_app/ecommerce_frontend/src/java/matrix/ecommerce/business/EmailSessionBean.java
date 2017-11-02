/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matrix.ecommerce.business;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Gautam
 *
 */

@Named
@RequestScoped
public class EmailSessionBean {
    
    @Resource (lookup="mail/shoppingcart")
    private Session toGmail; 
    
    @EJB CustomerBean cutomerBean;
    
    public void sendEmail() throws Exception {
        
        toGmail.setDebug(true);
        System.out.println(">> after add");
        Message message = new MimeMessage(toGmail);
        message.setSubject("Hi This is a test message");
        message.setRecipient(Message.RecipientType.TO , new InternetAddress("cgkrshnn@gmail.com","Gautam"));
        Multipart body = new MimeMultipart(); 
        BodyPart part = new MimeBodyPart();
        part.setText("Thank you for your order");
        body.addBodyPart(part); 
        message.setContent(body);
        Transport transport = toGmail.getTransport();
        transport.connect();
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
        
    }
}
