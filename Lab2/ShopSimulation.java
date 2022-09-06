import java.util.Scanner;

class ShopSimulation extends Simulation {
  public Event[] initEvents;
  private Shop shop;

  public ShopSimulation(Scanner scanner) {
    this.initEvents = new Event[scanner.nextInt()];
    int numCounters  = scanner.nextInt();
    int lenQueue = scanner.nextInt();
    shop = new Shop(numCounters, lenQueue);

    int n = 0;
    while (scanner.hasNextDouble()) {
      double arrivalTime = scanner.nextDouble();
      double serviceTime = scanner.nextDouble();
      this.initEvents[n] = new ArrivalEvent(arrivalTime, new Customer(serviceTime), shop);
      n++;
    }
  }

  public Event[] getInitialEvents() {
    return this.initEvents;
  }
}
