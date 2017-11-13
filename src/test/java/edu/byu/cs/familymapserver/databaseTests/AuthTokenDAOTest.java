package edu.byu.cs.familymapserver.databaseTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.familymapserver.database.Database;
import edu.byu.cs.familymapserver.model.AuthToken;

import static org.junit.Assert.assertEquals;

/**
 * Created by christian on 3/13/17.
 */

public class AuthTokenDAOTest {
    private Database db;
    private AuthToken token;
    private AuthToken token1;
    private AuthToken token2;

    List<AuthToken> tokens = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        db = new Database();
        db.openConnection("testdb.sqlite");
        db.clear();
        token = new AuthToken();
        token1 = new AuthToken("thatguy");
        token2 = new AuthToken("jcleish");
        tokens.add(token);
        tokens.add(token1);
        tokens.add(token2);
    }

    @After
    public void tearDown() throws Exception {
        db.closeConnection(true);
        db = null;
    }

    @Test
    public void testAddAuthToken() {
        System.out.print("Testing authTokenDAO...");
        db.authTokenDAO.addToken(token);
        db.authTokenDAO.addToken(token1);
        db.authTokenDAO.addToken(token2);
        assertEquals(db.authTokenDAO.getToken("thatguy").getUsername(),token1.getUsername());
        System.out.println("PASSED");
    }
}
