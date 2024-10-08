package bank.web;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bank.model.Account;
import bank.service.BankServiceLocal;

/**
 * Servlet implementation class CreateClientServlet
 */
@WebServlet("/CreateAccountServlet")
public class CreateAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	BankServiceLocal bank;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int clientId = Integer.parseInt(request.getParameter("clientId"));
		double balance = Double.parseDouble(request.getParameter("balance"));
		
		String message = null;
		try {
			Account account = new Account(balance);
			bank.createAccountForClient(account, clientId);
			message = "Account created with id " + account.getAccountid();
		} catch (Exception e) {
			e.printStackTrace();
			message = "Account creation failed: " + e.getMessage();
		}
		
		request.setAttribute("resultOfAccountCreation", message);
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

}
