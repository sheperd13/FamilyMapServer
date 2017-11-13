package edu.byu.cs.familymapserver.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.byu.cs.familymapserver.facade.facade_main.Facade;
import edu.byu.cs.familymapserver.facade.facade_objects.PersonIDResult;
import edu.byu.cs.familymapserver.facade.facade_objects.PersonResult;
import edu.byu.cs.familymapserver.model.Person;

/**
 * Created by christian on 10/30/17.
 * Handles the person() and personID() services
 */

public class PersonIDHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Facade facade = new Facade();
        URI uri = exchange.getRequestURI();

        //check if a valid authToken exists
        String userName = validateAuthToken(exchange, facade);
        String personID = getPersonID(uri);

        //if token exists
        if(userName != null && !userName.equals("")) {
            try {
                if (exchange.getRequestMethod().equals("GET")) {
                    Gson gson = new Gson();
                    PersonIDResult resultID = new PersonIDResult();
                    PersonResult result = new PersonResult();

                    Facade personIDService = new Facade();
                    //if a personID is provided then do this else get all people
                    if(!personID.equals("")) {
                        resultID = personIDService.personID(personID);
                        //if result username doesn't match the auth token username
                        if(!resultID.getPerson().getDescendant().equals(userName)){
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                            setResponseBody(exchange, "authToken doesn't match user");
                            throw new Exception ("authToken doesn't match user");
                        }
                        if(resultID.getPerson().getPersonID() == null){
                            String errorMessage = "{ \"message\":\"Wrong PersonID\"}";
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                            setResponseBody(exchange, errorMessage);
                            throw new Exception("PersonIDException: wrong personID");
                        }
                    }else{
                        result = personIDService.person(userName);
                        Iterator iter = result.getData().iterator();
                        Person person = (Person)iter.next();
                        if(!userName.equals(person.getDescendant())){
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                            setResponseBody(exchange, "authToken doesn't match user");
                            throw new Exception ("authToken doesn't match user");
                        }
                    }

                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                    Writer writer = new OutputStreamWriter(exchange.getResponseBody());
                    if(!personID.equals("")) {
                        gson.toJson(resultID.getPerson(), writer);
                    }else{
                        gson.toJson(result, writer);
                    }

                    writer.close();
                } else {
                    String errorMessage = "{ \"message\":\"Bad Request\"}";
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    setResponseBody(exchange, errorMessage);
                }
            } catch (IOException e) {
                String errorMessage = "{ \"message\":\"IOException Caught\"}";
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                setResponseBody(exchange, errorMessage);
            } catch (Exception e) {
                String errorMessage = "{ \"message\":\"Other Exception Caught\"}";
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                setResponseBody(exchange, errorMessage);
            }
        }else{
            //no valid token found
            String errorMessage = "{ \"message\":\"Invalid authToken\"}";
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            setResponseBody(exchange, errorMessage);
        }
    }

    private String validateAuthToken(HttpExchange exchange, Facade facade){
        Map<String,List<String>> headers = new HashMap<>(exchange.getRequestHeaders());
        List<String> authorization = headers.get("Authorization");
        String authToken = authorization.get(0);
        return facade.db.authTokenDAO.hasToken(authToken,false);
    }

    private void setResponseBody(HttpExchange exchange, String body) throws IOException{
        OutputStream os = exchange.getResponseBody();
        os.write(body.getBytes());
        os.close();
    }

    private String getPersonID(URI uri){
        String path = uri.getPath();
        StringBuilder userID = new StringBuilder(path.length() - 8);
        for(int i = 8; i < path.length(); i++){
            userID.append(path.charAt(i));
        }
        return userID.toString();
    }
}
