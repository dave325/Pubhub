package examples.pubhub.servlets;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import examples.pubhub.dao.UserDAO;
import examples.pubhub.utilities.DAOUtilities;

/**
 * Servlet implementation class LogoutAccount
 */
@WebServlet("/LogoutAccount")
public class LogoutAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Boolean isSuccess = false;
		ServletContext context = request.getServletContext();
		String completeURL = request.getHeader("referer");
		String username = (String) context.getAttribute("activeAccount");
		UserDAO dao = DAOUtilities.getUserDAO();
		isSuccess = dao.logOut(username);
		if (isSuccess){
			request.getSession().setAttribute("message", "Successfully logged out " + username + "!") ;
			request.getSession().setAttribute("messageClass", "alert-success");
			context.removeAttribute("activeAccount");
			response.sendRedirect("login.jsp");
		}else{
			request.getSession().setAttribute("message", "There was a problem logging you out. Please try again");
			request.getSession().setAttribute("messageClass", "alert-danger");
			request.getRequestDispatcher(completeURL).forward(request, response);
		}
	}
}
