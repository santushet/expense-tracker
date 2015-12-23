package expense.tracker.app;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import sun.security.acl.PrincipalImpl;
import expense.tracker.app.dao.ExpenseRepository;
import expense.tracker.app.model.Expense;
import expense.tracker.config.root.RootContextConfig;
import expense.tracker.config.root.TestConfiguration;
import expense.tracker.config.servlet.ServletContextConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ActiveProfiles("test")
@ContextConfiguration(classes={TestConfiguration.class, RootContextConfig.class, ServletContextConfig.class})
public class ExpenseRestWebServiceTest {

    private MockMvc mockMvc;

    @Autowired
    private ExpenseRepository expenseRepo;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void init()  {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

/*    @Test
    public void testSearchMealsByDate() throws Exception {
        mockMvc.perform(get("/expense")
                .param("fromDate", "2015/12/11")
                .param("toDate", "2015/12/14")
        		.param("fromDate", "2015/12/21")
                .param("toDate", "2015/12/22")
                .param("pageNumber", "1")
                .accept(MediaType.APPLICATION_JSON)
                .principal(new PrincipalImpl("test123")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.['expenses'].[0].['description']").value("test"));
                //.andExpect(jsonPath("$.['meals'].[0].['description']").value("2 -  Chickpea with roasted cauliflower"));
    }*/

/*    @Test
    public void testSaveMeals() throws Exception {
        mockMvc.perform(post("/expense")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[{ \"id\":\"1\",\"date\": \"2015/01/01\", \"description\": \"test\",\"amount\":\"100\" }]")
                .accept(MediaType.APPLICATION_JSON)
                .principal(new PrincipalImpl(UserServiceTest.USERNAME)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.[0].['description']").value("test"));
    }*/

    @Test
    public void deleteMeals() throws Exception {
        mockMvc.perform(delete("/expense")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[1]")
                .accept(MediaType.APPLICATION_JSON)
                .principal(new PrincipalImpl(UserServiceTest.USERNAME)))
                .andDo(print())
                .andExpect(status().isOk());
        
        Expense exp=expenseRepo.findExpenseById(1L);
        assertNull("not deleted",exp);

        
    }

}
