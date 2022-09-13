/**
 * The Array&ltT&gt for CS2030S.
 *
 * @author Aryan Jain
 * @version CS2030S AY21/22 Semester 2
 */
class Array<T extends Comparable<T>> { 
  private T[] array;

  Array(int size) {
    // We can suppress "unchecked" and "rawtypes" since the only way to modify T[]
    // is through the set() method (which type checks for T) and the
    // only way to access it is through get() and min() which can only 
    // return objects of Type T. Thus, we can guarantee type safety.
    @SuppressWarnings({"rawtypes", "unchecked"})
    T[] temp = (T[]) new Comparable[size]; 
    array = temp;
  }

  public void set(int index, T item) {
    this.array[index] = item;
  }

  public T get(int index) {
    return this.array[index];
  }

  public int length() {
    return this.array.length;
  }

  public T min() {
    if (array.length == 0) {
      return null;
    }
    T currentMin = this.array[0];
    for (T t : this.array) {
      if (currentMin.compareTo(t) > 0) {
        currentMin = t;
      }
    }
    return currentMin;
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder("[ ");
    for (int i = 0; i < array.length; i++) {
      s.append(i + ":" + array[i]);
      if (i != array.length - 1) {
        s.append(", ");
      }
    }
    return s.append(" ]").toString();
  }
}
