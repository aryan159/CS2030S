class Customer {
  private double serviceTime;
  private int id;
  private static int count = 0;

  public Customer(double serviceTime) {
    this.serviceTime = serviceTime;
    this.id = count++;
  }

  public double getServiceTime() {
    return this.serviceTime;
  }

  public String toString() {
    return "C" + this.id;
  }
}
