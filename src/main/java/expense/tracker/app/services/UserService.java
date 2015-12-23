package expense.tracker.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import expense.tracker.app.dao.UserRepository;
import expense.tracker.app.model.User;

@Service
public class UserService {

	@Autowired
	UserRepository userRepo;
	
	@Transactional
	public void createUser(String name, String email, String password) {
		if(!userRepo.isNameAvailable(name))
			throw new IllegalArgumentException("Username is already available");
		User user=new User(name, email, password);
		userRepo.save(user);
	}
	
	@Transactional
	public User findUserByUserName(String name) {
		return userRepo.findUserByName(name);
	}
	
	@Transactional
	public Long findTodaysExpenseForUser(String name)
	{
		return userRepo.findTodaysTotalCountForUser(name);
	}
	
	
}
