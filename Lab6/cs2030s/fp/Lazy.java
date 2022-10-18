package cs2030s.fp;
/**
 * Encapsulats a Constant that is lazyly evaluated
 */
public class Lazy<T> implements Immutatorable<T> {
  /**
   * The encapsulated constant
   */
  private Constant<? extends T> init;

  /**
   * Default Constructor
   *
   * @param c the constant
   */
  protected Lazy(Constant<? extends T> c) {
    this.init = c;
  }

  /**
   * Creates a Lazy from a value
   *
   * @param <T> generic type
   * @param v the value
   * @return the generated lazy
   */
  public static <T> Lazy<T> from(T v) {
    return Lazy.from(() -> v);  
  }

  /**
   * Creates a Lazy from a constant
   *
   * @param <T> generic type
   * @param c the constant
   * @return the generated lazy
   */
  public static <T> Lazy<T> from(Constant<? extends T> c) {
    return new Lazy<T>(c);
  }

  /**
   * Returns the value
   *
   * @return the value
   */
  public T get() {
    return init.init();
  }

  /**
   * Transform the value in lazy using the provided immutator
   *
   * @param <R> generic type
   * @param immutator the provided immutator
   * @return the new transformed lazy
   */
  @Override
  public <R> Lazy<R> transform(Immutator<? extends R, ? super T> immutator) {
    return Lazy.<R>from(() -> immutator.invoke(this.get())); 
  }

  /**
   * Return the next lazy using the provided immutator
   *
   * @param <R> generic type
   * @param immutator the provided immutator
   * @return the new lazy
   */
  public <R> Lazy<R> next(Immutator<? extends Lazy<? extends R>, ? super T> immutator) {
    return Lazy.<R>from(() -> immutator.invoke(this.get()).get());
  }

  /**
   * @return string representation of this lazy
   */
  @Override
  public String toString() {
    return this.get().toString();
  }
}
