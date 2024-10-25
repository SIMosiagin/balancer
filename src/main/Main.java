package main;

import com.sun.net.httpserver.HttpServer;
import main.controller.Initializer;
import main.controller.Balancer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {
    public static void main(String[] args) throws IOException {

        Balancer balancer = new Balancer(new Initializer().init());

        // Create an HttpServer instance on port 8080
        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);

        // Set up the context for the root path "/"
        server.createContext("/ddos", balancer);

        // Start the server
        server.setExecutor(Executors.newFixedThreadPool(balancer.getThreadsAmount())); // creates a default executor
        server.start();

        System.out.println("Server started on port 8081");
    }
}