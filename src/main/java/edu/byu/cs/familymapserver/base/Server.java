package edu.byu.cs.familymapserver.base;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

import edu.byu.cs.familymapserver.handlers.ClearHandler;
import edu.byu.cs.familymapserver.handlers.DefaultHandler;
import edu.byu.cs.familymapserver.handlers.EventIDHandler;
import edu.byu.cs.familymapserver.handlers.FillHandler;
import edu.byu.cs.familymapserver.handlers.LoadHandler;
import edu.byu.cs.familymapserver.handlers.LoginHandler;
import edu.byu.cs.familymapserver.handlers.PersonIDHandler;
import edu.byu.cs.familymapserver.handlers.RegisterHandler;

/**
 * Main is in here.
 */
public class Server{
    private HttpServer server;

	//create the contexts based on the possible URLs
    private void createContexts(HttpServer server){
        server.createContext("/", new DefaultHandler());
        server.createContext("/user/register", new RegisterHandler());
        server.createContext("/user/login", new LoginHandler());
        server.createContext("/clear", new ClearHandler());
        server.createContext("/fill", new FillHandler());
        server.createContext("/load", new LoadHandler());
        server.createContext("/person", new PersonIDHandler());
        server.createContext("/event", new EventIDHandler());

        System.out.println("Created / default context");
        System.out.println("Created /user/register context");
        System.out.println("Created /user/login context");
        System.out.println("Created /clear context");
        System.out.println("Created /fill context");
        System.out.println("Created /load context");
        System.out.println("Created /person context");
        System.out.println("Created /event context");
    }

	//run server
    private void run(String port){
        System.out.println("Initializing HTTP Server");

        try{
            server = HttpServer.create(new InetSocketAddress(Integer.parseInt(port)), 1);
        }catch(IOException e){
            return;
        }

        server.setExecutor(null);

        createContexts(server);

        System.out.println("Starting Server");
        server.start();
        System.out.println("Waiting for request...");
    }

	//Main
    public static void main(String[] args){
        assert args.length == 1;
        String port = args[0];
        new Server().run(port);
    }
}
