package bank.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import bank.model.History;

/**
 * Session Bean implementation class ClientDao
 */
@Stateless
@LocalBean
public class HistoryDao extends AbstractDao<History, Long>{

	@PersistenceContext
	private EntityManager em;

	public HistoryDao() {
		super(History.class);
	}

	@Override
	public EntityManager em() {
		return em;
	}
	
}
