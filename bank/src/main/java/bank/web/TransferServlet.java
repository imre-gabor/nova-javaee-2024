package bank.web;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bank.service.BankServiceLocal;

/**
 * Servlet implementation class CreateClientServlet
 */
@WebServlet("/TransferServlet")
public class TransferServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	BankServiceLocal bank;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int fromId = Integer.parseInt(request.getParameter("from"));
		int toId = Integer.parseInt(request.getParameter("to"));
		double amount = Double.parseDouble(request.getParameter("amount"));
		int delay = Integer.parseInt(request.getParameter("delay"));
		
		String message = null;
		try {
			bank.transfer(fromId, toId, amount);
			//bank.scheduleTransfer(fromId, toId, amount, delay);
			message = "Transfer successful";
		} catch (Exception e) {
			e.printStackTrace();
			message = "Transfer failed: " + e.getMessage();
		}
		
		request.setAttribute("resultOfTransfer", message);
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

}
