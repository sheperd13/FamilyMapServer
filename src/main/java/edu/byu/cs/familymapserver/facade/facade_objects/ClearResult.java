package edu.byu.cs.familymapserver.facade.facade_objects;

/**
 * Created by christian on 3/12/17.
 */

/**
 * Contains the results of a clear request
 */
public class ClearResult {
    String message;

    public ClearResult(){
        this.message = null;
    }

    public ClearResult(String message){
        this.message = message;
    }

    public void setMessage(String message){
        this.message = message;
    }
}
