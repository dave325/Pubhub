package examples.pubhub.dao;

import java.util.ArrayList;
import java.util.List;

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
import examples.pubhub.model.BookTags;
import examples.pubhub.utilities.LoggingTest;

/**
 * Implementation for the BookDAO, responsible for querying the database for
 * Book objects.
 */
public class BookDAOImpl implements BookDAO {

	private static Logger log = Logger.getLogger(LoggingTest.class);

	StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
	SessionFactory sessionFactory = null;
	/*------------------------------------------------------------------------------------------------*/

	@Override
	public List<Book> getAllBooks() {
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
		List<Book> query = session.createQuery("FROM Book").getResultList();
		for (Book s : query) {
			books.add(s);
		}
		session.getTransaction().commit();
		// return the list of Book objects populated by the DB.
		session.close();
		String logBooks = books.toString();
		log.setLevel(Level.INFO);
		log.info("getAllBooks returned: " + logBooks);
		return books;
	}

	/*------------------------------------------------------------------------------------------------*/

	@Override
	public List<Book> getBooksByTitle(String title) {

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
		Query query = session.createQuery("FROM Book WHERE title LIKE :titleName"); 
		query.setParameter("titleName", "%" + title + "%");
		List<Book> bookList = query.getResultList();
		for (Book b : bookList) {
			books.add(b);
		}
		String logBooks = books.toString();
		log.setLevel(Level.INFO);
		log.info("getBooksByTitle returned: " + logBooks);
		return books;
	}

	/*------------------------------------------------------------------------------------------------*/

	@Override
	public List<Book> getBooksByAuthor(String author) {
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
		Query query = session.createQuery("FROM Book WHERE title LIKE :authorName"); 
		query.setParameter("authorName", "%" + author + "%");
		List<Book> bookList = query.getResultList();
		for (Book b : bookList) {
			books.add(b);
		}
		String logBooks = books.toString();
		log.setLevel(Level.INFO);
		log.info("getBooksByAuthor returned: " + logBooks);
		return books;
	}

	/*------------------------------------------------------------------------------------------------*/

	@Override
	public List<Book> getBooksLessThanPrice(double price) {
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
		Query query = session.createQuery("FROM Book WHERE title LIKE :priceName"); 
		query.setParameter("priceName", "%" + price + "%");
		List<Book> bookList = query.getResultList();
		for (Book b : bookList) {
			books.add(b);
		}
		// This command populate the 1st '?' with the title and wildcards,
		// between ' '
		String logBooks = books.toString();
		log.setLevel(Level.INFO);
		log.info("getBooksLessThanPrice returned: " + logBooks);
		return books;
	}

	/*------------------------------------------------------------------------------------------------*/

	@Override
	public Book getBookByISBN(String isbn) {
		Book book = null;

		try {
			sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
		}
		catch (Exception e) {
			System.out.println("Could not create connection!");
			e.printStackTrace();
			// The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
			// so destroy it manually.
			StandardServiceRegistryBuilder.destroy( registry );
		}
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.createQuery("FROM Book WHERE isbn_13=:isbn"); // Note the ? in the query
		query.setParameter("isbn", isbn );
		List<Book> result = query.getResultList();
		for (Book s : result) {
			book = s;
		}
		session.getTransaction().commit();
		session.close();
		// This command populate the 1st '?' with the title and wildcards, between ' '
		log.setLevel(Level.INFO);
		log.info("getBooksByIsbn returned: " + book);
		return book;
	}

	/*------------------------------------------------------------------------------------------------*/

	@Override
	public boolean addBook(Book book) {
		Book newBook = new Book();
		BookTags newBookTag = new BookTags();
				try {
					sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
				}
				catch (Exception e) {
					System.out.println("Could not create connection!");
					e.printStackTrace();
					// The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
					// so destroy it manually.
					StandardServiceRegistryBuilder.destroy( registry );
				}
				Session session = sessionFactory.openSession();
				session.beginTransaction();
				newBook.setIsbn13(book.getIsbn13());
				newBook.setTitle(book.getTitle());
				newBook.setAuthor(book.getAuthor());
				newBook.setPublishDate(book.getPublishDate());
				newBook.setPrice(book.getPrice());
				newBook.setContent(book.getContent());
				newBookTag.setIsbn13(book.getIsbn13());
				newBookTag.setTags(null);
				newBookTag.setTitle(book.getTitle());
				boolean success = false;
				try{
					session.saveOrUpdate(newBook);
					session.saveOrUpdate(newBookTag);
					session.getTransaction().commit();
					success = true;
					log.setLevel(Level.INFO);
					log.info("Successfully added: " + book.getTitle());
				}
				catch(HibernateException e){
					log.setLevel(Level.ERROR);
					log.info("Not able to add book: " + book.getTitle());
					success = false;
				}finally{
					session.close();
				}
				return success;
				//Query query = session.createQuery(tagsql); // Note the ? in the query
				//query.setParameter("isbn", "%" + book.getIsbn13() + "%");
				//query.executeUpdate();
				
	}

	/*------------------------------------------------------------------------------------------------*/

	@Override
	public boolean updateBook(Book book) {
		try {
			sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
		}
		catch (Exception e) {
			System.out.println("Could not create connection!");
			e.printStackTrace();
			// The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
			// so destroy it manually.
			StandardServiceRegistryBuilder.destroy( registry );
		}
		Session session = sessionFactory.openSession();
		session.beginTransaction();
			String sql = "UPDATE Book SET title=:title, author=:author, price=:price WHERE isbn_13=:isbn";
			Query query = session.createQuery(sql);
			query.setParameter("title", book.getTitle());
			query.setParameter("author", book.getAuthor());
			query.setParameter("price", book.getPrice());
			query.setParameter("isbn", book.getIsbn13());
			if(query.executeUpdate() != 0){
				session.getTransaction().commit();
				session.close();
				log.setLevel(Level.INFO);
				log.info("Successfully updated " + book.getTitle());
				return true;
			}else{
				session.close();
				log.setLevel(Level.WARN);
				log.info("Unsuccessfully updated " + book.getTitle());
				return false;
			}
	}

	/*------------------------------------------------------------------------------------------------*/

	@Override
	public boolean deleteBookByISBN(String isbn) {
		try {
			sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
		}
		catch (Exception e) {
			System.out.println("Could not create connection!");
			e.printStackTrace();
			// The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
			// so destroy it manually.
			StandardServiceRegistryBuilder.destroy( registry );
		}
		Session session = sessionFactory.openSession();
			String sql = "DELETE Book WHERE isbn_13=:isbn";
			Query query = session.createQuery(sql);
			query.setParameter("isbn", isbn);
			if(query.executeUpdate() != 0){
				session.close();
				log.setLevel(Level.INFO);
				log.info("Successfully deleted " + isbn);
				return true;
			}else{
				session.close();
				log.setLevel(Level.WARN);
				log.info("Unsuccessfully deleted " + isbn);
				return false;
			}
	}

	/*------------------------------------------------------------------------------------------------
	// Closing all resources is important, to prevent memory leaks.
	// Ideally, you really want to close them in the reverse-order you open them
	private void closeResources() {
		try {
			if (stmt != null)
				stmt.close();
		} catch (SQLException e) {
			System.out.println("Could not close statement!");
			e.printStackTrace();
		}

		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			System.out.println("Could not close connection!");
			e.printStackTrace();
		}
	}
	*/
}
