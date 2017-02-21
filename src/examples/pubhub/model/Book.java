package examples.pubhub.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="BOOKS")
public class Book implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private String isbn_13;// International Standard Book Number, unique
	@OneToOne
	@JoinColumn(name="isbn_13")
	private BookList booklist;
	@Column
	private String title;
	@Column
	private String author;
	@Column
	private LocalDate publishDate;	// Date of publish to the website
	@Column
	private double price;
	@Column
	private byte[] content;
	

	// Constructor used when no date is specified
	public Book(String isbn, String title, String author, byte[] content) {
		this.isbn_13 = isbn;
		this.title = title;
		this.author = author;
		this.publishDate = LocalDate.now();
		this.content = content;
	}
	
	// Constructor used when a date is specified
	public Book(String isbn, String title, String author, byte[] content, LocalDate publishDate) {
		this.isbn_13 = isbn;
		this.title = title;
		this.author = author;
		this.publishDate = publishDate;
		this.content = content;
	}
	
	// Default constructor
	public Book() {
		this.isbn_13 = null;
		this.title = null;
		this.author = null;
		this.publishDate = LocalDate.now();
		this.content = null;
	}
	
	public String getIsbn13() {
		return isbn_13;
	}

	public void setIsbn13(String isbn13) {
		this.isbn_13 = isbn13;
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

	public LocalDate getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(LocalDate publishDate) {
		this.publishDate = publishDate;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public BookList getBooklist() {
		return booklist;
	}

	public void setBooklist(BookList booklist) {
		this.booklist = booklist;
	}
	
	
}
