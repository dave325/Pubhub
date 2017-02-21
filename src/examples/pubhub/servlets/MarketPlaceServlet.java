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

@WebServlet("/Marketplace")
public class MarketPlaceServlet extends HttpServlet {
			
			private static final long serialVersionUID = 1L;

			protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

				// Grab the list of Books from the Database
				UserDAO dao = DAOUtilities.getUserDAO();
				List<BookList> bookList = dao.viewBooks();

				// Populate the list into a variable that will be stored in the session
				request.getSession().setAttribute("books", bookList);
				
				request.getRequestDispatcher("marketplace.jsp").forward(request, response);
			}
}
