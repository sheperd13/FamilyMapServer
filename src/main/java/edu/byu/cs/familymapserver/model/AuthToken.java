package edu.byu.cs.familymapserver.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.byu.cs.familymapserver.facade.facade_objects.RegisterRequest;
import edu.byu.cs.familymapserver.util.RandomID;

/**
 * AuthToken class which contains all of the information required to create a valid, unique authToken
 */
public class AuthToken {
	private String token;
	private int token_length = 10;
	private String userName;
	private String lastUsed;
	/**
	 * Generate a random, unique auth token
	 */
	public AuthToken(){
		RandomID rand = new RandomID();
		this.token = rand.genRandomID(token_length);
		this.userName = "";
		this.lastUsed = "";
	}

	/**
	 * Creates an authToken with the given request
	 * @param request
	 */
	public AuthToken(RegisterRequest request){
		RandomID rand = new RandomID();
		DateFormat df = new SimpleDateFormat("yyyy,MM/dd HH:mm:ss");
		Date date = new Date();
		this.token = rand.genRandomID(token_length);
		this.userName = request.getUsername();
		this.lastUsed = df.format(date);
	}

	/**
	 * creates an authToken for the user with the given username
	 * @param userName
	 */
	public AuthToken(String userName){
		RandomID rand = new RandomID();
		DateFormat df = new SimpleDateFormat("yyyy,MM/dd HH:mm:ss");
		Date date = new Date();
		this.token = rand.genRandomID(token_length);
		this.userName = userName;
		this.lastUsed = df.format(date);
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUsername() {
		return userName;
	}

	public void setUsername(String userName) {
		this.userName = userName;
	}

	public String getLastUsed() {
		return lastUsed;
	}

	public void setLastUsed(String lastUsed) {
		this.lastUsed = lastUsed;
	}
}
