class ServiceBeginEvent extends ShopEvent {
  private Counter counter;

  public ServiceBeginEvent(double time, Customer customer, Shop shop, Counter counter) {
    super(time, customer, shop);
    this.counter = counter;
  }
    
  @Override
  public String toString() {
    return super.toString() + String.format("service begin (by " + this.counter + ")");
  }

  @Override
  public Event[] simulate() {
    this.counter.setAvailable(false);
    return new Event[]{
      new ServiceEndEvent(
        this.getTime() + this.getCustomer().getServiceTime(),
        this.getCustomer(),
        this.getShop(),
        this.counter
      )
    };
  }
}
