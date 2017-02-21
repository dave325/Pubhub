package examples.pubhub.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="purchased_books")
public class OrderHistory {
	
	@Id
	private String isbn_13;
	
	@Column
	private LocalDate purchase_date;
	
	public OrderHistory(){
		this.isbn_13 = null;
		this.purchase_date = LocalDate.now();
	}

	public String getIsbn_13() {
		return isbn_13;
	}

	public void setIsbn_13(String isbn_13) {
		this.isbn_13 = isbn_13;
	}

	public LocalDate getPurchase_date() {
		return purchase_date;
	}

	public void setPurchase_date(LocalDate purchase_date) {
		this.purchase_date = purchase_date;
	}
	
	
}
