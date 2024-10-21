package bank.service;

import javax.annotation.Priority;
import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;

import bank.exception.BankException;

@Decorator
public abstract class TransferDecorator implements BankServiceLocal {

	@Inject @Delegate
	private BankServiceLocal bankService;
	
	
	@Override
	public void transfer(int fromId, int toId, double amount) throws BankException {
		amount *= 400.0;
		
		bankService.transfer(fromId, toId, amount);
	}

	
}
