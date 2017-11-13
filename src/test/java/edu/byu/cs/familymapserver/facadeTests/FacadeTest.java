package edu.byu.cs.familymapserver.facadeTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.byu.cs.familymapserver.database.Database;
import edu.byu.cs.familymapserver.facade.facade_main.Facade;
import edu.byu.cs.familymapserver.facade.facade_objects.LoginRequest;
import edu.byu.cs.familymapserver.facade.facade_objects.LoginResult;
import edu.byu.cs.familymapserver.facade.facade_objects.RegisterRequest;
import edu.byu.cs.familymapserver.facade.facade_objects.RegisterResult;
import edu.byu.cs.familymapserver.model.User;
import edu.byu.cs.familymapserver.util.DataGenerator;

import static junit.framework.Assert.assertEquals;

/**
 * Created by christian on 3/13/17.
 */

public class FacadeTest {
    private Database db;
    private Facade facade;

    User user;
    User user1;
    User user2;

    RegisterRequest request;
    RegisterRequest request1;
    RegisterRequest request2;
    RegisterResult result;
    RegisterResult result1;
    RegisterResult result2;

    LoginRequest logRequest;
    LoginRequest logRequest1;
    LoginRequest logRequest2;
    LoginRequest badLogin;
    LoginResult logResult;
    LoginResult logResult1;
    LoginResult logResult2;
    LoginResult badResult;

    @Before
    public void setUp() throws Exception {
        db = new Database();
        db.openConnection("testdb.sqlite");
        db.clear();
        facade  = new Facade(db);

//        user = new User();
        user1 = new User("name1","pass1","email1","ken1","ken1","f","as;dkfj");
        user2 = new User("name2","pass2","email2","ken2","ken2","m",";lkjfdsa");
//        request = new RegisterRequest(user);
        request1 = new RegisterRequest(user1);
        request2 = new RegisterRequest(user2);
//
//        logRequest = new LoginRequest();
//        logRequest1 = new LoginRequest(user1);
//        logRequest2 = new LoginRequest(user2);
//        badLogin = new LoginRequest("name1","pass");

        db.closeConnection(true);
    }

    @After
    public void tearDown() throws Exception {
        db = null;
    }

    @Test
    public void registerUserTest() throws Exception {
        System.out.print("Testing registerUser()...");
        result1 = facade.registerUser(request1);
        result2 = facade.registerUser(request2);
        assertEquals(result1.getUsername(), "name1");
        //assertEquals(result2.getUsername(), "name2");
        System.out.println("PASSED");
    }

    @Test
    public void loginTest() throws Exception {
        result1 = facade.registerUser(request1);
        result2 = facade.registerUser(request2);
        logResult1 = facade.login(logRequest1);
        logResult2 = facade.login(logRequest2);
        badResult = facade.login(badLogin);

        logResult1 = facade.login(logRequest1);
        logResult2 = facade.login(logRequest2);
    }

    @Test
    public void fillTest() throws Exception{
        facade.fill("sheila",-1);
        System.out.println("Data Stored");
    }
}
