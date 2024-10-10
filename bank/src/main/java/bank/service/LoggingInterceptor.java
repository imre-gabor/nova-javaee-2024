package bank.service;

import java.util.Arrays;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class LoggingInterceptor {

	@AroundInvoke
	public Object logCall(InvocationContext ctx) throws Exception {
		
		System.out.print("Calling " + ctx.getMethod().getName());
		System.out.print(" with argument list " + Arrays.toString(ctx.getMethod().getParameters()));
		System.out.println(" with values " + Arrays.toString(ctx.getParameters()));
		
		Object retVal = null;
		try {
			retVal = ctx.proceed();
			System.out.println("Returned " + retVal);
		} catch (Exception e) {
			System.out.println("Exception thrown.");
			throw e;
		}
		
		return retVal;
	}
	
}
