package hu.bme.aait;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import hu.bme.aait.Orders.Status;
import hu.bme.aait.vicc_air.TicketOrderBean;

/**
 * Message-Driven Bean implementation class for: OrderResponseHanlderBean
 */
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destination", propertyValue = "java:global/jms/ViccAirResponse"), @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Queue")
		}, 
		mappedName = "java:global/jms/ViccAirResponse")
public class OrderResponseHandlerBean implements MessageListener {

	@EJB
	OrderHandlerBean orderHandlerBean;
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
    	try {
			TicketOrderBean tob = (TicketOrderBean) ((ObjectMessage)message).getObject();
			orderHandlerBean.updateOrderStatus(tob.getOrderId(), convert(tob.getStatus()));
		} catch (JMSException e) {
			e.printStackTrace();
		}
    }
    
    public Status convert(hu.bme.aait.vicc_air.TicketOrderBean.Status status) {
    	return Status.valueOf(status.toString());
    }

}
