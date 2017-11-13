package edu.byu.cs.familymapserver.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;

import edu.byu.cs.familymapserver.facade.facade_main.Facade;
import edu.byu.cs.familymapserver.facade.facade_objects.ClearResult;

/**
 * Created by christian on 10/30/17.
 */

public class ClearHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod() != "") {
                Gson gson = new Gson();
                Facade clearService = new Facade();
                ClearResult result = clearService.clear();

                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                Writer writer = new OutputStreamWriter(exchange.getResponseBody());
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
}
