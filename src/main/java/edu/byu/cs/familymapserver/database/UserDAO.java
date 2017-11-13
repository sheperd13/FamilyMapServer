package edu.byu.cs.familymapserver.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.familymapserver.model.User;

/**
 * accesses the user table in the database
 */
public class UserDAO {
    private Database db;
    private Connection conn;

    /**
     * constructor. requires database
     * @param db
     */
    public UserDAO(Database db){
        this.db = db;
        this.conn = null;
    }

    /**
     * adds a given user to the database
     * @param user
     * @throws Exception
     */
    public void addUser(User user) throws Exception{
        try{
            PreparedStatement stmt = null;
            try{
                this.conn = db.getConn();
                String sql = "insert into users (userName, password, email, firstName, lastName," +
                        "gender, personID) VALUES (?,?,?,?,?,?,?)";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1,user.getUsername());
                stmt.setString(2,user.getPassword());
                stmt.setString(3,user.getEmail());
                stmt.setString(4,user.getFirstName());
                stmt.setString(5,user.getLastName());
                stmt.setString(6,user.getGender());
                stmt.setString(7,user.getPersonID());
                if (stmt.executeUpdate() != 1) {
                    throw new Exception("addUser failed: Could not add user");
                }
            }finally{
                stmt.close();
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void addUsers (List<User> users) throws Exception{
        try {
            for (User u : users) {
                addUser(u);
            }
        }catch(Exception e){
            throw new Exception("addUsers failed: Could not add users");
        }
    }

    /**
     * returns the user with the given personID
     * @param personID
     * @return
     * @throws SQLException
     */
    public User getUserByID(String personID) throws SQLException{
        User retUser = new User();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                this.conn = db.getConn();
                String sql = "select * from users where personID = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1,personID);
                rs = stmt.executeQuery();
                retUser.setUsername(rs.getString("userName"));
                retUser.setPassword(rs.getString("password"));
                retUser.setEmail(rs.getString("email"));
                retUser.setFirstName(rs.getString("firstName"));
                retUser.setLastName(rs.getString("lastName"));
                retUser.setGender(rs.getString("gender"));
                retUser.setPersonID(rs.getString("personID"));
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
        return retUser;
    }

    /**
     * returns the user with the given username
     * @param userName
     * @return
     * @throws SQLException
     */
    public User getUserByUsername(String userName) throws SQLException{
        User retUser = new User();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                this.conn = db.getConn();
                String sql = "select * from users where userName = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1,userName);
                rs = stmt.executeQuery();
                retUser.setUsername(rs.getString("userName"));
                retUser.setPassword(rs.getString("password"));
                retUser.setEmail(rs.getString("email"));
                retUser.setFirstName(rs.getString("firstName"));
                retUser.setLastName(rs.getString("lastName"));
                retUser.setGender(rs.getString("gender"));
                retUser.setPersonID(rs.getString("personID"));
            }finally{
                if(rs != null){
                    rs.close();
                }
                if(stmt != null) {
                    stmt.close();
                }
            }
        }catch (Exception e){

        }
        return retUser;
    }

    /**
     * returns all users
     * @return
     * @throws Exception
     */
    public List<User> getAllUsers() throws Exception {
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from users";
                this.conn = db.getConn();
                stmt = conn.prepareStatement(sql);

                List<User> users = new ArrayList<>();
                rs = stmt.executeQuery();
                while (rs.next()) {
                    User tempUser = new User();
                    tempUser.setUsername(rs.getString(1));
                    tempUser.setPassword(rs.getString(2));
                    tempUser.setEmail(rs.getString(3));
                    tempUser.setFirstName(rs.getString(4));
                    tempUser.setLastName(rs.getString(5));
                    tempUser.setGender(rs.getString(6));
                    tempUser.setPersonID(rs.getString(7));
                    users.add(tempUser);
                }

                return users;
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
            throw new Exception("getAllUsers failed", e);
        }
    }
}
