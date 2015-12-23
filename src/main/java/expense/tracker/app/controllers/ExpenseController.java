package expense.tracker.app.controllers;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import expense.tracker.app.dto.ExpenseDTO;
import expense.tracker.app.dto.ExpensesDTO;
import expense.tracker.app.model.Expense;
import expense.tracker.app.model.SearchResult;
import expense.tracker.app.services.ExpenseService;

@Controller
@RequestMapping("/expense")
public class ExpenseController {
	  private static final long DAY_IN_MS = 1000 * 60 * 60 * 24;
	
	Logger LOGGER=Logger.getLogger(ExpenseController.class);
	
	@Autowired
	ExpenseService expenseService;
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method = RequestMethod.GET)
	public ExpensesDTO searchExpenseByDate(
			Principal principal,
			@RequestParam(value="fromDate", required=false) @DateTimeFormat(pattern="yyyy/MM/dd") Date fromDate,
			@RequestParam(value="toDate", required=false) @DateTimeFormat(pattern="yyyy/MM/dd") Date toDate,
			@RequestParam(value="pageNumber") Integer pageNmbr){
		 if (fromDate == null && toDate == null) {
	            fromDate = new Date(System.currentTimeMillis() - (3 * DAY_IN_MS));
	            toDate = new Date();
	        }
		SearchResult<Expense> result=expenseService.searchByDate(principal.getName(), fromDate, toDate, pageNmbr);
		
		Long resultCount=result.getResultsCount();
		
		Long totalPages=resultCount/10;
		if(totalPages%10>0){
			totalPages++;
		}
		
		return new ExpensesDTO(pageNmbr, totalPages, ExpenseDTO.mapFromExpenseEntities(result.getResult()));
		
	}
	
	@ResponseBody
	@RequestMapping(method=RequestMethod.POST)
	public List<ExpenseDTO> saveExpenses(Principal principal,@RequestBody List<ExpenseDTO> expenses) {
		List<Expense> result=expenseService.saveExpenses(principal.getName(), expenses);
		
		return result.stream().map(ExpenseDTO::mapFromExpenseEntity).collect(Collectors.toList());
		
	}
	
	@ResponseBody
	@RequestMapping(method=RequestMethod.DELETE)
	public void deleteExpenses(@RequestBody List<Long> deleteIds) {
		expenseService.deleteExpenses(deleteIds);
	}
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> errorHandler(Exception ex){
		//LOGGER.error(ex.getMessage(),ex);
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
