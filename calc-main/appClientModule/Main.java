import javax.ejb.EJB;

import hu.novaservices.calc.CalculatorBeanRemote;
import hu.novaservices.calc.ShoppingCartBeanRemote;

public class Main {
	
	@EJB
	static CalculatorBeanRemote calc;
	
	@EJB
	static ShoppingCartBeanRemote cart1;
	
	@EJB
	static ShoppingCartBeanRemote cart2;
	
	public static void main(String[] args) {
		System.out.println("3 + 4 = " + calc.add(3, 4));
		
		cart1.addProductId(1);
		cart1.addProductId(3);
		cart1.addProductId(5);
		
		cart2.addProductId(2);
		cart2.addProductId(4);
		cart2.addProductId(6);
		
		System.out.println("Cart1:");
		cart1.getProductIds().stream().forEach(System.out::println);
		
		System.out.println("Cart2:");
		cart2.getProductIds().stream().forEach(System.out::println);
	}
}