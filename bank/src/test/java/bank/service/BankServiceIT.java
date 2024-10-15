package bank.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.File;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import bank.model.Account;

@RunWith(Arquillian.class)
public class BankServiceIT {

	@EJB
	BankServiceLocal bankService;
	
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
	
}
