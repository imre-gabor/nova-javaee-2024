package hu.bme.aait;

import java.util.List;

import hu.bme.aait.Orders.Status;
import hu.bme.aait.vicc_air.TicketOrderBean;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class OrderHandlerBean {
	
	@PersistenceContext
	private EntityManager em;
	
	@Inject
	JMSContext jmsContext;
	
	@Resource(lookup = "java:global/jms/ViccAirRequest")
	Queue viccAirRequestQ;
	

	public void createNewOrder(Orders orderData) {
		orderData.setStatus(Status.PENDING);
		em.persist(orderData);
		
		
		jmsContext.createProducer().send(
				viccAirRequestQ, 
				new TicketOrderBean(orderData.getOrderId(), orderData.getCustomername(), 
						orderData.getFlightId(), orderData.getDepart(), orderData.getSeats()));
	}

	public List<Orders> getOrders() {		
		return em.createQuery("SELECT o FROM Orders o", Orders.class)
				.getResultList();
	}

	public void updateOrderStatus(int orderId, Status status) {
		em.find(Orders.class, orderId).setStatus(status);
	}
	
	public void cancelFlight(String flightId) {
		em.createNamedQuery("Orders.findByFlightId", Orders.class)
		.setParameter("flightId", flightId)
		.getResultList()
		.forEach(o -> o.setStatus(Status.CANCELLED));
	}
}
