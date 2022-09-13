class Counter implements Comparable<Counter> {
  private boolean available = true;
  private static int count = 0;
  private int id = count++;
  private Queue<Customer> queue;

  public Counter(int queueLen) {
    this.queue = new Queue<Customer>(queueLen);
  }

  public boolean isAvailable() {
    return this.available;
  }

  public void setAvailable(boolean available) {
    this.available = available;
  }

  public Boolean enq(Customer customer) {
    return this.queue.enq(customer);  
  }

  public Customer deq() {
    return this.queue.deq();
  }

  public Boolean isQueueFull() {
    return this.queue.isFull();
  }
  
  @Override
  public int compareTo(Counter counter) {
    if (this.queue.length() > counter.queue.length()) {
      return 1;
    } else if (this.queue.length() == counter.queue.length()) {
      return 0;
    } else {
      return -1;
    }
  }

  public String toString() {
    return "S" + this.id + " " + this.queue.toString();  
  }
}   
