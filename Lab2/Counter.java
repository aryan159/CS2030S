import java.util.ArrayList;

class Counter {
  private boolean available = true;
  private static int count = 0;
  private int id = count++;
  private static ArrayList<Counter> listOfCounters = new ArrayList<Counter>();

  public Counter() {
    listOfCounters.add(this);
  }

  public boolean isAvailable() {
    return this.available;
  }

  public void setAvailable(boolean available) {
    this.available = available;
  }

  public static Counter nextAvailCounter() {
    for (int i = 0; i < listOfCounters.size(); ++i) {
      if (listOfCounters.get(i).isAvailable()) {
        return listOfCounters.get(i);
      }
    }
    return null;
  }

  public String toString() {
    return "S" + this.id;  
  }
}   
