package examples.pubhub.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import examples.pubhub.dao.UserDAO;
import examples.pubhub.model.Book;
import examples.pubhub.model.PurchasedBook;
import examples.pubhub.utilities.DAOUtilities;

@WebServlet("/OrderHistory")
public class OrderHistoryServlet extends HttpServlet {
private static final long serialVersionUID = 1L;
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		boolean isSuccess= false;
		UserDAO userdao = DAOUtilities.getUserDAO();
		List<PurchasedBook> orderHistory = userdao.orderHistory();
		request.getSession().setAttribute("userBooks", orderHistory); // retrieve user order for later 
		if (orderHistory != null){
			isSuccess = true;
		}else{
			isSuccess = false;
		}
		if(isSuccess){
			request.getSession().setAttribute("message", "Cart successfully updated");
			request.getSession().setAttribute("messageClass", "alert-success");
			response.sendRedirect("viewOrderHistory.jsp");
		}else {
			request.getSession().setAttribute("message", "There was a problem updating the cart");
			request.getSession().setAttribute("messageClass", "alert-danger");
			request.getRequestDispatcher("marketplace.jsp").forward(request, response);
		}
	}
}
