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

import edu.byu.cs.familymapserver.facade.facade_main.Facade;
import edu.byu.cs.familymapserver.facade.facade_objects.FillResult;

/**
 * Created by christian on 11/5/17.
 * Handles the fill function
 */

public class FillHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (!exchange.getRequestMethod().equals("scooby doo")) {
                URI uri = exchange.getRequestURI();
                String url = uri.getPath();
                String userName = getUserName(url);
                int generations = getGenerations(url, userName);
                Facade fillService = new Facade();
                FillResult result = fillService.fill(userName, generations);

                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                Writer writer = new OutputStreamWriter(exchange.getResponseBody());
                Gson gson = new Gson();
                gson.toJson(result, writer);

                writer.close();
            } else {
                String errorMessage = "{ \"message\":\"Bad Request\"}";
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                setResponseBody(exchange, errorMessage);
            }
        }catch(IOException e){
            String errorMessage = "{ \"message\":\"IOException Caught\"}";
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            setResponseBody(exchange, errorMessage);
        }catch (Exception e) {
            String errorMessage = "{ \"message\":\"Other Exception Caught\"}";
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            setResponseBody(exchange, errorMessage);
        }
    }

    private void setResponseBody(HttpExchange exchange, String body) throws IOException{
        OutputStream os = exchange.getResponseBody();
        os.write(body.getBytes());
        os.close();
    }

    private String getUserName(String url){
        StringBuilder sb = new StringBuilder();
        for(int i = 6; i < url.length(); i++){
            if(url.charAt(i) == '/'){
                break;
            }
            sb.append(url.charAt(i));
        }
        return sb.toString();
    }

    private int getGenerations(String url, String userName){
        StringBuilder sb = new StringBuilder();
        for(int i = 7 + userName.length(); i < url.length(); i++){
            sb.append(url.charAt(i));
        }
        if(sb.toString().equals("")){
            return -1;
        }
        return Integer.parseInt(sb.toString());
    }
}
