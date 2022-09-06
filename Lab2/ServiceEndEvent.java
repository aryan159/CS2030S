class ServiceEndEvent extends ShopEvent {
  private Counter counter;

  public ServiceEndEvent(double time, Customer customer, Shop shop, Counter counter) {
    super(time, customer, shop);
    this.counter = counter;
  }

  @Override
  public String toString() {
    return super.toString() + String.format("service done (by " + this.counter + ")");
  }

  @Override
  public Event[] simulate() {
    this.counter.setAvailable(true);
    Event departureEvent = new DepartureEvent(this.getTime(), this.getCustomer(), this.getShop());
    Customer nextCustomer = this.getShop().dequeue();
    if (nextCustomer != null) {
      return new Event[]{
        departureEvent,
        new ServiceBeginEvent(this.getTime(), nextCustomer, this.getShop(), this.counter)
      };
    }
    return new Event[]{
      departureEvent
    };
  }
}
