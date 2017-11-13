package edu.byu.cs.familymapserver.databaseTests;

/**
 * Created by christian on 3/3/17.
 */

import org.junit.*;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.familymapserver.database.Database;
import edu.byu.cs.familymapserver.model.User;

import static org.junit.Assert.assertEquals;

public class UserDAOTest {

    private Database db;
    private User user;
    private User user1;
    private User user2;
    private List<User> users = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        db = new Database();
        db.openConnection("testdb.sqlite");
        db.clear();
        user = new User("jcleish","chicken","jcleish@gmail.com","j","c","m","12345678");
        user1 = new User("name1","pass","email","ken","ken","f","as;dkfj");
        user2 = new User("name2","pass","email","ken","ken","f",";lkjfdsa");
        users.add(user);
        users.add(user1);
        users.add(user2);
    }

    @After
    public void tearDown() throws Exception {
        db.closeConnection(false);
        db = null;
    }

    @Test
    public void testAddUser() throws Exception {
        System.out.print("Testing userDAO...");
        db.userDAO.addUser(user);
        db.userDAO.addUser(user1);
        db.userDAO.addUser(user2);
        assertEquals(db.userDAO.getUserByID("12345678").getUsername(),user.getUsername());
        assertEquals(db.userDAO.getUserByUsername("name1").getUsername(),user1.getUsername());
        User newUser = db.userDAO.getUserByUsername("shite");
        User newUser2 = db.userDAO.getUserByUsername("name1");
        assertEquals(newUser2.getPassword(), user1.getPassword());
        assertEquals(newUser.getUsername(), null);
        db.userDAO.getAllUsers();
        System.out.println("PASSED");
    }

    @Test
    public void testAddUsers() throws Exception{
        System.out.print("Testing userDAO.addUsers...");
        db.clear();
        db.userDAO.addUsers(users);
        assertEquals(db.userDAO.getUserByID("12345678").getUsername(),user.getUsername());
        assertEquals(db.userDAO.getUserByUsername("name1").getUsername(),user1.getUsername());
        User newUser = db.userDAO.getUserByUsername("shite");
        User newUser2 = db.userDAO.getUserByUsername("name1");
        assertEquals(newUser2.getPassword(), user1.getPassword());
        assertEquals(newUser.getUsername(), null);
        System.out.println("PASSED");
    }
}