package bank.exception;

import javax.ejb.ApplicationException;

//@ApplicationException(rollback = true)
public class BankException extends Exception {

	public BankException(String message) {
		super(message);
	}

}
