class ArrivalEvent extends ShopEvent {
  public ArrivalEvent(double time, Customer customer, Shop shop) {
    super(time, customer, shop);
  }
    
  @Override
  public String toString() {
    return super.toString() + "arrived " + this.getShop().getQueue();
  }

  @Override
  public Event[] simulate() {
    Counter counter = this.getShop().nextAvailCounter();
    if (counter != null) {
      return new Event[]{
        new ServiceBeginEvent(this.getTime(), this.getCustomer(), this.getShop(), counter)
      };
    } else if (!this.getShop().isQueueFull()) {
      return new Event[]{
        new CustomerQueuedEvent(this.getTime(), this.getCustomer(), this.getShop())
      };
    } else {
      return new Event[]{
        new DepartureEvent(this.getTime(), this.getCustomer(), this.getShop())
      };
    }
  }
}
