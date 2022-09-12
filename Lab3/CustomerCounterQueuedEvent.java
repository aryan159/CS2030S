class CustomerCounterQueuedEvent extends ShopEvent {
  private Counter counter;

  public CustomerCounterQueuedEvent(double time, Customer customer,
      Shop shop, Counter counter) {
    super(time, customer, shop);
    this.counter = counter;
  }

  @Override
  public String toString() {
    return super.toString() + "joined counter queue (at " + this.counter.toString() + ")";  
  }

  @Override
  public Event[] simulate() {
    this.counter.enq(this.getCustomer());
    return new Event[0];
  }
}
