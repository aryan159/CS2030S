class Shop {
  private Counter[] counters;
  private Queue queue;

  public Shop(int numCounters, int lenQueue) {
    counters = new Counter[numCounters];
    for (int n = 0; n < numCounters; n++) {
      counters[n] = new Counter();
    }
    queue = new Queue(lenQueue);
  }

  public Queue getQueue() {
    return this.queue;
  }

  public Counter nextAvailCounter() {
    for (int i = 0; i < counters.length; i++) {
      if (counters[i].isAvailable()) {
        return counters[i];
      }
    }
    return null;
  }

  public boolean enqueue(Customer customer) {
    return queue.enq(customer);
  }

  public Customer dequeue() {
    return (Customer) queue.deq();
  }

  public boolean isQueueFull() {
    return queue.isFull();
  }
}
