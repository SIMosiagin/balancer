package main.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.model.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.*;

import static main.constants.Constants.OK;

public class Balancer implements HttpHandler {
    private Config config;
    private ExecutorService executor;

    public Balancer(Config config) {
        this.config = config;
        this.executor = Executors.newFixedThreadPool(config.getWorkerProcesses());
    }

    public int getThreadsAmount() {
        return config.getWorkerProcesses();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = sendRequest(httpExchange);
        if (response == null) {
            throw new RuntimeException("response is null");
        }

        httpExchange.sendResponseHeaders(200, response.getBytes().length);

        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

        httpExchange.sendResponseHeaders(200, response.length());
    }

    private String sendRequest(HttpExchange httpExchange) {
        executor.submit(() -> {
            try {

                URL url = new URL(config.getStrategy().routeRequest(config.getUpstreams()).getForwardTo());
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestMethod(httpExchange.getRequestMethod());
                httpURLConnection.getOutputStream().write(httpExchange.getRequestBody().read());
                httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");

                int responseCode = httpURLConnection.getResponseCode();
                if (!OK.equals(responseCode)) {
                    throw new RuntimeException("route failed");
                }


                BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString();

            } catch (IOException exception) {
                throw new RuntimeException("something went wrong");
            }

        });
        return null;
    }
}
