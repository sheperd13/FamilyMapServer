package edu.byu.cs.familymapserver.facade.facade_objects;

/**
 * Created by christian on 3/12/17.
 */

/**
 * Contents of a Fill result
 */
public class FillResult {
    public String message;

    public FillResult(){
        message = null;
    }

    public FillResult(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
