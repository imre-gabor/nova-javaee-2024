package bank.service;

import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bank.dao.AccountDao;
import bank.dao.ClientDao;
import bank.dao.HistoryDao;
import bank.exception.BankException;
import bank.model.Account;
import bank.model.Client;
import bank.model.History;

/**
 * Session Bean implementation class BankService
 */
@Stateless
public class BankService implements BankServiceLocal {

	@EJB
	private ClientDao clientDao;
	
	@EJB
	private AccountDao accountDao;
	
	@EJB
	private HistoryDao historyDao;
	
	@Resource
	private SessionContext ctx;

	@Override
	public void createClient(Client client) {
		clientDao.create(client);
	}
	
	
	@Override
	public void createAccountForClient(Account account, int clientId) throws BankException {
		Client client = clientDao.findById(clientId);
		if(client == null)
			throw new BankException("Client does not exist");
		
		client.addAccount(account); //--> be fognak töltődni a jelenlegi accountok is a client-hez
		//account.setClient(client); //a client account listájában tranzakción belül nem fog látszódni ez az új account
		//client.getAccounts().add(account); //teljesen rossz, mert a tulajdonos oldalról nem állítja be a kapcsolatot, NULL lesz DB-ben a clientid
		account.setCreatedate(new Date());
		accountDao.create(account);
	}
	
	
	@Override
	public void transfer(int fromId, int toId, double amount) throws BankException {
		try {
			//logTransfer(fromId, toId, amount); --> lokális hívásnál nem lesz hatása a @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)-nak, és más annotációnak sem 
			ctx.getBusinessObject(BankServiceLocal.class).logTransfer(fromId, toId, amount);
			Account fromAccount = getAccountOrThrow(fromId);
			Account toAccount = getAccountOrThrow(toId);

			toAccount.increase(amount);
			fromAccount.decrease(amount);
			
		} catch (Exception e) {			
			ctx.setRollbackOnly();
			throw e;
		}
	}
	

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void logTransfer(int fromId, int toId, double amount) {
		historyDao.create(new History(String.format("Transfer tried from %d to %d, amount %f", fromId, toId, amount)));
	}


	private Account getAccountOrThrow(int id) throws BankException {
		Account account = accountDao.findById(id);
		if(account == null) {
			throw new BankException(String.format("Account with id %d does not exist.", id));
		}
		return account;
	}
}
