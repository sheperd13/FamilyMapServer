package edu.byu.cs.familymapserver.databaseTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.familymapserver.database.Database;
import edu.byu.cs.familymapserver.model.Event;

import static org.junit.Assert.assertEquals;

/**
 * Created by christian on 3/7/17.
 */

public class EventsDAOTest {
    private Database db;
    private Event event;
    private Event event1;
    private Event event2;
    private List<Event> events = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        db = new Database();
        db.openConnection("testdb.sqlite");
        db.clear();
        event = new Event();
        event1 = new Event("pickle","desc","pers",(float)2.0,(float)2.0,"canada","dumbtown","gay","2001");
        event2 = new Event("pickle2","desc2","pers2",(float)3.0,(float)3.0,"c@nada","fagville","homo","2003");
        events.add(event);
        events.add(event1);
        events.add(event2);
    }

    @After
    public void tearDown() throws Exception {
        db.closeConnection(false);
        db = null;
    }

    @Test
    public void testAddEvent() throws Exception {
        System.out.print("Testing EventDAO...");
        db.eventDAO.addEvent(event1);
        db.eventDAO.addEvent(event2);
        assertEquals(db.eventDAO.getEvent("pickle").getEventID(),event1.getEventID());
        System.out.println("PASSED");
    }
}
