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
import examples.pubhub.utilities.DAOUtilities;

@WebServlet("/DeleteFromCart")
public class DeleteFromCartServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		boolean isSuccess= false;
		String isbn13 = request.getParameter("isbn13");
		String title = request.getParameter("title");
		UserDAO userdao = DAOUtilities.getUserDAO();
		isSuccess = userdao.removeItem(isbn13);
		List<Book> cartItem = userdao.viewPurchasedBooks();
		if(isSuccess){
			request.getSession().setAttribute("items", cartItem);
			request.getSession().setAttribute("message", "Cart successfully removed " + title);
			request.getSession().setAttribute("messageClass", "alert-success");
			response.sendRedirect("viewCart.jsp");
		}else {
			request.getSession().setAttribute("message", "There was a problem updating the cart");
			request.getSession().setAttribute("messageClass", "alert-danger");
			request.getRequestDispatcher("viewCart.jsp").forward(request, response);
		}
	}

}
