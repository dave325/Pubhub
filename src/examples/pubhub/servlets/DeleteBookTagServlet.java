package examples.pubhub.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import examples.pubhub.dao.BookTagsDAO;
import examples.pubhub.model.BookTags;
import examples.pubhub.utilities.DAOUtilities;

/**
 * Servlet implementation class DeleteTagServlet
 */
@WebServlet("/DeleteBookTags")
public class DeleteBookTagServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean isSuccess= false;
		String isbn13 = request.getParameter("isbn13");
		String tags = request.getParameter("booktags");
		BookTagsDAO tagsdao = DAOUtilities.getBookTagDAO();
		BookTags bookTags = new BookTags();
		BookTags book = tagsdao.getTags(isbn13);
		if(book != null){
				bookTags.setIsbn13(isbn13);
				bookTags.setTags(tags);
				request.setAttribute("tags", bookTags);
				isSuccess = tagsdao.deleteTag(bookTags);
		}else {
			//ASSERT: couldn't find book with isbn. Update failed.
			isSuccess = false;
		}
		
		if(isSuccess){
			request.getSession().setAttribute("message", "Book successfully updated");
			request.getSession().setAttribute("messageClass", "alert-success");
			response.sendRedirect("ViewBookDetails?isbn13=" + isbn13);
		}else {
			request.getSession().setAttribute("message", "There was a problem updating this book");
			request.getSession().setAttribute("messageClass", "alert-danger");
			request.getRequestDispatcher("bookDetails.jsp").forward(request, response);
		}
	}

}
