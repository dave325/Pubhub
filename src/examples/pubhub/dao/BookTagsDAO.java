package examples.pubhub.dao;

import java.util.List;

import examples.pubhub.model.Book;
import examples.pubhub.model.BookTags;

public interface BookTagsDAO {
	public BookTags getTags(String isbn);
	
	public boolean addTag(BookTags bookTags);
	public boolean deleteTag(BookTags tag);
	public List<Book> getBooksByTags(String tag, String isbn);
	BookTags getBookTagsByISBN(String isbn);
}
