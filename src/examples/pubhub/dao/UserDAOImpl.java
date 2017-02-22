package examples.pubhub.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import examples.pubhub.model.Book;
import examples.pubhub.model.BookList;
import examples.pubhub.model.PurchasedBook;
import examples.pubhub.model.ShoppingCart;
import examples.pubhub.model.Users;
import examples.pubhub.utilities.LoggingTest;

public class UserDAOImpl implements UserDAO {
	StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
	SessionFactory sessionFactory = null;
	private static Logger log = Logger.getLogger(LoggingTest.class);

	// View Books in Marketplace
	@Override
	public List<BookList> viewBooks(String user) {
		List<BookList> books = new ArrayList<>();
		try {
			sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		} catch (Exception e) {
			System.out.println("Could not create connection!");
			e.printStackTrace();
			// The registry would be destroyed by the SessionFactory, but we had
			// trouble building the SessionFactory
			// so destroy it manually.
			StandardServiceRegistryBuilder.destroy(registry);
		}

		Session session = sessionFactory.openSession();

		session.beginTransaction();
		List<BookList> query = session.createQuery("FROM BookList").getResultList();
		Query purchaseQuery = session.createQuery("FROM PurchasedBook Where username=:username AND isbn_13=:isbn");
		purchaseQuery.setParameter("username", user);
		for (BookList s : query) {
			BookList b = new BookList();
			b.setTitle(s.getTitle());
			b.setAuthor(s.getAuthor());
			b.setIsbn_13(s.getIsbn_13());
			b.setPrice(s.getPrice());
			purchaseQuery.setParameter("isbn", s.getIsbn_13());
			try{
				PurchasedBook date = (PurchasedBook) purchaseQuery.getSingleResult();
				b.setPurchase_date(date.getDate());
			}catch(NoResultException ne){
				b.setPurchase_date(null);
			}
			books.add(b);
		}
		session.getTransaction().commit();
		// return the list of Book objects populated by the DB.
		session.close();
		String logBooks = books.toString();
		log.setLevel(Level.INFO);
		log.info("viewPurchasedBooks returned: " + logBooks);
		return books;
	}

	// view Shopping Carts
	@Override
	public List<Book> viewPurchasedBooks() {
		List<Book> books = new ArrayList<>();
		try {
			sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		} catch (Exception e) {
			System.out.println("Could not create connection!");
			e.printStackTrace();
			// The registry would be destroyed by the SessionFactory, but we had
			// trouble building the SessionFactory
			// so destroy it manually.
			StandardServiceRegistryBuilder.destroy(registry);
		}

		Session session = sessionFactory.openSession();

		session.beginTransaction();
		// Cycle through shopping cart
		Query<Book> cartISBN = session
				.createQuery("Select book from Book book, ShoppingCart cart where cart.isbn_13 = book.isbn_13");
		List<Book> cartResult = cartISBN.getResultList();
		for (Book b : cartResult) {
			books.add(b);
		}
		session.getTransaction().commit();
		session.close();
		String logBooks = books.toString();
		log.setLevel(Level.INFO);
		log.info("viewPurchasedBooks returned: " + logBooks);
		// return the list of Book objects populated by the DB.
		return books;
	}

	// Add Items to Cart
	@Override
	public Boolean addItem(String isbn) {
		boolean queryResult = false; // Return value to the servlet
		try {
			sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		} catch (Exception e) {
			System.out.println("Could not create connection!");
			e.printStackTrace();
			// The registry would be destroyed by the SessionFactory, but we had
			// trouble building the SessionFactory
			// so destroy it manually.
			StandardServiceRegistryBuilder.destroy(registry);
		}
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		String checkHql = "From ShoppingCart c Where c.isbn_13=:isbn";
		Query checkQuery = session.createQuery(checkHql);
		checkQuery.setParameter("isbn", isbn);
		ShoppingCart checkResult = (ShoppingCart) checkQuery.uniqueResult();
		if (checkResult != null) {
			queryResult = false;
			log.setLevel(Level.INFO);
			log.info("Unable to add: " + isbn + ". Already existed in shopping cart");
		} else {
			try {
				String hql = "Insert Into ShoppingCart (isbn_13, price) Select b.isbn_13, b.price From BookList b Where b.isbn_13=:isbn";
				Query insertStatement = session.createQuery(hql);
				insertStatement.setParameter("isbn", isbn);
				insertStatement.executeUpdate();
				session.getTransaction().commit();
				queryResult = true;
				log.setLevel(Level.INFO);
				log.info("addItem returned " + isbn);
			} catch (HibernateException e) {
				e.printStackTrace();
				queryResult = false;
				log.setLevel(Level.ERROR);
				log.error("Exception caught: " + e.getMessage());
			}
		}
		session.close();

		return queryResult;
	}

