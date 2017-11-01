/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matrix.ecommerce.business;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

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
    @Override
	public void onMessage(Message message) {
		TextMessage txtMsg = (TextMessage)message;
		System.out.println("... received");
		try {
			System.out.println("\t" + txtMsg.getText());
		} catch (JMSException ex) {
			ex.printStackTrace();
		}
	}
}
