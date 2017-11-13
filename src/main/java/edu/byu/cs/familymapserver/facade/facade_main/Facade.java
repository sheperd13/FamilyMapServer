package edu.byu.cs.familymapserver.facade.facade_main;

import java.util.List;

import edu.byu.cs.familymapserver.database.Database;
import edu.byu.cs.familymapserver.facade.facade_objects.ClearResult;
import edu.byu.cs.familymapserver.facade.facade_objects.EventIDResult;
import edu.byu.cs.familymapserver.facade.facade_objects.EventResult;
import edu.byu.cs.familymapserver.facade.facade_objects.FillResult;
import edu.byu.cs.familymapserver.facade.facade_objects.LoadRequest;
import edu.byu.cs.familymapserver.facade.facade_objects.LoadResult;
import edu.byu.cs.familymapserver.facade.facade_objects.LoginRequest;
import edu.byu.cs.familymapserver.facade.facade_objects.LoginResult;
import edu.byu.cs.familymapserver.facade.facade_objects.PersonIDResult;
import edu.byu.cs.familymapserver.facade.facade_objects.PersonResult;
import edu.byu.cs.familymapserver.facade.facade_objects.RegisterRequest;
import edu.byu.cs.familymapserver.facade.facade_objects.RegisterResult;
import edu.byu.cs.familymapserver.model.AuthToken;
import edu.byu.cs.familymapserver.model.Event;
import edu.byu.cs.familymapserver.model.Person;
import edu.byu.cs.familymapserver.model.User;
import edu.byu.cs.familymapserver.util.DataGenerator;

/**
 * this class interacts with the database class and implements the basic server methods
 */
public class Facade {
    public Database db;
    private static boolean test = false;

    /**
     * constructors
     */
    public Facade(){
        this.db = new Database();
    }

    public Facade(Database db){
        this.db = db;
    }

    /**
     * registers the user with the information provided via the RegisterRequest
     * @param request registerrequest
     * @return registerresult
     * @throws Exception errors and stuff
     */
    public RegisterResult registerUser(RegisterRequest request) throws Exception {
        RegisterResult result = new RegisterResult();
        Person userPerson = new Person(request);
        AuthToken token = new AuthToken(request);
        User user = new User(request);
        user.setPersonID(userPerson.getPersonID());

        try {
            if(test) {
                db.openConnection("facadeTest.sqlite");
            }else db.openConnection();

            //ERROR CHECKING/////////////////////////////////////
            if(request.hasNull()){
                result = new RegisterResult("Missing a parameter");
                throw new Exception("Missing a parameter");
            }else if(db.userDAO.getUserByUsername(request.getUsername()).getUsername() != null){
                result = new RegisterResult("UserName already taken");
                throw new Exception("UserName already taken");
            }else if(!request.validGender()){
                result = new RegisterResult("Gender must be m or f");
                throw new Exception("Gender must be m or f");
            }
            /////////////////////////////////////////////////////

            db.personDAO.addPerson(userPerson);
            db.userDAO.addUser(user);
            db.authTokenDAO.addToken(token);
            db.closeConnection(true);
            result.setAuthToken(token.getToken());
            result.setUsername(request.getUsername());
            result.setPersonID(userPerson.getPersonID());
            fill(request.getUsername(), 4);
        }catch(Exception e){
            db.closeConnection(false);
            //System.out.println(e);
            return result;
        }

        return result;
    }

