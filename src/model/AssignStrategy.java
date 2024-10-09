package model;

public interface AssignStrategy {

    public Server getNextServer ();

    public void addRequest(Server server);

}
