package edu.byu.cs.familymapserver.model;

import edu.byu.cs.familymapserver.util.RandomID;

/**
 * event class. Contains information required to create a valid event in the database
 */
public class Event {
	private transient int idLength = 10;
	String descendant;
	String eventID;
	String personID;
	float latitude;
	float longitude;
	String country;
	String city;
	String eventType;
	String year;

	/**
	 * default constructor
	 */
	public Event(){
		RandomID rand = new RandomID();
		eventID = rand.genRandomID(idLength);
		descendant = null;
		personID = null;
		latitude = (float)0.0;
		longitude = (float)0.0;
		country = null;
		city = null;
		eventType = null;
		year = null;
	}

	/**
	 * creates an event with specific information
	 * @param eventID
	 * @param descendant
	 * @param personID
	 * @param latitude
	 * @param longitude
	 * @param country
	 * @param city
	 * @param eventType
	 * @param year
	 */
	public Event(String eventID, String descendant, String personID, float latitude, float longitude,
				 String country, String city, String eventType, String year){
		this.eventID = eventID;
		this. descendant = descendant;
		this.personID = personID;
		this.latitude = latitude;
		this.longitude = longitude;
		this.country = country;
		this.city = city;
		this.eventType = eventType;
		this.year = year;
	}

	public String hasError(){
		if(descendant.equals("") || eventID.equals("") || personID.equals("") ||
				country.equals("") || city.equals("")  || eventType.equals("") || year.equals("")){
			return "user request contains an empty value";
		}
		return null;
	}
	
	public String getEventID() {
		return eventID;
	}
	
	public void setEventID(String eventID) {
		this.eventID = eventID;
	}
	
	public String getDescendant() {
		return descendant;
	}

	public void setDescendant(String descendant) {
		this.descendant = descendant;
	}
	
	public String getPerson() {
		return personID;
	}
	
	public void setPerson(String personID) {
		this.personID = personID;
	}
	
	public float getLatitude() {
		return latitude;
	}
	
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	
	public float getLongitude() {
		return longitude;
	}
	
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getEventType() {
		return eventType;
	}
	
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	
	public String getYear() {
		return year;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
}
