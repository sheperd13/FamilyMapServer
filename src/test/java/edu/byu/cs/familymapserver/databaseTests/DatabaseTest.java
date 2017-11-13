package edu.byu.cs.familymapserver.databaseTests;

/**
 * Created by christian on 3/3/17.
 */

import org.junit.* ;

import edu.byu.cs.familymapserver.database.Database;

import static junit.framework.Assert.assertEquals;

public class DatabaseTest {

    private Database db;

    @Before
    public void setUp() throws Exception {
        db = new Database();
        db.openConnection("testdb.sqlite");
        db.createTables();
    }

    @After
    public void tearDown() throws Exception {
        db.closeConnection(false);
        db = null;
    }

    @Test
    public void testClear() throws Exception {
        System.out.print("Testing Database.clear()...");
        db.clear();
        assert(!db.userDAO.getAllUsers().iterator().hasNext());
        System.out.println("PASSED");
    }
}