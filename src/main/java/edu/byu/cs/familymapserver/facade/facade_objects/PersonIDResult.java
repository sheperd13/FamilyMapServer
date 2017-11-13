package edu.byu.cs.familymapserver.facade.facade_objects;

import edu.byu.cs.familymapserver.model.Person;

/**
 * Created by christian on 10/13/17.
 */

/**
 * contents of a person result which, if successful, returns a person else an error message
 */
public class PersonIDResult {
    Person person;
    String message;

    public PersonIDResult(){
        person = new Person();
        message = null;
    }

    public PersonIDResult(String message){
        this.message = message;
    }

    public PersonIDResult(Person person){
        this.person = person;
    }

    public void setPerson(Person person){
        this.person = person;
    }

    public Person getPerson(){
        return person;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
