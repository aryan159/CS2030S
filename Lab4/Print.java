/**
 * A non-generic Action to print the String
 * representation of the object.
 *
 * CS2030S Lab 4
 * AY22/23 Semester 1
 *
 * @author Aryan Jain */

public class Print implements Action<Object> {

  @Override
  public void call(Object obj) {
    System.out.println(obj.toString());
  }
}
