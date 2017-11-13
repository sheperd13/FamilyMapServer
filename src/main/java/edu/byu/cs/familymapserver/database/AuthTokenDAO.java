package edu.byu.cs.familymapserver.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.familymapserver.model.AuthToken;

/**
 * accesses the authToken table in the database
 */
public class AuthTokenDAO {
    private Database db;
    private Connection conn;

    /**
     * AuthTokenDAO constructor
     * @param db database to be accessed
     */
    public AuthTokenDAO(Database db){
        this.db = db;
        conn = null;
    }

    /**
     * adds an authtoken to the database
     * @param token
     */
    public void addToken(AuthToken token){
        try{
            PreparedStatement stmt = null;
            try{
                this.conn = db.getConn();
                String sql = "insert into authToken (userName, token, lastUsed) VALUES (?,?,?)";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1,token.getUsername());
                stmt.setString(2,token.getToken());
                stmt.setString(3,token.getLastUsed());
                if (stmt.executeUpdate() != 1) {
                    throw new Exception("addToken failed: Could not add token");
                }
            }finally{
                stmt.close();
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }

    /**
     * returns the token linked to the given username
     * @param userName
     * @return
     */
    public AuthToken getToken(String userName){
        AuthToken retToken = new AuthToken();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                this.conn = db.getConn();
                String sql = "select * from authToken where userName = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1,userName);
                rs = stmt.executeQuery();
                retToken.setToken(rs.getString("token"));
                retToken.setUsername(rs.getString("userName"));
                retToken.setLastUsed(rs.getString("lastUsed"));
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
        return retToken;
    }

    public String hasToken(String authToken, boolean testing){
        String userName = "";
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            boolean successful = false;
            try {
                if(testing) {
                    db.openConnection("facadeTest.sqlite");
                    this.conn = db.getConn();
                }else {
                    db.openConnection();
                    this.conn = db.getConn();
                }
                String sql = "select userName from authToken where token = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1,authToken);
                rs = stmt.executeQuery();
                userName = rs.getString("userName");
                successful = true;
            }finally{
                db.closeConnection(successful);
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
        return userName;
    }

    /**
     * returns all current authtokens
     * @return
     * @throws Exception
     */
    public List<AuthToken> getAllTokens() throws Exception{
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from authToken";
                this.conn = db.getConn();
                stmt = conn.prepareStatement(sql);

                List<AuthToken> events = new ArrayList<>();
                rs = stmt.executeQuery();
                while (rs.next()) {
                    AuthToken tempEvent = new AuthToken();
                    tempEvent.setToken(rs.getString(1));
                    tempEvent.setUsername(rs.getString(2));
                    tempEvent.setLastUsed(rs.getString(3));
                    events.add(tempEvent);
                }

                return events;
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
            throw new Exception("getAllTokens failed", e);
        }
    }
}
