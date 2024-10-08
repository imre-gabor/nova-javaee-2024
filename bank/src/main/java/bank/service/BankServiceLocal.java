package bank.service;

import javax.ejb.Local;

import bank.model.Account;
import bank.model.Client;

@Local
public interface BankServiceLocal {

	void transfer(int fromId, int toId, double amount);

	void createAccountForClient(Account account, int clientId) throws BankException;

	void createClient(Client client);

}
