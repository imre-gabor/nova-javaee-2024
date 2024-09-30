package hu.novaservices.calc;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface ShoppingCartBeanRemote {

	List<Integer> getProductIds();

	void addProductId(int id);

}