	@Override
	// Remove Items from Cart
	public Boolean removeItem(String isbn) {
		boolean queryResult = false; // Return Vlaue to the servlet
		try {
			sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		} catch (Exception e) {
			System.out.println("Could not create connection!");
			e.printStackTrace();
			// The registry would be destroyed by the SessionFactory, but we had
			// trouble building the SessionFactory
			// so destroy it manually.
			StandardServiceRegistryBuilder.destroy(registry);
		}

		Session session = sessionFactory.openSession();
		session.beginTransaction();
		String hql = "DELETE ShoppingCart WHERE isbn_13=:isbn";
		Query query = session.createQuery(hql);
		query.setParameter("isbn", isbn);
		if (query.executeUpdate() != 0) {
			session.getTransaction().commit();
			session.close();
			queryResult = true;
			log.setLevel(Level.INFO);
			log.info("Successfully updated cart, added item: " + isbn);
		} else {
			session.close();
			queryResult = false;
			log.setLevel(Level.WARN);
			log.info("Unable to add item: " + isbn);
		}

		return queryResult;
	}

	@Override
	// view Order History
	public List<PurchasedBook> orderHistory() {
		List<PurchasedBook> books = new ArrayList<>();
		try {
			sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		} catch (Exception e) {
			System.out.println("Could not create connection!");
			e.printStackTrace();
			// The registry would be destroyed by the SessionFactory, but we had
			// trouble building the SessionFactory
			// so destroy it manually.
			StandardServiceRegistryBuilder.destroy(registry);
		}

		Session session = sessionFactory.openSession();

		session.beginTransaction();

		List<PurchasedBook> query = session.createQuery("From PurchasedBook").getResultList();// Our
																								// SQL
		for (PurchasedBook s : query) {
			books.add(s);
		}
		session.getTransaction().commit();
		// return the list of Book objects populated by the DB.
		session.close();
		String logBooks = books.toString();
		log.setLevel(Level.INFO);
		log.info("orderHistory returned: " + logBooks);
		return books;
	}

	// Checkout Cart
	@Override
	public Boolean checkout() {
		boolean queryResult = false; // Return Vlaue to the servlet
		try {
			sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		} catch (Exception e) {
			System.out.println("Could not create connection!");
			e.printStackTrace();
			// The registry would be destroyed by the SessionFactory, but we had
			// trouble building the SessionFactory
			// so destroy it manually.
			StandardServiceRegistryBuilder.destroy(registry);
			log.setLevel(Level.FATAL);
			log.fatal("Fatal Error: " + e.getMessage());
		}
		Session session = sessionFactory.openSession();

		session.beginTransaction();
		// Insert purchased book
		Query<?> query = session.createQuery(
				"Insert Into PurchasedBook(isbn_13, date) Select s.isbn_13, s.purchase_date From ShoppingCart s");
		if (query.executeUpdate() != 0) {
			// if successful, empty shopping cart
			Query removeList = session.createQuery("Delete ShoppingCart");
			if (removeList.executeUpdate() != 0) {
				// retrieve the table entries
				String updateBookList = "FROM PurchasedBook p";
				Query updateBook = session.createQuery(updateBookList);
				List<PurchasedBook> updateCheck = updateBook.getResultList();
				// Cycle through the array of purchased book and insert purchase
				// date into booklist if isbn_13 are the same and if booklist
				// has null date value
				for (PurchasedBook p : updateCheck) {
					Query updatehql = session.createQuery(
							"Update BookList b Set purchase_date = :purchase_date Where b.isbn_13=:isbn AND b.purchase_date is null");
					updatehql.setParameter("purchase_date", p.getDate());
					updatehql.setParameter("isbn", p.getIsbn13());
					updatehql.executeUpdate();
					log.setLevel(Level.INFO);
					log.info("Successfully updated: " + p.getIsbn13());
				}
				session.getTransaction().commit();
				session.close();
				queryResult = true;
			} else {
				session.close();
				queryResult = false;
				log.setLevel(Level.ERROR);
				log.error("Did not delete shopping cart");
			}
		} else {
			session.close();
			queryResult = false;
			log.setLevel(Level.WARN);
			log.warn("Unable to checkout cart");
		}
		return queryResult;
	}

