class Counter {
  private boolean available = true;
  private static int count = 0;
  private int id = count++;

  public Counter() {
  }

  public boolean isAvailable() {
    return this.available;
  }

  public void setAvailable(boolean available) {
    this.available = available;
  }

  public String toString() {
    return "S" + this.id;  
  }
}   
