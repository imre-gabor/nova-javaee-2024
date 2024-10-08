package bank.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import bank.model.Account;

/**
 * Session Bean implementation class ClientDao
 */
@Stateless
@LocalBean
public class AccountDao extends AbstractDao<Account, Integer>{

	@PersistenceContext
	private EntityManager em;

	public AccountDao() {
		super(Account.class);
	}

	@Override
	public EntityManager em() {
		return em;
	}
	
}
