package hu.bme.aait;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Message-Driven Bean implementation class for: DeleteFlightHandlerBean
 */
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destination", propertyValue = "java:global/jms/ViccAirDFlights"), @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Topic")
		}, 
		mappedName = "java:global/jms/ViccAirDFlights")
public class DeleteFlightHandlerBean implements MessageListener {

	@EJB
	OrderHandlerBean orderHandlerBean;
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
    	try {
			orderHandlerBean.cancelFlight(((TextMessage)message).getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
    }

}