	@Override
	public Boolean loginAccount(Users user) {
		// TODO Auto-generated method stub
		Boolean isSuccess = false;
		try {
			sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		} catch (Exception e) {
			System.out.println("Could not create connection!");
			e.printStackTrace();
			// The registry would be destroyed by the SessionFactory, but we had
			// trouble building the SessionFactory
			// so destroy it manually.
			StandardServiceRegistryBuilder.destroy(registry);
			log.setLevel(Level.FATAL);
			log.fatal("Fatal Error: " + e.getMessage());
		}
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		String hql = "From Users u Where (u.username=:username AND u.password=:password)";
		Query query = session.createQuery(hql);
		query.setParameter("username", user.getUsername());
		query.setParameter("password", user.getPassword());
		try {
			Users result = (Users) query.getSingleResult();
			if (result != null) {
				String setActive = "Update Users u SET u.activeUser=:active Where (u.username=:username)";
				Query queryActive = session.createQuery(setActive);
				queryActive.setParameter("username", user.getUsername());
				queryActive.setParameter("active", "true");
				if (queryActive.executeUpdate() != 0) {
					log.setLevel(Level.INFO);
					log.info(user.getUsername() + " successfully loggged in at " + LocalDate.now());
					isSuccess = true;
					session.getTransaction().commit();
				} else {
					log.setLevel(Level.WARN);
					log.warn("Unable to login " + user.getUsername() + " at " + LocalDate.now());
					isSuccess = false;
				}
			}else{
				log.setLevel(Level.WARN);
				log.warn("Unable to login " + user.getUsername() + " at " + LocalDate.now() + ". No username existed");
				isSuccess = false;
			}
		} catch (NoResultException ne) {
			log.setLevel(Level.WARN);
			log.warn("Unable to login " + user.getUsername() + " at " + LocalDate.now());
			isSuccess = false;
		} finally {
			session.close();
		}
		return isSuccess;
	}

	@Override
	public Boolean logOut(String user) {
		// TODO Auto-generated method stub
		Boolean isSuccess = false;
		try {
			sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		} catch (Exception e) {
			System.out.println("Could not create connection!");
			e.printStackTrace();
			// The registry would be destroyed by the SessionFactory, but we had
			// trouble building the SessionFactory
			// so destroy it manually.
			StandardServiceRegistryBuilder.destroy(registry);
			log.setLevel(Level.FATAL);
			log.fatal("Fatal Error: " + e.getMessage());
		}
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		String hql = "Update Users SET activeUser=:active Where username=:username";
		Query query = session.createQuery(hql);
		query.setParameter("active", "false");
		query.setParameter("username", user);
		if (query.executeUpdate() != 0) {
			log.setLevel(Level.INFO);
			log.info(user + " successfully loggged out at " + LocalDate.now());
			session.getTransaction().commit();
			isSuccess = true;
		} else {
			log.setLevel(Level.WARN);
			log.info(user + " did not successfully loggged out at " + LocalDate.now());
		}
		session.close();
		return isSuccess;
	}

	@Override
	public String getUser(String user) {
		// TODO Auto-generated method stub
		String username = null;
		try {
			sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		} catch (Exception e) {
			System.out.println("Could not create connection!");
			e.printStackTrace();
			// The registry would be destroyed by the SessionFactory, but we had
			// trouble building the SessionFactory
			// so destroy it manually.
			StandardServiceRegistryBuilder.destroy(registry);
			log.setLevel(Level.FATAL);
			log.fatal("Fatal Error: " + e.getMessage());
		}
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		String hql = "From Users u Where u.active='true'";
		Query query = session.createQuery(hql);
		Users result = (Users) query.getSingleResult();
		if (result != null) {
			username = result.getUsername();
		} else {
			username = null;
		}
		return username;
	}

}
