class ShopEvent
extends Event {
    private Customer customer;

    public ShopEvent(double time, Customer customer) {
        super(time);
        this.customer = customer;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    @Override
    public String toString() {
        String string = String.format(": Customer %d ", this.customer.getId());
        return super.toString() + string;
    }

    @Override
    public Event[] simulate() {
        return new Event[0];
    }
}