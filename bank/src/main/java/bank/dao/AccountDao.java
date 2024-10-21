package bank.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import bank.model.Account;
import bank.model.Account_;
import bank.model.Client;
import bank.model.Client_;

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
	
	public List<Account> findByExample(Account example) {
		Integer accountid = example.getAccountid();
		double balance = example.getBalance();
		Date createdate = example.getCreatedate();
		Integer clientid = null;
		String clientName = null;
		
		Client client = example.getClient();		
		if(client != null) {
			clientid = client.getClientid();
			clientName = client.getName();
		}
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Account> cq = cb.createQuery(Account.class);
		Root<Account> root = cq.from(Account.class);
		cq.select(root);
		
		List<Predicate> predicates = new ArrayList<>();
		if(accountid != null) {
			predicates.add(cb.equal(root.get(Account_.accountid), accountid));
		}
		
		if(balance > 0.0) {
			predicates.add(cb.between(root.get(Account_.balance), balance*0.9, balance*1.1));
		}
		
		if(createdate != null) {
			predicates.add(cb.equal(root.get(Account_.createdate), 
					Date.from(
							createdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
							.atStartOfDay(ZoneId.systemDefault()).toInstant())));
		}
		if(clientid != null) {
			predicates.add(cb.equal(root.get(Account_.client).get(Client_.clientid), clientid));
		}
		if(clientName != null && clientName.length() != 0) {
			predicates.add(cb.like(root.get(Account_.client).get(Client_.name), clientName + "%"));
		}
		
		cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
		return em.createQuery(cq).getResultList();
	}
	
}
