package edu.byu.cs.familymapserver.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import edu.byu.cs.familymapserver.database.Database;
import edu.byu.cs.familymapserver.model.Event;
import edu.byu.cs.familymapserver.model.Location;
import edu.byu.cs.familymapserver.model.Person;
import edu.byu.cs.familymapserver.model.User;

/**
 * Created by christian on 11/5/17.
 * This class will create a given number of generations of
 * data for a given userName. This class will call the DAO
 * functions to add everything to the database.
 *
 * This class will also clear any existing data for the given
 * userName before inserting new data.
 */

public class DataGenerator {
    private static final String path = "/home/christian/Downloads/familymapserver/json";
    private static int birthyear = 1992;
    private Database db;
    private List<Person> persons;
    private List<Event> events;
    private List<String> femaleNames;
    private List<String> maleNames;
    private List<String> lastNames;
    private List<Location> locations;

    public DataGenerator(Database db){
        this.db = db;
        persons = new ArrayList<>();
        events = new ArrayList<>();
        femaleNames = setFemaleNames();
        maleNames = setMaleNames();
        lastNames = setLastNames();
        locations = setLocations();
    }

    /**
     * The algorithm for generating data will go here
     */
    public String generateData(int generations, Person curUser){
        String message = null;
        try {
            //generates the persons to be added///////////////////////
            List<Person> parents = new ArrayList<>();
            List<Person> toBeAddedToParents = new ArrayList<>();
            List<Person> toBeRemoved = new ArrayList<>();
            parents.add(generateSelf(curUser));
            persons.addAll(parents);
            for (int i = 0; i < generations; i++) {
                for (Iterator<Person> iter = parents.listIterator(); iter.hasNext(); ) {
                    Person temp = iter.next();
                    toBeAddedToParents.add(generateFather(temp, i + 1));
                    toBeAddedToParents.add(generateMother(temp, i + 1));
                    toBeRemoved.add(temp);
                }
                parents.removeAll(toBeRemoved);
                parents.addAll(toBeAddedToParents);
                persons.addAll(toBeAddedToParents);
                toBeAddedToParents.clear();
                toBeRemoved.clear();
            }
            //////////////////////////////////////////////////////////

            //generates the events to be added////////////////////////
            generateEvents();
            //////////////////////////////////////////////////////////
            message = "Successfully added " + persons.size() + " persons and " + events.size() +
                    " events to the database.";
        }catch(Exception e){
            e.printStackTrace();
            message = e.toString();
        }
        return message;
    }

    private void generateEvents(){
        //birth, baptism, marriage, then death
        Random randomizer = new Random();
        int tempBirthyear = 1992;
        for(Person temp: persons){
            tempBirthyear = birthyear - ((temp.getGeneration()) * (20 + randomizer.nextInt(15)));
            generateEvent("birth", tempBirthyear, temp);
            generateEvent("baptism", tempBirthyear, temp);
            generateEvent("marriage", tempBirthyear, temp);
            generateEvent("death", tempBirthyear, temp);
        }
    }

    private void generateEvent(String eventType, int birthyear, Person person){
        Event event = new Event();
        Random randomizer = new Random();
        Location location = getRandomLocation();
        event.setDescendant(person.getDescendant());
        event.setPerson(person.getPersonID());
        event.setLatitude(location.getLatitude());
        event.setLongitude(location.getLongitude());
        event.setCountry(location.getCountry());
        event.setCity(location.getCity());
        event.setEventType(eventType);
        if(eventType.equals("birth")){
            event.setYear(Integer.toString(birthyear));
        }else if(eventType.equals("baptism")){
            event.setYear(Integer.toString(birthyear + 8 + randomizer.nextInt(15)));
        }else if(eventType.equals("marriage")){
            event.setYear(Integer.toString(birthyear + 20 + randomizer.nextInt(10)));
        }else if(eventType.equals("death")){
            event.setYear(Integer.toString(birthyear + 60 + randomizer.nextInt(30)));
        }else return;

        if(Integer.parseInt(event.getYear()) <= 2017) {
            db.eventDAO.addEvent(event);
            events.add(event);
        }
    }

    private Location getRandomLocation(){
        Random randomizer = new Random();
        Location random = locations.get(randomizer.nextInt(locations.size()));
        return random;
    }

    private Person generateSelf(Person curUser){
        Person self = new Person(curUser.getPersonID());
        self.setGeneration(0);
        self.setDescendant(curUser.getDescendant());
        self.setFirstName(curUser.getFirstName());
        self.setLastName(curUser.getLastName());
        self.setGender(curUser.getGender());

        //record this for generating the father's person
        self.setFatherFirstName(getRandomFirstName("m"));
        self.setFather(self.getFatherFirstName() + " " + curUser.getLastName());

        //record this stuff for generating the mother's person
        self.setMotherFirstName(getRandomFirstName("f"));
        self.setMotherLastName(getRandomSurname());
        self.setMother(self.getMotherFirstName() + " " + self.getMotherLastName());

        try{
            db.personDAO.addPerson(self);
        }catch(Exception e){
            e.printStackTrace();
        }
        return self;
    }

