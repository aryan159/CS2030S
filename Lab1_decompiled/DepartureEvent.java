class DepartureEvent
extends ShopEvent {
    public DepartureEvent(double time, Customer customer) {
        super(time, customer);
    }

    public String toString() {
        return super.toString() + "departed";
    }

    public Event[] simulate() {
        return new Event[0];
    }
}