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
import java.util.ListIterator;
import java.util.Map;

import edu.byu.cs.familymapserver.facade.facade_main.Facade;
import edu.byu.cs.familymapserver.facade.facade_objects.EventIDResult;
import edu.byu.cs.familymapserver.facade.facade_objects.EventResult;
import edu.byu.cs.familymapserver.model.Event;

/**
 * Created by christian on 11/2/17.
 */

public class EventIDHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Facade facade = new Facade();
        URI uri = exchange.getRequestURI();

        //check if a valid authToken exists
        String userName = validateAuthToken(exchange, facade);
        String eventID = getEventID(uri);

        //if token exists
        if(userName != null && !userName.equals("")) {
            try {
                if (exchange.getRequestMethod().equals("GET")) {
                    Gson gson = new Gson();
                    EventIDResult resultID = new EventIDResult();
                    EventResult result = new EventResult();

                    Facade eventIDService = new Facade();
                    //if a personID is provided then do this else get all people
                    if(!eventID.equals("")) {
                        resultID = eventIDService.eventID(eventID);
                        //if result username doesn't match the auth token username
                        if(!resultID.getEvent().getDescendant().equals(userName)){
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                            setResponseBody(exchange, "authToken doesn't match user");
                            throw new Exception ("authToken doesn't match user");
                        }
                        if(resultID.getEvent().getEventID() == null){
                            String errorMessage = "{ \"message\":\"Wrong EventID\"}";
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                            setResponseBody(exchange, errorMessage);
                            throw new Exception("PersonIDException: wrong personID");
                        }
                    }else{
                        result = eventIDService.event(userName);
                        Iterator iter = result.getData().iterator();
                        Event event = (Event)iter.next();
                        if(!userName.equals(event.getDescendant())){
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                            setResponseBody(exchange, "authToken doesn't match user");
                            throw new Exception ("authToken doesn't match user");
                        }
                    }

                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                    Writer writer = new OutputStreamWriter(exchange.getResponseBody());
                    if(!eventID.equals("")) {
                        gson.toJson(resultID.getEvent(), writer);
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

    private String getEventID(URI uri){
        String path = uri.getPath();
        StringBuilder eventID = new StringBuilder(path.length() - 7);
        for(int i = 7; i < path.length(); i++){
            eventID.append(path.charAt(i));
        }
        return eventID.toString();
    }
}
