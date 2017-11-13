package edu.byu.cs.familymapserver.database;

import org.sqlite.SQLiteException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.familymapserver.model.Person;

/**
 * accesses the person table in the database
 */
public class PersonDAO {
    private Database db;
    private Connection conn;

    /**
     * constructor. requires database
     * @param db
     */
    public PersonDAO(Database db){
        this.db = db;
        conn = null;
    }

    /**
     * adds a given person to the database
     * @param person
     */
    public void addPerson(Person person) throws Exception {

            PreparedStatement stmt = null;
            try{
                this.conn = db.getConn();
                String sql = "insert into person (personID, descendant, firstName, lastName, " +
                        "gender, father, mother, spouse) VALUES (?,?,?,?,?,?,?,?)";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1,person.getPersonID());
                stmt.setString(2,person.getDescendant());
                stmt.setString(3,person.getFirstName());
                stmt.setString(4,person.getLastName());
                stmt.setString(5,person.getGender());
                stmt.setString(6,person.getFather());
                stmt.setString(7,person.getMother());
                stmt.setString(8,person.getSpouse());
                if (stmt.executeUpdate() != 1) {
                    throw new Exception("addPerson failed: Could not add person");
                }
            }finally{
                stmt.close();
            }
    }

    /**
     * returns a person with the given personID
     * @param personID
     * @return
     */
    public Person getPerson(String personID){
        Person retPerson = new Person();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                this.conn = db.getConn();
                String sql = "select * from person where personID = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1,personID);
                rs = stmt.executeQuery();
                retPerson.setPersonID(rs.getString("personID"));
                retPerson.setDescendant(rs.getString("descendant"));
                retPerson.setFirstName(rs.getString("firstName"));
                retPerson.setLastName(rs.getString("lastName"));
                retPerson.setGender(rs.getString("gender"));
                retPerson.setFather(rs.getString("father"));
                retPerson.setMother(rs.getString("mother"));
                retPerson.setSpouse(rs.getString("spouse"));
            }finally{
                if(rs != null){
                    rs.close();
                }
                if(stmt != null) {
                    stmt.close();
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return retPerson;
    }

    /**
     * returns all persons in database
     * @return
     * @throws Exception
     */
    public List<Person> getAllPeople() throws Exception{
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from person";
                this.conn = db.getConn();
                stmt = conn.prepareStatement(sql);

                List<Person> people = new ArrayList<>();
                rs = stmt.executeQuery();
                while (rs.next()) {
                    Person tempPerson = new Person();
                    tempPerson.setPersonID(rs.getString(1));
                    tempPerson.setDescendant(rs.getString(2));
                    tempPerson.setFirstName(rs.getString(3));
                    tempPerson.setLastName(rs.getString(4));
                    tempPerson.setGender(rs.getString(5));
                    tempPerson.setFather(rs.getString(6));
                    tempPerson.setMother(rs.getString(7));
                    tempPerson.setSpouse(rs.getString(8));
                    people.add(tempPerson);
                }

                return people;
            }
            finally {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (Exception e) {
            throw new Exception("getAllPeople failed", e);
        }
    }

    public List<Person> getAllPeople(String userName) throws Exception{
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from person where descendant = ?";
                this.conn = db.getConn();
                stmt = conn.prepareStatement(sql);
                stmt.setString(1,userName);

                List<Person> people = new ArrayList<>();
                rs = stmt.executeQuery();
                while (rs.next()) {
                    Person tempPerson = new Person();
                    tempPerson.setPersonID(rs.getString(1));
                    tempPerson.setDescendant(rs.getString(2));
                    tempPerson.setFirstName(rs.getString(3));
                    tempPerson.setLastName(rs.getString(4));
                    tempPerson.setGender(rs.getString(5));
                    tempPerson.setFather(rs.getString(6));
                    tempPerson.setMother(rs.getString(7));
                    tempPerson.setSpouse(rs.getString(8));
                    people.add(tempPerson);
                }

                return people;
            }
            finally {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (Exception e) {
            throw new Exception("getAllPeople failed", e);
        }
    }
}
