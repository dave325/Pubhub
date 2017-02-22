package examples.pubhub.dao;

import java.util.List;

import examples.pubhub.model.Book;
import examples.pubhub.model.BookList;
import examples.pubhub.model.PurchasedBook;
import examples.pubhub.model.Users;

public interface UserDAO {
	public Boolean loginAccount(Users user);//login to account
	public Boolean logOut(String user); // logout of account
	public String getUser(String user);
	public List<BookList> viewBooks(String user); //View Books in Marketplace
	public List<Book> viewPurchasedBooks(); //view Shopping Carts
	public Boolean addItem(String isbn); // Add Items to Cart
	public Boolean removeItem(String isbn); // Remove Items from Cart
	public List<PurchasedBook> orderHistory(); // view Order History
	public Boolean checkout(); // Checkout Cart
}
