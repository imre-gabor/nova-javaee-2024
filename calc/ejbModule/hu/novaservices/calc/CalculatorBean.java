package hu.novaservices.calc;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class CalculatorBean
 */
@Stateless
public class CalculatorBean implements CalculatorBeanRemote {

    /**
     * Default constructor. 
     */
    public CalculatorBean() {
        System.out.println("CalculatorBean constructor called");
    }
    
    @PostConstruct
    public void init() {
    	System.out.println("@PostConstruct called");
    }
    
    @PreDestroy
    public void destroy() {
    	System.out.println("@PreDestroy called");
    }
    
    
    @Override
	public int add(int a, int b) {
    	return a+b;
    }

}
