package edu.byu.cs.familymapserver.facade.facade_objects;

/**
 * Created by christian on 10/13/17.
 */

/**
 * contains the contents of a Load result
 */
public class LoadResult {
    String message;

    public LoadResult(){
        message = null;
    }

    public LoadResult(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }
}
