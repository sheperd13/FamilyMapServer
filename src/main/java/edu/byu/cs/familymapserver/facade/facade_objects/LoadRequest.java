package edu.byu.cs.familymapserver.facade.facade_objects;

/**
 * Created by christian on 10/13/17.
 */

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.familymapserver.model.Event;
import edu.byu.cs.familymapserver.model.Person;
import edu.byu.cs.familymapserver.model.User;

/**
 * Contains the contents of a Load request
 */
public class LoadRequest {
    List<User> users;
    List<Person> persons;
    List<Event> events;

    public LoadRequest(){
        users = new ArrayList<>();
        persons = new ArrayList<>();
        events = new ArrayList<>();
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
