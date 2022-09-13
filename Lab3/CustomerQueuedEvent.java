class CustomerQueuedEvent extends ShopEvent {

  public CustomerQueuedEvent(double time, Customer customer, Shop shop) {
    super(time, customer, shop);
  }
    
  @Override
  public String toString() {
    return super.toString() + "joined " + this.getShop();
  }

  @Override
  public Event[] simulate() {
    this.getShop().enqueue(this.getCustomer());
    return new Event[]{};
  }
}
