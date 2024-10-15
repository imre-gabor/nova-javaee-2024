package bank.service;

//import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import bank.dao.AccountDao;
import bank.dao.ClientDao;
import bank.model.Account;
import bank.model.Client;

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
//		BankException e = assertThrows(BankException.class, () -> {
//			//ACT
//			bankService.createAccountForClient(new Account(), 2);
//		});
		//ASSERT
//		assertEquals("Client does not exist", e.getMessage());
		
		assertThatThrownBy(() -> { bankService.createAccountForClient(new Account(), 2); })
			.hasMessage("Client does not exist");
	}
	
	@Test
	public void testAccountProperlyCreatedForExistingClient() throws Exception {
		Client client = new Client();
		int clientid = 1;
		client.setClientid(clientid);
		client.setAccounts(new HashSet<>());
		
		when(clientDao.findById(clientid)).thenReturn(client);
		
		Account account = new Account(100.0);
		bankService.createAccountForClient(account, clientid);
//		assertEquals(clientid, account.getClient().getClientid());
//		assertTrue(client.getAccounts().contains(account));
		assertThat(account.getCreatedate()).isCloseTo(new Date(), 500);
		assertThat(account.getClient().getClientid()).isEqualTo(clientid);
		assertThat(client.getAccounts()).contains(account);
		
		verify(accountDao).create(account);
	}
}
