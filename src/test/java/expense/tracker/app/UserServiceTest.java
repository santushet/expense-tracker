package expense.tracker.app;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import expense.tracker.app.model.User;
import expense.tracker.app.services.UserService;
import expense.tracker.config.root.RootContextConfig;
import expense.tracker.config.root.TestConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes={TestConfiguration.class, RootContextConfig.class})
public class UserServiceTest {

    public static final String USERNAME = "test123";

    @Autowired
    private UserService userService;

    @PersistenceContext
    private EntityManager em;

  /*  @Test
    public void testUpdateUserMaxCaloriesPerDay() {

        userService.updateUserMaxCaloriesPerDay("test123", 300L);

        User user = findUserByUsername(USERNAME);
        assertTrue("The user calories where not updated: " + user.getMaxCaloriesPerDay(),
                user.getMaxCaloriesPerDay() == 300L);
    }*/

    @Test
    public void testFindUserByUsername() {
        User user = findUserByUsername(USERNAME);
        assertNotNull("User is mandatory",user);
        assertTrue("Unexpected user " + user.getName(), user.getName().equals(USERNAME));
    }

    @Test
    public void testUserNotFound() {
        User user = findUserByUsername("doesnotexist");
        assertNull("User must be null", user);
    }

    @Test
    public void testCreateValidUser() {
        userService.createUser("test456", "test@gmail.com","password");
        User user = findUserByUsername("test456");

        assertTrue("username not expected " + user.getName(), "test456".equals(user.getName()) );
        assertTrue("email not expected " + user.getEmail(), "test@gmail.com".equals(user.getEmail()) );

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        assertTrue("password not expected " + user.getPassword(), passwordEncoder.matches("password",user.getPassword()) );
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBlankUser() {
        userService.createUser("", "test@gmail.com","Password3");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUsernameLength() {
        userService.createUser("test", "test@gmail.com","Password3");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUsernameAvailable() {
        userService.createUser("test123", "test@gmail.com","Password3");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBlankEmail() {
        userService.createUser("test001", "","Password3");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidEmail() {
        userService.createUser("test001", "test","Password3");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBlankPassword() {
        userService.createUser("test002", "test@gmail.com","");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPasswordPolicy() {
        userService.createUser("test003", "test@gmail.com","Password");
    }


    private User findUserByUsername(String username) {
        List<User> users = em.createQuery("select u from User u where name = :username")
                .setParameter("username", username).getResultList();

        return users.size() == 1 ? users.get(0) : null;
    }
}
