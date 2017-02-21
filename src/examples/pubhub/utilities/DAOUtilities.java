package examples.pubhub.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import examples.pubhub.dao.BookDAO;
import examples.pubhub.dao.BookDAOImpl;
import examples.pubhub.dao.BookTagsDAO;
import examples.pubhub.dao.BookTagsDAOImpl;
import examples.pubhub.dao.UserDAO;
import examples.pubhub.dao.UserDAOImpl;
import oracle.jdbc.driver.OracleDriver;

/**
 * Class used to retrieve DAO Implementations. Serves as a factory. Also manages a single instance of the database connection.
 */
public class DAOUtilities {

	private static final String CONNECTION_PASSWORD = "Pachi92813";
	private static final String CONNECTION_USERNAME = "dave325";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static Connection connection;
	
	public static synchronized Connection getConnection() throws SQLException {
		if (connection == null) {
			System.out.println("Opening first connection...");
			DriverManager.registerDriver(new OracleDriver());
			connection = DriverManager.getConnection(URL, CONNECTION_USERNAME, CONNECTION_PASSWORD);
		}
		
		//If connection was closed then retrieve a new connection
		if (connection.isClosed()){
			System.out.println("getting new connection...");
			connection = DriverManager.getConnection(URL, CONNECTION_USERNAME, CONNECTION_PASSWORD);
		}
		return connection;
	}
	
	public static BookDAO getBookDAO() {
		return new BookDAOImpl();
	}
	public static BookTagsDAO getBookTagDAO(){
		return new BookTagsDAOImpl();
	}
	public static UserDAO getUserDAO(){
		return new UserDAOImpl();
	}

}
