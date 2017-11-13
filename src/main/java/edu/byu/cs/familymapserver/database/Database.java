package edu.byu.cs.familymapserver.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *This class will implement methods to interact with the database
 * utilizing the DAO's and their methods.
 */

public class Database {
    private static boolean exists = true;
    private static File dbPath = new File("/home/christian/Desktop/AndroidStudioProjects/FamilyMap/database.sqlite");
    public static UserDAO userDAO;
    public static EventDAO eventDAO;
    public static PersonDAO personDAO;
    public static AuthTokenDAO authTokenDAO;

    private static Connection conn;

    /**
     * default constructor
     */
    public Database() {
        userDAO = new UserDAO(this);
        eventDAO = new EventDAO(this);
        personDAO = new PersonDAO(this);
        authTokenDAO = new AuthTokenDAO(this);
        if(!dbPath.exists()){
            try{
                openConnection();
                createTables();
                closeConnection(true);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    static {
        try {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        }
        catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * opens connection to the database
     * @throws Exception
     */
    public void openConnection() throws Exception {
        try {
            final String CONNECTION_URL = "jdbc:sqlite:" + "database.sqlite";

            // Open a database connection
            conn = DriverManager.getConnection(CONNECTION_URL);

            // Start a transaction
            conn.setAutoCommit(false);
        }
        catch (SQLException e) {
            throw new Exception("openConnection failed", e);
        }
    }

    /**
     * opens a connection with a specified database
     * @param dbName name of database to connect to
     * @throws Exception
     */
    public void openConnection(String dbName) throws Exception {
        try {
            final String CONNECTION_URL = "jdbc:sqlite:" + dbName;

            // Open a database connection
            conn = DriverManager.getConnection(CONNECTION_URL);

            // Start a transaction
            conn.setAutoCommit(false);
        }
        catch (SQLException e) {
            throw new Exception("openConnection failed", e);
        }
    }

    /**
     * closes connection to database
     * @param commit if true then it will save changes to database else discard them
     * @throws Exception
     */
    public void closeConnection(boolean commit) throws Exception {
        try {
            if (commit) {
                conn.commit();
            }
            else {
                conn.rollback();
            }

            conn.close();
            conn = null;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("closeConnection failed", e);
        }
    }

    /**
     * creates all tables in the database
     * @throws Exception
     */
    public void createTables() throws Exception {
        try {
            Statement stmt = null;
            try {
                stmt = conn.createStatement();
                stmt.executeUpdate("drop table if exists users");
                stmt.executeUpdate("drop table if exists person");
                stmt.executeUpdate("drop table if exists events");
                stmt.executeUpdate("drop table if exists authToken");

                stmt.executeUpdate("create table users(" +
                        "userName varchar MAX NOT NULL UNIQUE PRIMARY KEY," +
                        "password varchar MAX NOT NULL," +
                        "email varchar MAX NOT NULL," +
                        "firstName varchar MAX NOT NULL," +
                        "lastName varchar MAX NOT NULL," +
                        "gender varchar MAX NOT NULL," +
                        "personID varchar MAX NOT NULL UNIQUE" +
                        ")");

                stmt.executeUpdate("create table person(" +
                        "personID varchar MAX NOT NULL UNIQUE PRIMARY KEY," +
                        "descendant varchar MAX NOT NULL," +
                        "firstName varchar MAX NOT NULL," +
                        "lastName varchar MAX NOT NULL," +
                        "gender varchar MAX NOT NULL," +
                        "father varchar MAX," +
                        "mother varchar MAX," +
                        "spouse varchar MAX" +
                        ")");

                stmt.executeUpdate("create table events(" +
                        "eventID varchar MAX NOT NULL UNIQUE PRIMARY KEY," +
                        "descendant varchar MAX NOT NULL," +
                        "personID varchar MAX NOT NULL," +
                        "latitude real NOT NULL," +
                        "longitude real NOT NULL," +
                        "country varchar MAX NOT NULL," +
                        "city varchar MAX NOT NULL," +
                        "eventType varchar MAX NOT NULL," +
                        "year varchar MAX NOT NULL" +
                        ")");

                stmt.executeUpdate("create table authToken(" +
                        "userName varchar MAX NOT NULL," +
                        "token varchar MAX NOT NULL UNIQUE PRIMARY KEY," +
                        "lastUsed varchar MAX NOT NULL" +
                        ")");
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            }
        }
        catch (SQLException e) {
            throw new Exception("createTables failed", e);
        }
    }

    /**
     * drops and recreates all of the tables
     * @throws Exception
     */
    public void clear() throws Exception{
        createTables();
    }

    /**
     * clears the data for an existing userName
     * @param userName username to be cleared
     */
    public void clear(String userName){
        try {
            PreparedStatement stmt = null;
            try {
                String sql = "delete from person where descendant = ?";
                this.conn = getConn();
                stmt = conn.prepareStatement(sql);
                stmt.setString(1,userName);
                stmt.executeUpdate();

                sql = "delete from events where descendant = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1,userName);
                stmt.executeUpdate();
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * singleton model of database
     * @return
     */
    public Connection getConn(){
        return conn;
    }
}
