package expense.tracker.app.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


//import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import expense.tracker.app.model.Expense;
import expense.tracker.app.model.User;

@Repository
public class ExpenseRepository {

	//private static Logger LOGGER=Logger.getLogger(ExpenseRepository.class);
	
	@PersistenceContext
	EntityManager em;

	public List<Expense> searchExpenseByDate(String username, Date fromDate,
			Date toDate, int pageNmbr) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Expense> searchQuery = cb.createQuery(Expense.class);
		Root<Expense> searchRoot = searchQuery.from(Expense.class);
		searchQuery.select(searchRoot);
		searchQuery.where(getWhereCondition(cb, searchRoot, username, fromDate,
				toDate));
		TypedQuery<Expense> filterquery = em.createQuery(searchQuery)
				.setFirstResult((pageNmbr - 1) * 10).setMaxResults(10);
		return filterquery.getResultList();
	}
	
	public Long countSearchResult(String username, Date fromDate,
			Date toDate, int pageNmbr) {
		CriteriaBuilder cb=em.getCriteriaBuilder();
		CriteriaQuery<Long> countQuery=cb.createQuery(Long.class);
		Root<Expense> countRoot=countQuery.from(Expense.class);
		countQuery.multiselect(cb.count(countRoot));
		countQuery.where(getWhereCondition(cb, countRoot, username, fromDate, toDate));
		Long result=em.createQuery(countQuery).getSingleResult();
		return result;
	}

	public void delete(Long id) {
		Expense ex=em.find(Expense.class, id);
		em.remove(ex);
	}
	
	public Expense findExpenseById(Long id) {
		return em.find(Expense.class, id);
	}
	
	
	
	private Predicate[] getWhereCondition(CriteriaBuilder cb,
			Root<Expense> searchRoot, String username, Date fromDate,
			Date toDate) {
		// TODO Auto-generated method stub
		List<Predicate> predicates = new ArrayList<Predicate>();
		Join<Expense, User> user = searchRoot.join("user");
		predicates.add(cb.equal(user.get("name"), username));
		predicates.add(cb.greaterThanOrEqualTo(searchRoot.<Date> get("date"),
				fromDate));
		predicates.add(cb.lessThanOrEqualTo(searchRoot.<Date> get("date"),
				toDate));
		return predicates.toArray(new Predicate[] {});
	}

	public Expense save(Expense ex) {
		return em.merge(ex);
	}
	
	
}
