package expense.tracker.app.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import expense.tracker.app.dto.NewUserDTO;
import expense.tracker.app.dto.UserInfoDTO;
import expense.tracker.app.model.User;
import expense.tracker.app.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	//private static final Logger LOGGER=Logger.getLogger(UserController.class);
	
	@Autowired
	UserService userService;
	
	@ResponseBody
	@RequestMapping(method=RequestMethod.GET)
	public UserInfoDTO getUserInfo(Principal principal)
	{
		User user=userService.findUserByUserName(principal.getName());
		Long todaysExpense=userService.findTodaysExpenseForUser(user.getName());
		return new UserInfoDTO(user.getName(), todaysExpense);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public void createNewUser(@RequestBody NewUserDTO user){
		userService.createUser(user.getUsername(), user.getEmail(), user.getPlainTextPassword());
	}
	
	 @ExceptionHandler(Exception.class)
	    public ResponseEntity<String> errorHandler(Exception exc) {
	      //  LOGGER.error(exc.getMessage(), exc);
	        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
	    }
}
