package controller;

import constants.Constants;
import model.AssignStrategy;
import model.Server;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Balancer <T extends AssignStrategy> {

    private AssignStrategy strategy;
    private Executor executor = Executors.newFixedThreadPool(Constants.THREAD_CAPACITY);

    public Balancer(T strategy) {
        this.strategy = strategy;
    }

    public void registerServer(Server server) {
        this.strategy.addRequest(server);
    }


    public void routeTo() {
        executor.execute(() -> {
            System.out.println("Route to =" + strategy.getNextServer().getAddress());
        });
    }

    public void addRequest(Server server) {
        strategy.addRequest(server);
    }

}
