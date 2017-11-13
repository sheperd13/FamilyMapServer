package edu.byu.cs.familymapserver.model;

import edu.byu.cs.familymapserver.facade.facade_objects.RegisterRequest;
import edu.byu.cs.familymapserver.util.RandomID;

/**
 * person class. this class contains the information necessary to create a valid person in the database
 */
public class Person {
	private transient int id_length = 8;
	private transient int generation = 0;
	private transient String motherFirstName = null;
	private transient String motherLastName = null;
	private transient String fatherFirstName = null;
	private String descendant;
	private String personID;
	private String firstName;
	private String lastName;
	private String gender;
	private String father; //(possibly null)
	private String mother; //(possibly null)
	private String spouse; //(possibly null)

	/**
	 * default constructor
	 */
	public Person(){
		RandomID rand = new RandomID();
		personID = rand.genRandomID(id_length);
		descendant = null;
		firstName = null;
		lastName = null;
		gender = null;
		father = null;
		mother = null;
		spouse = null;
	}

	public Person(String personID){
		this.personID = personID;
		descendant = null;
		firstName = null;
		lastName = null;
		gender = null;
		father = null;
		mother = null;
		spouse = null;
	}

	/**
	 * This creates a person with the given information
	 * @param personID
	 * @param descendant
	 * @param firstName
	 * @param lastName
	 * @param gender
	 * @param father
	 * @param mother
	 * @param spouse
	 */
	public Person(String personID, String descendant, String firstName, String lastName,
				  String gender, String father, String mother, String spouse){
		this.personID = personID;
		this.descendant = descendant;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.father = father;
		this.mother = mother;
		this.spouse = spouse;
	}

	/**
	 * creates a person to match the given user
	 * @param user
	 */
	public Person(User user){
		RandomID rand = new RandomID();
		personID = rand.genRandomID(id_length);
		descendant = user.getUsername();
		firstName = user.getFirstName();
		lastName = user.getLastName();
		gender = user.getGender();
	}

	/**
	 * creates a person with a register request
	 * @param request
	 */
	public Person(RegisterRequest request){
		RandomID rand = new RandomID();
		personID = rand.genRandomID(id_length);
		descendant = request.getUsername();
		firstName = request.getFirstName();
		lastName = request.getLastName();
		gender = request.getGender();
	}

	public String hasError(){
		if(personID.equals("") || descendant.equals("") || firstName.equals("") || lastName.equals("") ||
				gender.equals("")){
			return "person request contains a null value";
		}

		if(!gender.equals("M") && !gender.equals("m") && !gender.equals("F") && !gender.equals("f")){
			return "invalid person gender";
		}

		return null;
	}
	
	public String getPersonID() {
		return personID;
	}
	
	public void setPersonID(String personID) {
		this.personID = personID;
	}
	
	public String getDescendant() {
		return descendant;
	}
	
	public void setDescendant(String descendant) {
		this.descendant = descendant;
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
	
	public String getFather() {
		return father;
	}
	
	public void setFather(String father) {
		this.father = father;
	}
	
	public String getMother() {
		return mother;
	}
	
	public void setMother(String mother) {
		this.mother = mother;
	}
	
	public String getSpouse() {
		return spouse;
	}
	
	public void setSpouse(String spouse) {
		this.spouse = spouse;
	}

	public String getMotherFirstName() {
		return motherFirstName;
	}

	public void setMotherFirstName(String motherFirstName) {
		this.motherFirstName = motherFirstName;
	}

	public String getMotherLastName() {
		return motherLastName;
	}

	public void setMotherLastName(String motherLastName) {
		this.motherLastName = motherLastName;
	}

	public String getFatherFirstName() {
		return fatherFirstName;
	}

	public void setFatherFirstName(String fatherFirstName) {
		this.fatherFirstName = fatherFirstName;
	}

	public int getGeneration() {
		return generation;
	}

	public void setGeneration(int generation) {
		this.generation = generation;
	}
}
