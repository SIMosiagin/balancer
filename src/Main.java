import controller.Balancer;
import model.RoundRobin;
import model.Server;

public class Main {
    public static void main(String[] args) {
        Balancer balancer = new Balancer<>(new RoundRobin());


        for(int i = 1; i < 7; i++) {
            balancer.registerServer(new Server("S" + i));
        }

        for(int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    balancer.routeTo();
                }

            });
            thread.start();
        }




    }
}