    /**
     * returns a LoginResult object which will given information regarding whether or not the login
     * was successful.
     * @param request loginrequest
     * @return result
     * @throws Exception errors and stuff
     */
    public LoginResult login(LoginRequest request) throws Exception {
        LoginResult result = new LoginResult();
        try{
            if(test) {
                db.openConnection("facadeTest.sqlite");
            }else db.openConnection();
            User user = db.userDAO.getUserByUsername(request.getUsername());
            //error checking///////////////////////////////////////////////
            //if the requested user name or password are empty
            if(request.getUsername().equals("") || request.getPassword().equals("")){
                db.closeConnection(false);
                result = new LoginResult("Missing username or password");
                throw new Exception("Missing username or password");
            //if username or password are not in database
            }else if(user.getUsername() == null || user.getPassword() == null) {
                db.closeConnection(false);
                result = new LoginResult("Invalid username or password");
                throw new Exception("Invalid username or password");
            //if password is wrong
            } else if(!request.getPassword().equals(user.getPassword())) {
                db.closeConnection(false);
                result = new LoginResult("Incorrect password");
                throw new Exception("Incorrect password");
            }
            ///////////////////////////////////////////////////////////////

            AuthToken token = new AuthToken(user.getUsername());
            db.authTokenDAO.addToken(token);
            result.setAuthToken(token.getToken());
            result.setUsername(user.getUsername());
            result.setPersonID(user.getPersonID());
            db.closeConnection(true);
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Returns whether or not the clear was successful
     * @return clearresult
     */
    public ClearResult clear() throws Exception {
        ClearResult result = new ClearResult();
        try {
            if (test) {
                db.openConnection("facadeTest.sqlite");
            } else db.openConnection();

            db.clear();
            result.setMessage("Clear Succeeded.");
            db.closeConnection(true);
        }catch(Exception e){
            db.closeConnection(false);
            result.setMessage(e.toString());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * performs a fill and returns the result
     * @param userName username
     * @param generations number of generations to be generated
     * @return fillresult
     */
    public FillResult fill(String userName, int generations) {
        FillResult fillResult = new FillResult();
        try {
            db.openConnection();
            DataGenerator generator = new DataGenerator(db);
            Person curUser = generator.verifyUserName(userName); //get the user matching the name passed in

            //if the userName exists then proceed
            if (curUser != null) {
                db.clear(userName); //clear the existing events and people matching userName
                //if no generations were passed in then default to 4
                if (generations == -1) {
                    generations = 4;
                }

                //set the result message by running the algorithm in the DataGenerator class
                fillResult.setMessage(generator.generateData(generations, curUser));
            } else {  //if the username does not exist
                fillResult.setMessage("userName does not exist");
            }
            db.closeConnection(true);
        } catch (Exception e){
            e.printStackTrace();
        }
        return fillResult;
    }

    /**
     * performs a load and returns the result
     * @param loadRequest loadrequest
     * @return loadresult
     */
    public LoadResult load(LoadRequest loadRequest) throws Exception{
        LoadResult result = new LoadResult();
        List<User> users = loadRequest.getUsers();
        List<Person> persons = loadRequest.getPersons();
        List<Event> events = loadRequest.getEvents();
        String error;
        try {
            if(test) {
                db.openConnection("facadeTest.sqlite");
            }else db.openConnection();
            //clear database
            db.clear();

            //add users to database
            for (User temp : users) {
                //if no errors then add current user to database
                if((error = temp.hasError()) == null) {
                    db.userDAO.addUser(temp);
                }else{
                    result.setMessage(error);
                    throw new Exception(error);
                }
            }

            //add people to database
            for (Person temp : persons) {
                //if no errors then add current user to database
                if((error = temp.hasError()) == null) {
                    db.personDAO.addPerson(temp);
                }else{
                    result.setMessage(error);
                    throw new Exception(error);
                }
            }

            //add events to database
            for (Event temp : events) {
                //if no errors then add current user to database
                if((error = temp.hasError()) == null) {
                    db.eventDAO.addEvent(temp);
                }else{
                    result.setMessage(error);
                    throw new Exception(error);
                }
            }
            result.setMessage("Successfully added " + users.size() + " users, " +
                                persons.size() + " persons, and " +
                                events.size() + " events to the database.");
            db.closeConnection(true);
        }catch(Exception e){
            db.closeConnection(false);
            if(result.getMessage() == null){
                result.setMessage("duplicate ID");
            }
            System.out.println(e);
        }
        return result;
    }

    /**
     * returns a person with the given ID
     * authToken required
     * @param personID personID
     * @return personIDresult
     */
    public PersonIDResult personID(String personID) throws Exception {
        PersonIDResult result = new PersonIDResult();
        try {
            if (test) {
                db.openConnection("facadeTest.sqlite");
            } else db.openConnection();

            //perform personID routine
            result.setPerson(db.personDAO.getPerson(personID));
            db.closeConnection(true);
        }catch(Exception e){
            db.closeConnection(false);
            result.setMessage(e.toString());
            System.out.println(e);
        }
        return result;
    }

    /**
     * returns all of the people associated with current user in the database
     * authToken required
     * @return personresult
     */
    public PersonResult person(String userName) throws Exception {
        PersonResult result = new PersonResult();
        try {
            if (test) {
                db.openConnection("facadeTest.sqlite");
            } else db.openConnection();

            //perform person routine
            result.setData(db.personDAO.getAllPeople(userName));
            db.closeConnection(true);
        }catch(Exception e){
            db.closeConnection(false);
            result.setMessage(e.toString());
            System.out.println(e);
        }
        return result;
    }

    /**
     * returns and event with the given ID
     * authToken required
     * @param eventID eventID
     * @return eventIDresult
     */
    public EventIDResult eventID(String eventID) throws Exception{
        EventIDResult result = new EventIDResult();
        try {
            if (test) {
                db.openConnection("facadeTest.sqlite");
            } else db.openConnection();

            //perform personID routine
            result.setEvent(db.eventDAO.getEvent(eventID));
            db.closeConnection(true);
        }catch(Exception e){
            db.closeConnection(false);
            result.setMessage(e.toString());
            System.out.println(e);
        }
        return result;
    }

    /**
     * returns all events associated with current user
     * authToken required
     * @return eventresult
     */
    public EventResult event(String userName) throws Exception{
        EventResult result = new EventResult();
        boolean successful = false;
        try {
            if (test) {
                db.openConnection("facadeTest.sqlite");
            } else db.openConnection();

            //perform person routine
            result.setData(db.eventDAO.getAllEvents(userName));
            successful = true;
        }catch(Exception e){
            successful = false;
            result.setMessage(e.toString());
            e.printStackTrace();
        }finally{
            db.closeConnection(successful);
        }
        return result;
    }
}
