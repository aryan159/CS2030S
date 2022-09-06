import java.util.Scanner;

class ShopSimulation
extends Simulation {
    public Event[] initEvents;

    public ShopSimulation(Scanner scanner) {
        int n;
        this.initEvents = new Event[scanner.nextInt()];
        int n2 = scanner.nextInt();
        for (n = 0; n < n2; ++n) {
            new Counter();
        }
        n = 0;
        while (scanner.hasNextDouble()) {
            double arrivalTime = scanner.nextDouble();
            double serviceTime = scanner.nextDouble();
            this.initEvents[n] = new ArrivalEvent(arrivalTime, new Customer(serviceTime));
            ++n;
        }
    }

    public Event[] getInitialEvents() {
        return this.initEvents;
    }
}