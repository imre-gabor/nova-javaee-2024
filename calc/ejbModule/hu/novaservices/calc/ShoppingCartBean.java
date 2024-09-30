package hu.novaservices.calc;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateful;

/**
 * Session Bean implementation class ShoppingCartBean
 */
@Stateful
public class ShoppingCartBean implements ShoppingCartBeanRemote {

	private List<Integer> productIds;
	
	@PostConstruct
    public void init() {
    	System.out.println("ShoppingCartBean @PostConstruct called");
    }
    
    @PreDestroy
    public void destroy() {
    	System.out.println("ShoppingCartBean @PreDestroy called");
    }
	
    /**
     * Default constructor. 
     */
    public ShoppingCartBean() {
        productIds = new ArrayList<>();
    }

    @Override
	public void addProductId(int id) {
    	productIds.add(id);
    }

	@Override
	public List<Integer> getProductIds() {
		return productIds;
	}
    
}
