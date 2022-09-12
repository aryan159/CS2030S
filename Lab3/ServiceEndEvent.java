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
    Customer nextCustomer = this.counter.deq();
    Customer nextCustomerFromShopQ = this.getShop().dequeue();
    if (nextCustomer != null) {
      Event serviceBeginEvent = new ServiceBeginEvent(this.getTime(), nextCustomer, this.getShop(), this.counter);
      if (nextCustomerFromShopQ != null) {
        return new Event[] {
          departureEvent,
          new CustomerCounterQueuedEvent(this.getTime(), nextCustomerFromShopQ, this.getShop(), this.counter),
          serviceBeginEvent
        };
      } else {
        return new Event[] {
          departureEvent,
          serviceBeginEvent
        };
      }
    }
    if (nextCustomerFromShopQ != null) {
      return new Event[]{
        departureEvent,
        new ServiceBeginEvent(this.getTime(), nextCustomerFromShopQ, this.getShop(), this.counter)
      };
    }
    return new Event[]{
      departureEvent
    };
  }
}
