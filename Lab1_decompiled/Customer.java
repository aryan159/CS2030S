class Customer {
    private double serviceTime;
    private int id;
    private static int count = 0;

    public Customer(double serviceTime) {
        this.serviceTime = serviceTime;
        this.id = count++;
    }

    public int getId() {
        return this.id;
    }

    public double getServiceTime() {
        return this.serviceTime;
    }
}