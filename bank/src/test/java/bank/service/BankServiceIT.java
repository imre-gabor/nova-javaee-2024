package bank.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import bank.dao.AccountDao;
import bank.dao.ClientDao;
import bank.model.Account;
import bank.model.Client;

@RunWith(Arquillian.class)
@Transactional(value = TransactionMode.ROLLBACK)
public class BankServiceIT {

	@EJB
	BankServiceLocal bankService;
	
	@EJB
	ClientDao clientDao;
	
	@EJB
	AccountDao accountDao;
	
	@Deployment
	public static WebArchive createDeployment() {
		
		File[] dependencies = Maven.resolver()
				.loadPomFromFile("pom.xml")
				.resolve("org.assertj:assertj-core")
				.withTransitivity()
				.asFile();
		
		return ShrinkWrap.create(WebArchive.class)
				.addPackages(false, "bank.service", 
						"bank.model", "bank.dao", "bank.exception")
				.addAsResource("META-INF/persistence.xml")
				.addAsLibraries(dependencies);
				
	}
	
	@Test
	public void testCannotCreateAccountForNonExistingClient() {
//		BankException e = assertThrows(BankException.class, () -> {
////			//ACT
//			bankService.createAccountForClient(new Account(), -1);
//		});
//		assertEquals("Client does not exist", e.getMessage());
		
		assertThatThrownBy(() -> { 
			bankService.createAccountForClient(new Account(), -1); 
		}).hasMessage("Client does not exist");
		
	}
	
	@Test
	public void testAccountProperlyCreatedForExistingClient() throws Exception {
		//ARRANGE
		int clientId = getExistingClientId();
		
		//ACT
		Account account = new Account(100.0);
		bankService.createAccountForClient(account, clientId);
		int accountid = account.getAccountid();
		
		//ASSERT
		Account accountFromDb = accountDao.findById(accountid);
		assertThat(accountFromDb).isNotNull();
		assertThat(accountFromDb.getBalance()).isEqualTo(100.0);
		assertThat(accountFromDb.getClient().getClientid()).isEqualTo(clientId);
		assertThat(accountFromDb.getCreatedate()).isInSameDayAs(new Date());
	}

	private int getExistingClientId() {
		List<Client> allClients = clientDao.findAll();
		Client client = null;
		if(allClients.isEmpty()) {
			client = new Client();
			client.setName("abcdef");
			client.setAddress("address");
			clientDao.create(client);
		} else {
			client = allClients.get(0);
		}
		
		return client.getClientid();
	}
	
}