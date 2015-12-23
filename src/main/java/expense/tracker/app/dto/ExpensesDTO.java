package expense.tracker.app.dto;

import java.util.List;

public class ExpensesDTO {

	private Integer currentPage;
	private Long totalPages;
	List<ExpenseDTO> expenses;
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Long getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(Long totalPages) {
		this.totalPages = totalPages;
	}
	public List<ExpenseDTO> getExpenses() {
		return expenses;
	}
	public void setExpenses(List<ExpenseDTO> expenses) {
		this.expenses = expenses;
	}
	public ExpensesDTO(Integer pageNmbr, Long totalPages,
			List<ExpenseDTO> expenses) {
		super();
		this.currentPage = pageNmbr;
		this.totalPages = totalPages;
		this.expenses = expenses;
	}
	
	
}
