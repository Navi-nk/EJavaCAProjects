/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matrix.warehouse.business;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import matrix.warehouse.model.Order;

/**
 *
 * @author Gautam
 */

@MessageDriven(mappedName = "jms/warehouse",
		activationConfig = {
			@ActivationConfigProperty(
					propertyName = "destinationType",
					propertyValue = "javax.jms.Queue"
			)
		}
)
public class WarehouseBean implements MessageListener { 
    @Inject
    private Event<String> jmsEvent;
    
    @EJB
    private OrderBean orderBean;
    
    @Override
	public void onMessage(Message message) {
		TextMessage txtMsg = (TextMessage)message;
		System.out.println("inside jms");
		try {
                        String msg = txtMsg.getText();
			System.out.println("\t" + msg);
                        jmsEvent.fire(txtMsg.getText());
                        orderBean.persistOrder(msg);
                        
		} catch (JMSException ex) {
			ex.printStackTrace();
		}
	}
}
