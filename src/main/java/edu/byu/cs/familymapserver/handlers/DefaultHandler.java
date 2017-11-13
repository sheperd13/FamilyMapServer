package edu.byu.cs.familymapserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by christian on 11/1/17.
 * The DefaultHandler is the HTTP handler that processes
 * incoming HTTP request with '/' and serves up the homepage
 */
public class DefaultHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String filePathStr = "/home/christian/Downloads/familymapserver/web/";
        Path filePath;
        boolean success = false;

        try {
            if (exchange.getRequestMethod().equals("GET")) {
                String urlPath = exchange.getRequestURI().getPath();
                //if css file is found in URL
                if (urlPath.contains("main.css")) {
                    filePathStr += "css/main.css";
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    filePath = FileSystems.getDefault().getPath(filePathStr);
                    Files.copy(filePath, exchange.getResponseBody());

                    exchange.getResponseBody().close();
                    success = true;
                }
                //if favicon is found in URL path
                else if (urlPath.contains("favicon")) {
                    filePathStr += "favicon.ico";
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    filePath = FileSystems.getDefault().getPath(filePathStr);
                    Files.copy(filePath, exchange.getResponseBody());

                    exchange.getResponseBody().close();
                    success = true;
                }
                else {
                    // else if html is found in URL path
                    filePathStr += "index.html";
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    filePath = FileSystems.getDefault().getPath(filePathStr);
                    Files.copy(filePath, exchange.getResponseBody());

                    exchange.getResponseBody().close();
                    success = true;
                }
            }

            //if a bad request was sent
            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (Exception e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();

            e.printStackTrace();
        }
    }
}