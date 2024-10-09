package model;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class RoundRobin implements AssignStrategy {

    private BlockingDeque<Server> servers;

    public RoundRobin() {
        this.servers = new LinkedBlockingDeque<>();
    }

    @Override
    public Server getNextServer() {
        Server server = servers.pollFirst();
        servers.addLast(server);
        return server;
    }

    @Override
    public void addRequest(Server server) {
        this.servers.addLast(server);
    }
}
