class ShopEvent extends Event {
  private Customer customer;
  private Shop shop;

  public ShopEvent(double time, Customer customer, Shop shop) {
    super(time);
    this.customer = customer;
    this.shop = shop;
  }

  public Customer getCustomer() {
    return this.customer;
  }

  public Shop getShop() {
    return this.shop;
  }

  @Override
  public String toString() {
    return super.toString() + ": " + this.getCustomer() + " ";
  }

  @Override
  public Event[] simulate() {
    return new Event[0];
  }
}
