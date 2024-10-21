package bank.web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bank.dao.AccountDao;
import bank.model.Account;
import bank.model.Client;
import bank.service.BankServiceLocal;

/**
 * Servlet implementation class CreateClientServlet
 */
@WebServlet("/SearchAccountServlet")
public class SearchAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	AccountDao accountDao;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String accountPar = request.getParameter("accountId");
		Integer accountId = parseIfNotEmpty(accountPar, Integer::parseInt);
		
		String clientPar = request.getParameter("clientId");
		Integer clientId = parseIfNotEmpty(clientPar, Integer::parseInt);
		String balancePar = request.getParameter("balance");
		Double balance = parseIfNotEmpty(balancePar, Double::parseDouble);
		
		String createdatePar = request.getParameter("createDate");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date createdate = null;
		createdate = parseIfNotEmpty(createdatePar, s -> {
			try {
				return sdf.parse(s);
			} catch (ParseException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		});
		
		
		try {
			Account account = new Account(balance == null ? 0.0 : balance);
			account.setAccountid(accountId);
			account.setCreatedate(createdate);
			Client client = new Client();
			account.setClient(client);
			client.setClientid(clientId);
			client.setName(request.getParameter("clientName"));
			accountDao.findByExample(account).forEach(a -> System.out.println(a.getAccountid()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	private <T> T parseIfNotEmpty(String s, Function<String, T> conversion) {
		return (s == null || s.length() == 0) ? null : conversion.apply(s);
	}

}