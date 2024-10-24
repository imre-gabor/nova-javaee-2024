package bank.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import bank.model.Client;

/**
 * Session Bean implementation class ClientDao
 */
@Stateless
@LocalBean
public class ClientDao extends AbstractDao<Client, Integer>{

	@PersistenceContext
	private EntityManager em;

	public ClientDao() {
		super(Client.class);
	}

	@Override
	public EntityManager em() {
		return em;
	}
	
	public List<Client> findAllWithAccounts(){
		return em.createQuery("SELECT c FROM Client c LEFT JOIN FETCH c.accounts", Client.class).getResultList();
	}
	
	public List<Client> findAllWithAccounts2() {
		EntityGraph<?> eg = em.getEntityGraph("Client.withAccounts");
		return em.createQuery("SELECT c FROM Client c", Client.class).setHint("javax.persistence.loadgraph", eg).getResultList();
	}
	
//	
//	public Client findById(Integer id) {
//		return em.find(Client.class, id);
//	}
//	
//	public void create(Client client) {
//		em.persist(client);
//	}
//	
//	public void update(Client client) {
//		em.merge(client);
//	}
//	
//	public void delete(Client client) {
//		em.remove(em.merge(client));
//	}
//	
//	public List<Client> findAll() {
//		return em.createNamedQuery("Client.findAll", Client.class).getResultList();
//	}
}
