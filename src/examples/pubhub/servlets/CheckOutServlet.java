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
import examples.pubhub.model.BookList;
import examples.pubhub.utilities.DAOUtilities;

@WebServlet("/Checkout")
public class CheckOutServlet extends HttpServlet{
private static final long serialVersionUID = 1L;
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		boolean isSuccess= false;
		UserDAO userdao = DAOUtilities.getUserDAO();
		isSuccess = userdao.checkout();
		List<BookList> bookList = userdao.viewBooks();
		// Populate the list into a variable that will be stored in the session
		request.getSession().setAttribute("books", bookList);
		if(isSuccess){
			request.getSession().setAttribute("message", "Successful Order");
			request.getSession().setAttribute("messageClass", "alert-success");
			response.sendRedirect("marketplace.jsp");
		}else {
			request.getSession().setAttribute("message", "There was a problem updating the cart");
			request.getSession().setAttribute("messageClass", "alert-danger");
			request.getRequestDispatcher("marketplace.jsp").forward(request, response);
		}
	}
}
