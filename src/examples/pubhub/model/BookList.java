package examples.pubhub.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="BOOKLIST")
public class BookList{
	
		@Id
		private String isbn_13;
		@OneToOne(cascade = CascadeType.ALL, mappedBy="booklist")
		private Book book;
		@Column
		private String title;
		@Column
		private String author;
		@Column
		private double price;
		@OneToOne(cascade = CascadeType.ALL, mappedBy="purchasedBook")
		private PurchasedBook purchaseBook;
		@Column
		private LocalDate purchase_date;	// Date of purchase to the website
		
		// Default constructor
		public BookList() {
			this.isbn_13 = null;
			this.book = null;
			this.title = null;
			this.author = null;
			this.price = 0;
			this.purchase_date = null;
		}
		
		public LocalDate getPurchase_date() {
			return purchase_date;
		}

		public void setPurchase_date(LocalDate purchase_date) {
			this.purchase_date = purchase_date;
		}
		
		public String getIsbn_13() {
			return isbn_13;
		}

		public void setIsbn_13(String isbn_13) {
			this.isbn_13 = isbn_13;
		}

		public Book getBook() {
			return book;
		}

		public void setBook(Book book) {
			this.book = book;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getAuthor() {
			return author;
		}

		public void setAuthor(String author) {
			this.author = author;
		}

		public double getPrice() {
			return price;
		}

		public void setPrice(double price) {
			this.price = price;
		}
		
	}
