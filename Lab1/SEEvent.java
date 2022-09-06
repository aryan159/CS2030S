class SEEvent
extends ShopEvent {
    private Counter counter;

    public SEEvent(double time, Customer customer, Counter counter) {
        super(time, customer);
        this.counter = counter;
    }

    public String toString() {
        return super.toString() + String.format("service done (by Counter %d)", this.counter.getId());
    }

    public Event[] simulate() {
        this.counter.setAvailable(true);
        return new Event[]{new DepartureEvent(this.getTime(), this.getCustomer())};
    }
}