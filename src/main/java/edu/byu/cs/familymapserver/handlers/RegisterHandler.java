package edu.byu.cs.familymapserver.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;

import edu.byu.cs.familymapserver.facade.facade_main.Facade;
import edu.byu.cs.familymapserver.facade.facade_objects.RegisterRequest;
import edu.byu.cs.familymapserver.facade.facade_objects.RegisterResult;

/**
 * Created by christian on 10/30/17.
 */

public class RegisterHandler implements HttpHandler {
    //TODO when fill is done, call it at the end of the register function or inside the register service
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().equals("POST")) {
                String requestBody = readString(exchange.getRequestBody()); //get the request body
                Gson gson = new Gson();

                RegisterRequest registerRequest = gson.fromJson(requestBody, RegisterRequest.class);
                Facade registerService = new Facade();
                RegisterResult result = registerService.registerUser(registerRequest);

                exchange.sendResponseHeaders(responseStatus(result.hasError()), 0);

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

    private String readString(InputStream is) throws IOException{
        StringBuilder sb = new StringBuilder();
        InputStreamReader fin = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = fin.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    private int responseStatus(boolean hasError){
        if(!hasError){
            return HttpURLConnection.HTTP_OK;
        }else{
            return HttpURLConnection.HTTP_BAD_REQUEST;
        }
    }
}
