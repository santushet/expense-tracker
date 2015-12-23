package expense.tracker.app;



import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import expense.tracker.app.model.Expense;
import expense.tracker.app.model.SearchResult;
import expense.tracker.app.services.ExpenseService;
import expense.tracker.config.root.RootContextConfig;
import expense.tracker.config.root.TestConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes={TestConfiguration.class, RootContextConfig.class})
public class ExpenseServiceTest {

    @Autowired
    private ExpenseService expenseService;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void testFindMealsByDate() {
        SearchResult<Expense> result = expenseService.searchByDate(UserServiceTest.USERNAME, new Date(System.currentTimeMillis()-24*60*60*1000), new Date(), 1);
        assertTrue("results not expected, total " + result.getResultsCount(), result.getResultsCount() == 2);
    }

    
    @Test(expected = IllegalArgumentException.class)
    public void fromDateNull() {
    	expenseService.searchByDate(UserServiceTest.USERNAME, null, new Date(),  1);
    }

    
    @Test
    public void deleteMeals() {
    	expenseService.deleteExpenses(Arrays.asList(1L));
        Expense expense = em.find(Expense.class, 1L);
        assertNull("meal was not deleted" , expense);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteMealsNull() {
    	expenseService.deleteExpenses(null);
    }

   /* @Test
    public void saveMeals() {
    	ExpenseDTO meal1 = mapFromExpenseEntity(em.find(Expense.class, 1L));
        MealDTO meal2 = mapFromMealEntity(em.find(Expense.class, 2L));

        meal1.setDescription("test1");
        meal2.setCalories(10L);

        List<MealDTO> meals = Arrays.asList(meal1, meal2);

        mealService.saveMeals(UserServiceTest.USERNAME, meals);


        Meal m1 = em.find(Meal.class, 1L);
        assertTrue("description not as expected: " + m1.getDescription(), "test1".equals(m1.getDescription()));

        Meal m2 = em.find(Meal.class, 2L);
        assertTrue("calories not as expected: " + m2.getCalories(), m2.getCalories() == 10L);
    }*/


}
