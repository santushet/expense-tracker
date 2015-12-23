package expense.tracker.app.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;

import expense.tracker.app.model.Expense;

public class ExpenseDTO {
	private Long id;
	
	@JsonFormat(pattern="yyyy/MM/dd", timezone="IST")
	private Date date;
	
	private String description;
	private float amount;
	public ExpenseDTO(Long long1, Date date, String description, float amount) {
		super();
		this.id = long1;
		this.date = date;
		this.description = description;
		this.amount = amount;
	}
	
	public ExpenseDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static ExpenseDTO mapFromExpenseEntity(Expense ex) {
		return new ExpenseDTO(ex.getId(), ex.getDate(), ex.getDescription(), ex.getAmount());
	}
	
	public static List<ExpenseDTO> mapFromExpenseEntities(List<Expense> expenses) {
		return expenses.stream().map((ex)->mapFromExpenseEntity(ex)).collect(Collectors.toList());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}
	
}
