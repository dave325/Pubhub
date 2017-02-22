package examples.pubhub.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Purchased_Books")
public class PurchasedBook {
	
	@Id
	private String isbn_13;// International Standard Book Number, unique
	@Column(name="purchase_date")
	private LocalDate date;
	@OneToOne
	@JoinColumn(name="isbn_13")
	private BookList purchasedBook;
	@OneToOne(cascade = CascadeType.ALL, mappedBy="user")
	private Users user;
	@Column
	private String username;
	// Default constructor
	public PurchasedBook() {
		this.isbn_13 = null;
		this.date = LocalDate.now();
	}
	
	public String getIsbn13() {
		return isbn_13;
	}

	public void setIsbn13(String isbn13) {
		this.isbn_13 = isbn13;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	public BookList getPurchasedBook() {
		return purchasedBook;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPurchasedBook(BookList purchasedBook) {
		this.purchasedBook = purchasedBook;
	}
	
}
