package examples.pubhub.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import examples.pubhub.model.Book;
import examples.pubhub.model.BookTags;
import examples.pubhub.utilities.DAOUtilities;
import examples.pubhub.utilities.LoggingTest;

public class BookTagsDAOImpl extends BookDAOImpl implements BookTagsDAO {

	private static Logger log = Logger.getLogger(LoggingTest.class);

	StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
	SessionFactory sessionFactory = null;
	/*------------------------------------------------------------------------------------------------*/

	public List<Book> getBooksByTags(String tags, String isbn) {
		List<Book> bookTags = new ArrayList<Book>();
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
			String hql = "SELECT b.price, b.author, b.publish_date, t.ISBN13, t.title FROM Tags t, Books b WHERE t LIKE :tags";
				Query query = session.createQuery(hql);
				query.setParameter("tags", tags);
				List<Book> book = query.getResultList();
				for (Book b : book){
									Book bookTag = new Book();
									bookTag.setIsbn13(b.getIsbn13());
									bookTag.setTitle(b.getTitle());
									bookTag.setPrice(b.getPrice());
									bookTag.setAuthor(b.getAuthor());
									bookTag.setPublishDate(b.getPublishDate());
									bookTags.add(bookTag);
				}
		log.setLevel(Level.INFO);
		log.info("Returned: " + book.size() + "books from tag '" + tags + " '");
		session.close();
		return bookTags;
	}

	@Override
	public BookTags getBookTagsByISBN(String isbn) {
		BookTags bookTags = null;
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
		String hql = "FROM Tags WHERE ISBN_13 =:isbn";
		Query query = session.createQuery(hql);
		query.setParameter("isbn", isbn);
		List<BookTags> books = query.getResultList();
		for (BookTags b: books){
				bookTags = new BookTags();
				bookTags.setIsbn13(b.getIsbn13());
				bookTags.setTags(b.getTags());
				bookTags.setTitle(b.getTitle());
		}
		log.setLevel(Level.INFO);
		log.info("Returned: " + books.size() + "books from tag '" + bookTags.getTags() + " '");
		session.close();
		return bookTags;
	}

	/*------------------------------------------------------------------------------------------------*/

	@Override
	public BookTags getTags(String isbn) {
		BookTags bookTag = null;
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
		String hql = "SELECT tags.tags FROM BookTags tags where tags.ISBN13=:isbn";
		Query query = session.createQuery(hql);
		query.setParameter("isbn", isbn);
		List<BookTags> tags = query.getResultList();
		for(BookTags b: tags){
			bookTag.setTags(b.getTags());
			bookTag.setTitle(b.getTitle());
		}
		log.setLevel(Level.INFO);
		log.info("Returned: " + bookTag.getTitle() + ". The tags are  '" + bookTag.getTags() + " '");
		session.close();
		return bookTag;
	}

	/*------------------------------------------------------------------------------------------------*/

