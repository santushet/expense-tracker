package expense.tracker.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
/*@NamedQueries({
		@NamedQuery(name = User.FIND_BY_USERNAME, query = "select u from User u where name=:name"),
		@NamedQuery(name = User.TODAY_TOTAL_EXPENSE, query = "select sum(amount) from Expense e where e.name=:name and e.date=CURRENT_DATE") })
*/public class User {

	@Id
	@GeneratedValue
	@Column(name="USER_ID")
	private Long id;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

/*public static final String FIND_BY_USERNAME = "user.findByUserName";
	public static final String TODAY_TOTAL_EXPENSE = "user.todayTotalExpense";
*/	private String name;
	private String email;
	private String password;

	public User(String name, String email, String password) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
