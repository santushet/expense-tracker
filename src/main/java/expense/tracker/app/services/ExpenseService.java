package expense.tracker.app.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import expense.tracker.app.dao.ExpenseRepository;
import expense.tracker.app.dao.UserRepository;
import expense.tracker.app.dto.ExpenseDTO;
import expense.tracker.app.model.Expense;
import expense.tracker.app.model.SearchResult;
import expense.tracker.app.model.User;

@Service
public class ExpenseService {

	@Autowired
	ExpenseRepository expenseRepo;

	@Autowired
	UserRepository userRepo;

	@Transactional(readOnly = true)
	public SearchResult<Expense> searchByDate(String name, Date fromDate,
			Date toDate, int pageNmbr) {
		if (fromDate == null || toDate == null)
			throw new IllegalArgumentException("Both inputs are required");
		List<Expense> list = expenseRepo.searchExpenseByDate(name, fromDate,
				toDate, pageNmbr);
	Long count = expenseRepo.countSearchResult(name, fromDate, toDate,
				pageNmbr);
				;
		return new SearchResult<>(count, list);
	}

	@Transactional
	public void deleteExpenses(List<Long> ids) {
		if(ids==null)
			throw new IllegalArgumentException();
		//ids.stream().forEach((id)->expenseRepo.deleteExpense(id));
		ids.stream().forEach((id)->expenseRepo.delete(id));
		//List<Long> newlist=new ArrayList<Long>(ids);
	
		//newlist.clear();
		
		
	}
	@Transactional	
	public Expense saveExpense(String name,Long id,Date date,String description, float amount) {
		Expense ex=null;
		if(id!=null){
			ex=expenseRepo.findExpenseById(id);
			ex.setAmount(amount);
			ex.setDate(date);
			ex.setDescription(description);
			
		}
		else{
			User user=userRepo.findUserByName(name);
			ex=expenseRepo.save(new Expense(user, amount, description, date));
			
		}
		return ex;
	}
	@Transactional
	public List<Expense> saveExpenses(String username,List<ExpenseDTO> expenses) {
		return expenses.stream().map((expense)->saveExpense(username,expense.getId(),expense.getDate(),expense.getDescription(),expense.getAmount())).collect(Collectors.toList());
	}
	
	
	
}
