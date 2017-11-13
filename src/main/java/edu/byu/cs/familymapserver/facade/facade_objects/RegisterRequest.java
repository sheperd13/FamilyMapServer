package edu.byu.cs.familymapserver.facade.facade_objects;

import edu.byu.cs.familymapserver.model.User;

/**
 * Created by christian on 2/16/17.
 */

public class RegisterRequest {
    String userName;
    String password;
    String email;
    String firstName;
    String lastName;
    String gender;

    /**
     * Takes in a JSON request and parses it into variables that can be used
     */
    public RegisterRequest(){
        userName = null;
        password = null;
        email = null;
        firstName = null;
        lastName = null;
        gender = null;
    }

    public RegisterRequest(User user){
        this.userName = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.gender = user.getGender();
    }

    public boolean hasNull(){
        if(userName.equals("") || password.equals("") || email.equals("") || firstName.equals("") ||
                lastName.equals("") || gender.equals("")){
            return true;
        }
        else return false;
    }

    public boolean validGender(){
        if(!gender.equals("M") && !gender.equals("m") && !gender.equals("F") && !gender.equals("f")){
            return false;
        }
        else return true;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
