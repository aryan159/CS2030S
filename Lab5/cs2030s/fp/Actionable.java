package cs2030s.fp;

/**
 * The Actionable interface that can
 * act when given an action.
 *
 * <p>
 * Contains a single abstract method act.
 * </p>
 *
 * <p>
 * CS2030S Lab 4
 * AY22/23 Semester 1
 * </p>
 *
 * @author  Aryan Jain (10B) */
public interface Actionable<T> {
  void act(Action<? super T> action);
}
