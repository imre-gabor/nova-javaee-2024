package bank.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import bank.model.Client;

/**
 * Session Bean implementation class ClientDao
 */
@Stateless
@LocalBean
public class ClientDao {

	@PersistenceContext
	private EntityManager em;
	
	public Client findById(Integer id) {
		return em.find(Client.class, id);
	}
	
	public void create(Client client) {
		em.persist(client);
	}
	
	public void update(Client client) {
		em.merge(client);
	}
	
	public void delete(Client client) {
		em.remove(em.merge(client));
	}
	
	public List<Client> findAll() {
		return em.createNamedQuery("Client.findAll", Client.class).getResultList();
	}
}
