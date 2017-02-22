package examples.pubhub.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Shopping_Cart")
public class ShoppingCart {
	
	@Id
	private String isbn_13;
	
	@Column
	private double price;
	
	@Column
	private LocalDate purchase_date;
	
	
	public ShoppingCart(String isbn_13, Double price){
		this.isbn_13 = isbn_13;
		this.price = price;
		this.purchase_date = LocalDate.now();
	}
	
	public ShoppingCart(){
		this.isbn_13 = null;
		this.price = 0;
		this.purchase_date = LocalDate.now();
	}

	public String getIsbn_13() {
		return isbn_13;
	}
	public void setIsbn_13(String isbn_13) {
		this.isbn_13 = isbn_13;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public LocalDate getPurchase_date() {
		return purchase_date;
	}
	public void setPurchase_date(LocalDate purchase_date) {
		this.purchase_date = purchase_date;
	}
	
		
}
