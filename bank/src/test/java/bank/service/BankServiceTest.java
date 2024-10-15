package bank.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import bank.dao.AccountDao;
import bank.dao.ClientDao;
import bank.exception.BankException;
import bank.model.Account;

@ExtendWith(MockitoExtension.class)
public class BankServiceTest {
	
	@InjectMocks
	BankService bankService;
	
	@Mock
	ClientDao clientDao;
	
	@Mock
	AccountDao accountDao;
	
	@Test
	public void testCannotCreateAccountForNonExistingClient() {
		
//		bankService = new BankService();
//		clientDao = mock(ClientDao.class);
//		bankService.clientDao = clientDao;
//		when(clientDao.findById(anyInt())).thenReturn(null);
//		
//		try {
//			bankService.createAccountForClient(new Account(), 2);
//			fail("Expected BankException not thrown");
//		} catch (BankException e) {
//			assertEquals("Client does not exist", e.getMessage());
//		}
		
		//ARRANGE
		when(clientDao.findById(anyInt())).thenReturn(null);
		
//		try {
//			//ACT
//			bankService.createAccountForClient(new Account(), 2);
//			
//			//ASSERT
//			fail("Expected BankException not thrown");
//		} catch (BankException e) {
//			assertEquals("Client does not exist", e.getMessage());
//		}
		
		//ASSERT
		BankException e = assertThrows(BankException.class, () -> {
			//ACT
			bankService.createAccountForClient(new Account(), 2);
		});
		//ASSERT
		assertEquals("Client does not exist", e.getMessage());
		
	}
	
	@Test

}
