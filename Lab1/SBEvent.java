class SBEvent
extends ShopEvent {
    private Counter counter;

    public SBEvent(double time, Customer customer, Counter counter) {
        super(time, customer);
        this.counter = counter;
    }

    public String toString() {
        return super.toString() + String.format("service begin (by Counter %d)", this.counter.getId());
    }

    public Event[] simulate() {
        this.counter.setAvailable(false);
        return new Event[]{new SEEvent(this.getTime() + this.getCustomer().getServiceTime(), this.getCustomer(), this.counter)};
    }
}