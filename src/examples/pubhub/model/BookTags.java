package examples.pubhub.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tags")
public class BookTags {

	@Id
	private String isbn13;	// International Standard Book Number, unique
	@Column
	private String title;
	@Column
	private String tags;
	// Constructor used when a isbn is specified
		public BookTags(String isbn, String title, String tags) {
			this.setIsbn13(isbn);
			this.setTitle(title);
			this.setTags(tags);
		}
		
		// Default constructor
		public BookTags() {
			this.setIsbn13(null);
			this.setTitle(null);
			this.setTags(null);
		}

		public String getIsbn13() {
			return isbn13;
		}

		public void setIsbn13(String isbn13) {
			this.isbn13 = isbn13;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getTags() {
			return tags;
		}

		public void setTags(String tags2) {
			this.tags = tags2;
		}


}
