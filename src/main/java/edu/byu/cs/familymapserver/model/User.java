package edu.byu.cs.familymapserver.model;

import edu.byu.cs.familymapserver.facade.facade_objects.RegisterRequest;

/**
 * user class. contains information required to create a valid user in the database.
 */
public class User {
	String userName;
	String password;
	String email;
	String firstName;
	String lastName;
	String gender;
	String personID;

	/**
	 * default constructor
	 */
	public User(){
		userName = null;
		password = null;
		email = null;
		firstName = null;
		lastName = null;
		gender = null;
		personID = null;
	}

	/**
	 * creates a useer with the given information
	 * @param userName
	 * @param password
	 * @param email
	 * @param firstName
	 * @param lastName
	 * @param gender
	 * @param personID
	 */
	public User(String userName, String password, String email, String firstName, String lastName,
				String gender, String personID){
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.personID = personID;
	}

	/**
	 * creates a user with a register request
	 * @param request
	 */
	public User(RegisterRequest request){
		this.userName = request.getUsername();
		this.password = request.getPassword();
		this.email = request.getEmail();
		this.firstName = request.getFirstName();
		this.lastName = request.getLastName();
		this.gender = request.getGender();
		this.personID = null;
	}

	public String hasError(){
		if(userName.equals("") || password.equals("") || email.equals("") || firstName.equals("") ||
				lastName.equals("") || gender.equals("") || personID.equals("")){
			return "user request contains a null value";
		}

		if(!gender.equals("M") && !gender.equals("m") && !gender.equals("F") && !gender.equals("f")){
			return "invalid user gender";
		}

		return null;
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
	
	public String getPersonID() {
		return personID;
	}
	
	public void setPersonID(String personID) {
		this.personID = personID;
	}
}
