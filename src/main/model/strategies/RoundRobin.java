package main.model.strategies;

import main.model.Server;

import java.util.List;
import java.util.Map;

public class RoundRobin implements AssignStrategy {
    private int currentIndex = 0;
    @Override
    public Server routeRequest(List<Server> upstreams) {
        Server server = upstreams.get(currentIndex);
        currentIndex = (currentIndex + 1) % upstreams.size();
        return server;
    }
}
