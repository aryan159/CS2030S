package cs2030s.fp;

/**
 * The Action interface that can be called
 * on an object of type T to act.
 *
 * <p> 
 * Contains a single abstract method call. 
 * </p>
 *
 * <p>
 * CS2030S Lab 4
 * AY22/23 Semester 1
 * </p>
 *
 * @author Aryan Jain */
public interface Action<T> {
  void call(T t);

}
