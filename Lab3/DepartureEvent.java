class DepartureEvent extends ShopEvent {
  public DepartureEvent(double time, Customer customer, Shop shop) {
    super(time, customer, shop);
  }

  @Override
  public String toString() {
    return super.toString() + "departed";
  }

  @Override
  public Event[] simulate() {
    return new Event[0];
  }
}
