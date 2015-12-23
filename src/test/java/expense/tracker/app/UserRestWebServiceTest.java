package expense.tracker.app;


import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import expense.tracker.app.dao.UserRepository;
import expense.tracker.app.model.User;
import expense.tracker.config.root.RootContextConfig;
import expense.tracker.config.root.TestConfiguration;
import expense.tracker.config.servlet.ServletContextConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ActiveProfiles("test")
@ContextConfiguration(classes={TestConfiguration.class, RootContextConfig.class, ServletContextConfig.class})
public class UserRestWebServiceTest {

    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void init()  {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
/*
    @Test
    public void testUpdateMaxCalories() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"testing123\", \"plainTextPassword\": \"Password5\", \"email\": \"test@gmail.com\"}")
                .accept(MediaType.APPLICATION_JSON)
                .principal(new PrincipalImpl(UserServiceTest.USERNAME)))
                .andDo(print())
                .andExpect(status().isOk());

        User user = userRepository.findUserByName(UserServiceTest.USERNAME);
        //assertTrue("max calories not updated" + user.getMaxCaloriesPerDay(), user.getMaxCaloriesPerDay() == 200);
        assertTrue("user is incorrect "+user.getName(), user.getName()=="testing123");
    }
*/
    @Test
    public void testCreateUser() throws Exception {
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"testing123\", \"plainTextPassword\": \"Password5\", \"email\": \"test@gmail.com\"}")
                .accept(MediaType.APPLICATION_JSON)
                .principal(new PrincipalImpl(UserServiceTest.USERNAME)))
                .andDo(print())
                .andExpect(status().isOk());

        User user = userRepository.findUserByName("testing123");
        assertTrue("email not correct: " + user.getEmail(), "test@gmail.com".equals(user.getEmail()));
    }


}
