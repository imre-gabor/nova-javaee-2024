package bank.service;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bank.exception.BankException;
import bank.model.Account;
import bank.model.Client;

@Local
public interface BankServiceLocal {

	void transfer(int fromId, int toId, double amount) throws BankException;

	void createAccountForClient(Account account, int clientId) throws BankException;

	void createClient(Client client);

	void logTransfer(int fromId, int toId, double amount);

	void scheduleTransfer(int fromId, int toId, double amount, int delayInSeconds);

}
