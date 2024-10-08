package bank.service;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import bank.dao.AccountDao;
import bank.dao.ClientDao;
import bank.model.Account;
import bank.model.Client;

/**
 * Session Bean implementation class BankService
 */
@Stateless
public class BankService implements BankServiceLocal {

	@EJB
	private ClientDao clientDao;
	
	@EJB
	private AccountDao accountDao;

	@Override
	public void createClient(Client client) {
		clientDao.create(client);
	}
	
	
	@Override
	public void createAccountForClient(Account account, int clientId) throws BankException {
		Client client = clientDao.findById(clientId);
		if(client == null)
			throw new BankException("Client does not exist");
		
		client.addAccount(account);
		accountDao.create(account);
	}
	
	
	@Override
	public void transfer(int fromId, int toId, double amount) {
		
	}
}
