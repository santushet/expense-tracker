package expense.tracker.app.dto;

public class UserInfoDTO {
	
	private String userName;
	private Long todaysExpenses;
	public UserInfoDTO(String username, Long todaysExpenses) {
		super();
		this.userName = username;
		this.todaysExpenses = todaysExpenses;
	}
	public String getUsername() {
		return userName;
	}
	public void setUsername(String username) {
		this.userName = username;
	}
	public Long getTodaysExpenses() {
		return todaysExpenses;
	}
	public void setTodaysExpenses(Long todaysExpenses) {
		this.todaysExpenses = todaysExpenses;
	}
	
}
