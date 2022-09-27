/**
 * The Applicable interface that can probably
 * transform if given something that is
 * probably an Immutator.
 *
 * <p>
 * Contains a single abstract method apply.
 * </p>
 *
 * <p>
 * CS2030S Lab 4
 * AY22/23 Semester 1
 * </p>
 *
 * @author Aryan Jain (10B)
 */

interface Applicable<T> {
  abstract <R> Probably<R> apply(Probably<? extends Immutator<R, T>> probably);
}
