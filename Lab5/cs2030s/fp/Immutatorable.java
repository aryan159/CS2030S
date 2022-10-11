package cs2030s.fp;

/**
 * The Immutatorable interface that can
 * transform when given something that is
 * Immutator.
 *
 * <p>
 * Contains a single abstract method transform.
 * </p>
 *
 * <p>
 * CS2030S Lab 4
 * AY22/23 Semester 1
 * </p>
 *
 * @author Aryan Jain (10B)
 */
public interface Immutatorable<T> {
  <R> Immutatorable<R> transform(Immutator<? extends R, ? super T> immutator);
}
