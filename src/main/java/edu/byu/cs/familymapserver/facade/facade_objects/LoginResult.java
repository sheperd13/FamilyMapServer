package edu.byu.cs.familymapserver.facade.facade_objects;

/**
 * Created by christian on 2/16/17.
 * Spits out the result of the register request in JSON format
 */

public class LoginResult {
    String authToken;
    String userName;
    String personID;
    String message;

    public LoginResult(){
        authToken = null;
        userName = null;
        personID = null;
        message = null;
    }

    public LoginResult(String error){
        authToken = null;
        userName = null;
        personID = null;
        this.message = error;
    }

    public boolean hasError(){
        if(message == null){
            return false;
        } else return true;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String userName) {
        this.userName = userName;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getError() {
        return message;
    }

    public void setError(String message) {
        this.message = message;
    }

}
