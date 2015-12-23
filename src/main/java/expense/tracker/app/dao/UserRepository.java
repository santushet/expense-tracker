
package expense.tracker.app.dao;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import expense.tracker.app.model.User;

@Repository
public class UserRepository{

	@PersistenceContext
	EntityManager em;

	public boolean isNameAvailable(String name) {
		//List<User> users=em.createNamedQuery(User.FIND_BY_USERNAME,User.class).setParameter("name", name).getResultList();
		//return users.isEmpty();
		return true;
		
		/*List<User> users=getSession().getNamedQuery(User.FIND_BY_USERNAME).setParameter("name", name).list();
		return users.isEmpty();*/
	}
	
	public Long findTodaysTotalCountForUser(String name) {
		//return (Long) em.createNamedQuery(User.TODAY_TOTAL_EXPENSE).setParameter("name", name).getSingleResult();
	//	return (Long) getSession().getNamedQuery(User.TODAY_TOTAL_EXPENSE).setParameter("name", name).uniqueResult();
		//String query="select sum(amount) from Expense e, User u where u.name=:name and e.date=CURRENT_DATE";
		
		return null;
	}
	
	
	public void save(User user) {
		em.merge(user);
		//getSession().save(user);
	}
	
	public User findUserByName(String name) {
//	return em.createNamedQuery(User.FIND_BY_USERNAME,User.class).setParameter("name", name).getSingleResult();
	//	return (User) getSession().getNamedQuery(User.FIND_BY_USERNAME).setParameter("name", name).uniqueResult();
	     @SuppressWarnings("unchecked")
		List<User> users = em.createQuery("select u from User u where name = :username")
	                .setParameter("username", name).getResultList();
	     return users.size() == 1 ? users.get(0) : null;
	}
}
  