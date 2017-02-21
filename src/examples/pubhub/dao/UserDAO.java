package examples.pubhub.dao;

import java.util.List;

import examples.pubhub.model.Book;
import examples.pubhub.model.BookList;
import examples.pubhub.model.PurchasedBook;

public interface UserDAO {
	public List<BookList> viewBooks(); //View Books in Marketplace
	public List<Book> viewPurchasedBooks(); //view Shopping Carts
	public Boolean addItem(String isbn); // Add Items to Cart
	public Boolean removeItem(String isbn); // Remove Items from Cart
	public List<PurchasedBook> orderHistory(); // view Order History
	public Boolean checkout(); // Checkout Cart
}
