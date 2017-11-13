package edu.byu.cs.familymapserver.databaseTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.familymapserver.database.Database;
import edu.byu.cs.familymapserver.model.Person;

import static org.junit.Assert.assertEquals;

/**
 * Created by christian on 3/7/17.
 */

public class PersonDAOTest {
    private Database db;
    private Person person;
    private Person person1;
    private Person person2;
    private List<Person> people = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        db = new Database();
        db.openConnection("testdb.sqlite");
        db.clear();
        person = new Person();
        person1 = new Person("dumb","dumber","poop","free","m","butt","burrito","toilet");
        person2 = new Person("dumb1","dumber1","poop1","free1","m1","butt1","burrito1","toilet1");;
        people.add(person);
        people.add(person1);
        people.add(person2);
    }

    @After
    public void tearDown() throws Exception {
        db.closeConnection(false);
        db = null;
    }

    @Test
    public void testAddPerson() throws Exception {
        System.out.print("Testing personDAO...");
        db.personDAO.addPerson(person1);
        db.personDAO.addPerson(person2);
        assertEquals(db.personDAO.getPerson("dumb").getPersonID(),person1.getPersonID());
        System.out.println("PASSED");
    }
}
