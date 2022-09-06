class ArrivalEvent
extends ShopEvent {
    public ArrivalEvent(double time, Customer customer) {
        super(time, customer);
    }

    public String toString() {
        return super.toString() + "arrives";
    }

    public Event[] simulate() {
        Counter counter = Counter.nextAvailCounter();
        if (counter == null) {
            return new Event[]{new DepartureEvent(this.getTime(), this.getCustomer())};
        }
        return new Event[]{new SBEvent(this.getTime(), this.getCustomer(), counter)};
    }
}