package examples.pubhub.servlets;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import examples.pubhub.dao.UserDAO;
import examples.pubhub.model.Users;
import examples.pubhub.utilities.DAOUtilities;

/**
 * Servlet implementation class LoginAccount
 */
@WebServlet("/LoginAccount")
public class LoginAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Boolean isSuccess = false;
		ServletContext context = request.getSession().getServletContext();
		String url = request.getParameter("url");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Users user = new Users();
		user.setUsername(username);
		user.setPassword(password);
		UserDAO dao = DAOUtilities.getUserDAO();
		isSuccess = dao.loginAccount(user);
		if (isSuccess){
			context.setAttribute("activeAccount", username);
			request.getSession().setAttribute("message", "Thank you " + username + "!") ;
			request.getSession().setAttribute("messageClass", "alert-success");
			response.sendRedirect(url);
		}else{
			request.getSession().setAttribute("message", "There was a problem logging you in. Please try again");
			request.getSession().setAttribute("messageClass", "alert-danger");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}

}
