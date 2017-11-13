package edu.byu.cs.familymapserver.facade.facade_objects;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.familymapserver.model.Event;

/**
 * Created by christian on 10/13/17.
 */

/**
 * contents of an EventResult which is an array of events or an error message
 */
public class EventResult {
    List<Event> data;
    String message;

    public EventResult(){
        data = new ArrayList<>();
        message = null;
    }

    public EventResult(String message){
        data = new ArrayList<>();
        this.message = message;
    }

    public EventResult(List<Event> data){
        this.data = data;
        message = null;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public void setData(List<Event> data){
        this.data = data;
    }

    public List<Event> getData() {
        return data;
    }

}
