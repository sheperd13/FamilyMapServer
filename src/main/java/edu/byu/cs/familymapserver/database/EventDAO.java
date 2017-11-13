package edu.byu.cs.familymapserver.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.familymapserver.model.Event;

/**
 * Accesses the event table in the database
 */
public class EventDAO {
    private Database db;
    private Connection conn;

    /**
     * constructor, requires database
     * @param db
     */
    public EventDAO(Database db){
        this.db = db;
        this.conn = null;
    }

    /**
     * adds a given event to the database
     * @param event
     */
    public void addEvent(Event event){
        try{
            PreparedStatement stmt = null;
            try{
                this.conn = db.getConn();
                String sql = "insert into events (eventID, descendant, personID, latitude, longitude," +
                        "country, city, eventType, year) VALUES (?,?,?,?,?,?,?,?,?)";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1,event.getEventID());
                stmt.setString(2,event.getDescendant());
                stmt.setString(3,event.getPerson());
                stmt.setFloat(4,event.getLatitude());
                stmt.setFloat(5,event.getLongitude());
                stmt.setString(6,event.getCountry());
                stmt.setString(7,event.getCity());
                stmt.setString(8,event.getEventType());
                stmt.setString(9,event.getYear());
                if (stmt.executeUpdate() != 1) {
                    throw new Exception("addEvent failed: Could not add event");
                }
            }finally{
                stmt.close();
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }

    /**
     * gets an event with given eventID
     * @param eventID
     * @return
     */
    public Event getEvent(String eventID){
        Event retEvent = new Event();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                this.conn = db.getConn();
                String sql = "select * from events where eventID = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1,eventID);
                rs = stmt.executeQuery();
                retEvent.setEventID(rs.getString("eventID"));
                retEvent.setDescendant(rs.getString("descendant"));
                retEvent.setPerson(rs.getString("personID"));
                retEvent.setLatitude(rs.getFloat("latitude"));
                retEvent.setLongitude(rs.getFloat("longitude"));
                retEvent.setCountry(rs.getString("country"));
                retEvent.setCity(rs.getString("city"));
                retEvent.setEventType(rs.getString("eventType"));
                retEvent.setYear(rs.getString("year"));
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
        return retEvent;
    }

    /**
     * returns all events in database
     * @return
     * @throws Exception
     */
    public List<Event> getAllEvents(String userName) throws Exception{
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from events where descendant = ?";
                this.conn = db.getConn();
                stmt = conn.prepareStatement(sql);
                stmt.setString(1,userName);

                List<Event> events = new ArrayList<>();
                rs = stmt.executeQuery();
                while (rs.next()) {
                    Event tempEvent = new Event();
                    tempEvent.setEventID(rs.getString(1));
                    tempEvent.setDescendant(rs.getString(2));
                    tempEvent.setPerson(rs.getString(3));
                    tempEvent.setLatitude(rs.getFloat(4));
                    tempEvent.setLongitude(rs.getFloat(5));
                    tempEvent.setCountry(rs.getString(6));
                    tempEvent.setCity(rs.getString(7));
                    tempEvent.setEventType(rs.getString(8));
                    tempEvent.setYear(rs.getString(9));
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
            e.printStackTrace();
            throw new Exception("getAllEvents failed", e);
        }
    }
}
