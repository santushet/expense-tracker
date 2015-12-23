package expense.tracker.app.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="EXPENSES")
public class Expense  {

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Expense() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Id
	@GeneratedValue
	@Column(name="EXPENSE_ID")
	private Long id;
	@ManyToOne
	private User user;
	
	private float amount;
	private String description;
	private Date date;
	public Expense(User user, float amount, String description, Date date) {
		super();
		this.user = user;
		this.amount = amount;
		this.description = description;
		this.date = date;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
