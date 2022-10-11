package cs2030s.fp;

/**
 * The Immutator interface that can transform 
 * to type T2, an object of type T1.
 *
 * <p>
 * Contains a single abstract method invoke.
 * </p>
 *
 * <p>
 * CS2030S Lab 4
 * AY22/23 Semester 1
 * </p>
 *
 * @author Aryan Jain */
public interface Immutator<R, P> {
  R invoke(P p);
}
