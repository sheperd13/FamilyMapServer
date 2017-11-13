package edu.byu.cs.familymapserver.facade.facade_objects;

import org.json.JSONObject;

import edu.byu.cs.familymapserver.model.User;

/**
 * Created by christian on 2/16/17.
 * Takes in a JSON request and parses it into variables that can be used
 */

public class LoginRequest {
    String userName;
    String password;

    public LoginRequest(){
        userName = null;
        password = null;
    }

    public LoginRequest(User user){
        this.userName = user.getUsername();
        this.password = user.getPassword();
    }

    public LoginRequest(String userName, String password){
        this.userName = userName;
        this.password = password;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