	@Override
	public boolean addTag(BookTags bookTag) {
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
			String tagResults = "Select tags.tags From BookTags tags Where tags.ISBN13=:tagsIsbn";
			String sql = "Update BookTags tags SET tags.tags=:tags Where tags.ISBN13=:isbn"; 
			String updatedTags = null;
			Query query = session.createQuery(tagResults);
			Query query1 = session.createQuery(sql);
			query.setParameter("tagsIsbn", bookTag.getIsbn13());
			query1.setParameter("isbn", bookTag.getIsbn13());
			/*if(query.executeUpdate() != 0){
				session.getTransaction().commit();
				session.close();
				log.setLevel(Level.INFO);
				log.info("addTag() returned true" );
				return true;
			}else{
				session.close();
				log.setLevel(Level.INFO);
				log.info("addTag() returned false" );
				return false;
			}*/
			String newTagList = "";
			List<BookTags> tags = query.getResultList();
			//ResultSet rs = stmt.executeQuery();
			List<String> updateTagList = Arrays.asList(bookTag.getTags().split("\\s*,\\s*"));
			for(BookTags b: tags){
				String getTags = b.getTags();
				if (getTags != null) {
					List<String> tagList = Arrays.asList(getTags.split("\\s*,\\s*"));
					for (String s: updateTagList) {
						if (tagList.contains(s)) {
							newTagList += "";
						} else {
							newTagList += s + ", ";
						}
					}
					if (newTagList != "") {
						updatedTags = newTagList + tags;
					} else {
						updatedTags = getTags;
					}
				} else {
					updatedTags = bookTag.getTags();
				}
			}
			query1.setParameter("tags", updatedTags);
			if (query1.executeUpdate() != 0){
				log.setLevel(Level.INFO);
				log.info("addTag returned true");
				session.getTransaction().commit();
				session.close();
				return true;
			}else{
				log.setLevel(Level.WARN);
				log.info("AddTag returned false");
				session.close();
				return false;
			}
				
			/*if (rs.next()) {
				//String tags = rs.getString("tags");
				if (tags != null) {
					List<String> tagList = Arrays.asList(tags.split("\\s*,\\s*"));
					for (int i = 0; i < updateTagList.size(); i += 1) {
						if (tagList.contains(updateTagList.get(i))) {
							newTagList += "";
						} else {
							newTagList += updateTagList.get(i) + ", ";
						}
					}
					if (newTagList != "") {
						updatedTags = newTagList + tags;
					} else {
						updatedTags = tags;
					}
				} else {
					updatedTags = bookTag.getTags();
				}
				updatedstmt.setString(1, updatedTags);
				if (updatedstmt.executeUpdate() != 0) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			// If we were able to add our book to the DB, we want to return
			// true.
			// This if statement both executes our query, and looks at the
			// return
			// value to determine how many rows were changed

		/*} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}*/
	}
	
	/*------------------------------------------------------------------------------------------------*/

	@Override
	public boolean deleteTag(BookTags tag) {
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
		String sql = "Select tags.tags From BookTags tags Where tags.ISBN13=:isbn "; 
		String updatesql = "Update BookTags SET tags=:tags Where ISBN13=:isbn "; 
			String newTagList = "";
			String updateList = null;
			Query query = session.createQuery(sql);
			Query query1 = session.createQuery(updatesql);
			query.setParameter("isbn", tag.getIsbn13());
			query1.setParameter("isbn", tag.getIsbn13());
			//stmt.setString(1, tag.getIsbn13());
			//updatedstmt.setString(2, tag.getIsbn13());
			List<BookTags> result = query.getResultList();
			//ResultSet rs = stmt.executeQuery();
			List<String> deleteTagList = Arrays.asList(tag.getTags().split("\\s*,\\s*"));
			for(BookTags b:result){
				String tags = b.getTags();
				List<String> tagList = Arrays.asList(tags.split("\\s*,\\s*"));
				int j = 0;
				for (String s:tagList) {
					if (deleteTagList.contains(s)) {
						newTagList += "";
					} else {
						newTagList += s + ", ";
						j += 1;
					}
				}
				if (j > 1) {
					updateList = newTagList.substring(0, newTagList.length() - 2);
				} else {
					updateList = newTagList;
				}
			}
			query1.setParameter("tags", updateList);
			if(query1.executeUpdate() != 0){
				log.setLevel(Level.INFO);
				log.info("deleteTag returned true");
				session.getTransaction().commit();
				session.close();
				return true;
			}else{
				log.setLevel(Level.WARN);
				log.info("deleteTag returned false");
				session.close();
				return false;
			}
			/*if (rs.next()) {
				String tags = rs.getString("tags");
				List<String> tagList = Arrays.asList(tags.split("\\s*,\\s*"));
				int j = 0;
				for (int i = 0; i < tagList.size(); i += 1) {
					if (deleteTagList.contains(tagList.get(i))) {
						newTagList += "";
					} else {
						newTagList += tagList.get(i) + ", ";
						j += 1;
					}
				}
				if (j > 1) {
					updateList = newTagList.substring(0, newTagList.length() - 2);
				} else {
					updateList = newTagList;
				}
				updatedstmt.setString(1, updateList);
				if (updatedstmt.executeUpdate() != 0) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}*/

			// If we were able to add our book to the DB, we want to return
			// true.
			// This if statement both executes our query, and looks at the
			// return
			// value to determine how many rows were changed

		/*} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}*/
	}

	// Closing all resources is important, to prevent memory leaks.
	// Ideally, you really want to close them in the reverse-order you open them
	/*private void closeResources() {
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
	}*/

}
