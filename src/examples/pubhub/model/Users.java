package examples.pubhub.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="PUBHUBUSERS")
public class Users {
	@Id
	private String username;
	@Column
	private String password;
	@Column
	private String activeUser;
	@OneToOne
	@JoinColumn(name="username")
	private PurchasedBook user;
	// Constructor Overloaded
	public Users(String username, String password, String active){
		this.username = username;
		this.password = password;
		this.activeUser = active;
	}
	
	//Default Constructor 
	public Users (){
		this.username = null;
		this.password = null;
		this.activeUser = null;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public String getActive() {
		return activeUser;
	}

	public void setActive(String active) {
		this.activeUser = active;
	}

	@Override
	public String toString() {
		return "Users [username=" + username + ", password=" + password + ", active=" + activeUser + "]";
	}

	
	
}