    private Person generateMother(Person person, int generation){
        Person mother = new Person();
        mother.setGeneration(generation);
        mother.setDescendant(person.getDescendant());
        mother.setFirstName(person.getMotherFirstName());
        mother.setLastName(person.getMotherLastName());
        mother.setGender("f");

        //record this for generating the father's person
        mother.setFatherFirstName(getRandomFirstName("m"));
        mother.setFather(mother.getFatherFirstName() + " " + mother.getLastName());

        //record this stuff for generating the mother's person
        mother.setMotherFirstName(getRandomFirstName("f"));
        mother.setMotherLastName(getRandomSurname());
        mother.setMother(mother.getMotherFirstName() + " " + mother.getMotherLastName());

        mother.setSpouse(person.getFatherFirstName() + " " + person.getLastName());

        try{
            db.personDAO.addPerson(mother);
        }catch(Exception e){
            e.printStackTrace();
        }
        return mother;
    }

    private Person generateFather(Person person, int generation){
        Person father = new Person();
        father.setGeneration(generation);
        father.setDescendant(person.getDescendant());
        father.setFirstName(person.getFatherFirstName());
        father.setLastName(person.getLastName());
        father.setGender("m");

        //record this for generating the father's person
        father.setFatherFirstName(getRandomFirstName("m"));
        father.setFather(father.getFatherFirstName() + " " + person.getLastName());

        //record this stuff for generating the mother's person
        father.setMotherFirstName(getRandomFirstName("f"));
        father.setMotherLastName(getRandomSurname());
        father.setMother(father.getMotherFirstName() + " " + father.getMotherLastName());

        father.setSpouse(person.getMotherFirstName() + " " + person.getMotherLastName());

        try{
            db.personDAO.addPerson(father);
        }catch(Exception e){
            e.printStackTrace();
        }
        return father;
    }

    private String getRandomFirstName(String gender){
        Random randomizer = new Random();
        String random = null;
        if(gender.equals("m") || gender.equals("M")){
            random = maleNames.get(randomizer.nextInt(maleNames.size()));
        }else{
            random = femaleNames.get(randomizer.nextInt(femaleNames.size()));
        }
        return random;
    }

    private String getRandomSurname(){
        Random randomizer = new Random();
        String random = lastNames.get(randomizer.nextInt(lastNames.size()));
        return random;
    }

    private List<String> setFemaleNames(){
        String filePath = path + "/fnames.json";
        JsonParser parser = new JsonParser();
        try {
            Object object = parser.parse(new FileReader(filePath));
            JsonObject jsonObject =(JsonObject) object;
            JsonElement names = jsonObject.get("data");
            Type listType = new TypeToken<List<String>>(){}.getType();
            List<String> femNames = new Gson().fromJson(names, listType);
            return femNames;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    private List<String> setMaleNames(){
        String filePath = path + "/mnames.json";
        JsonParser parser = new JsonParser();
        try {
            Object object = parser.parse(new FileReader(filePath));
            JsonObject jsonObject =(JsonObject) object;
            JsonElement names = jsonObject.get("data");
            Type listType = new TypeToken<List<String>>(){}.getType();
            List<String> manNames = new Gson().fromJson(names, listType);
            return manNames;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    private List<String> setLastNames(){
        String filePath = path + "/snames.json";
        JsonParser parser = new JsonParser();
        try {
            Object object = parser.parse(new FileReader(filePath));
            JsonObject jsonObject =(JsonObject) object;
            JsonElement names = jsonObject.get("data");
            Type listType = new TypeToken<List<String>>(){}.getType();
            List<String> lastNames = new Gson().fromJson(names, listType);
            return lastNames;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    private List<Location> setLocations(){
        String filePath = path + "/locations.json";
        JsonParser parser = new JsonParser();
        try {
            Object object = parser.parse(new FileReader(filePath));
            JsonObject jsonObject =(JsonObject) object;
            JsonElement names = jsonObject.get("data");
            Type listType = new TypeToken<List<Location>>(){}.getType();
            List<Location> locs = new Gson().fromJson(names, listType);
            return locs;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public Person verifyUserName(String userName){
        try {
            //db.openConnection();
            User curUser = db.userDAO.getUserByUsername(userName);
            if(curUser.getPersonID() != null){
                return db.personDAO.getPerson(curUser.getPersonID());
            }
            //db.closeConnection(false);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
