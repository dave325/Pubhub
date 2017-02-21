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


@WebServlet("/ViewCart")
public class ViewCartServlet extends HttpServlet{
		private static final long serialVersionUID = 1L;
	    

		/**
		 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

			boolean isSuccess= false;
			String username = request.getParameter("username");
			UserDAO userdao = DAOUtilities.getUserDAO();
			List<Book> cartItem = userdao.viewPurchasedBooks();
			if (cartItem != null){
				isSuccess = true;
				request.getSession().setAttribute("items", cartItem);
			}else{
				isSuccess = false;
			}
			
			if(isSuccess){
				response.sendRedirect("viewCart.jsp");
			}else {
				request.getSession().setAttribute("message", "There was no items in your cart");
				request.getSession().setAttribute("messageClass", "alert-danger");
				request.getRequestDispatcher("viewCart.jsp").forward(request, response);
			}
		}
}
