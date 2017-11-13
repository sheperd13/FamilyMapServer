package edu.byu.cs.familymapserver.facade.facade_objects;

/**
 * Created by christian on 10/13/17.
 */

import edu.byu.cs.familymapserver.model.Event;

/**
 * contents of an eventID result which is just an event or error message
 */
public class EventIDResult {
    private Event event;
    private String message;

    public EventIDResult(){
        event = new Event();
        message = null;
    }

    public EventIDResult(Event event){
        this.event = event;
    }

    public EventIDResult(String message){
        this.message = message;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
