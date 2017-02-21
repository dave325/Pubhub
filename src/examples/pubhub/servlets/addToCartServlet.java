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

@WebServlet("/AddToCart")
public class addToCartServlet extends HttpServlet{
private static final long serialVersionUID = 1L;
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
		boolean isSuccess= false;
		String isbn13 = request.getParameter("isbn13");
		String title = request.getParameter("title");
		UserDAO userdao = DAOUtilities.getUserDAO();
		isSuccess = userdao.addItem(isbn13);
		
		if(isSuccess){
			request.getSession().setAttribute("message", "Cart successfully updated: Added " + title);
			request.getSession().setAttribute("messageClass", "alert-success");
			response.sendRedirect("marketplace.jsp");
		}else {
			List<Book> cartItem = userdao.viewPurchasedBooks();
			request.getSession().setAttribute("items", cartItem);
			request.getSession().setAttribute("message", title + " is already in your shopping cart");
			request.getSession().setAttribute("messageClass", "alert-danger");
			request.getRequestDispatcher("viewCart.jsp").forward(request, response);
		}
	}catch(ServletException e){
		e.printStackTrace();
	}
	}
}
