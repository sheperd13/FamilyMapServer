package edu.byu.cs.familymapserver.facade.facade_objects;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.familymapserver.model.Person;

/**
 * Created by christian on 10/13/17.
 */

/**
 * contents of a person result which, if successful, returns a person else an error message
 */
public class PersonResult {
    List<Person> data;
    String message;

    public PersonResult(){
        data = new ArrayList<>();
        message = null;
    }

    public PersonResult(String message){
        data = new ArrayList<>();
        this.message = message;
    }

    public PersonResult(List<Person> data){
        this.data = data;
        message = null;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public void setData(List<Person> data){
        this.data = data;
    }

    public List<Person> getData() {
        return data;
    }

}
