class Shop {
  private Array<Counter> counters;
  private Queue<Customer> queue;

  public Shop(int numCounters, int lenCounterQueue, int lenEntranceQueue) {
    counters = new Array<Counter>(numCounters);
    for (int n = 0; n < numCounters; n++) {
      counters.set(n, new Counter(lenCounterQueue));
    }
    queue = new Queue<Customer>(lenEntranceQueue);
  }

  public Queue<Customer> getQueue() {
    return this.queue;
  }

  public Counter nextAvailCounter() {
    for (int i = 0; i < this.counters.length(); i++) {
      if (counters.get(i).isAvailable()) {
        return counters.get(i);
      }
    }
    return null;
  }

  public Counter nextAvailCounterQueue() {
    Counter counter = this.counters.min();
    return counter.isQueueFull() ? null : counter;
  }

  public boolean enqueue(Customer customer) {
    return queue.enq(customer);
  }

  public Customer dequeue() {
    return queue.deq();
  }

  public boolean isQueueFull() {
    return queue.isFull();
  }

  @Override
  public String toString() {
    return "shop queue " + this.queue.toString();
  }
}
