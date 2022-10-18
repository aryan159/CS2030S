package cs2030s.fp;

/**
 * Encapsulates an Actually that may or may not be initialised.
 * If not initialised, the value is initialised once and stored and
 * returned every other time get is called
 */
public class Memo<T> extends Lazy<T> {
  /**
   * the encapsulated value (either initialized or uninitialized)
   */
  private Actually<T> value;

  /**
   * Default Constructor
   *
   * @param c a constant if the value is not yet initialised
   * @param a an Actually that represents the state (and value)
   */
  private Memo(Constant<? extends T> c, Actually<T> a) {
    super(c);
    this.value = a;
  }

  /**
   * Create a new memo from an initialized value
   *
   * @param <T> generic type
   * @param t initialized value of type T to create Memo with
   * @return the new Memo
   */
  public static <T> Memo<T> from(T t) {
    return new Memo<T>(null, Actually.ok(t));
  }

  /**
   * Create a new memo from a constant
   *
   * @param <T> generic type
   * @param c uninitialized value of type T encapsulated in Constant
   * @return the new Memo
   */
  public static <T> Memo<T> from(Constant<? extends T> c) {
    return new Memo<T>(c, Actually.err(new Exception("err")));
  }

  /**
   * Returns the initialized value. If the value is not initialized,
   * initialize it and then return it.
   *
   * @return the initialized value
   */
  public T get() {
    T val = this.value.unless(super.get());
    this.value = Actually.ok(val);
    return val;
  }

  /**
   * Combine the two memos using the provided combiner
   *
   * @param <S> generic type
   * @param <R> generic type
   * @param that the other Memo
   * @param combiner the combiner
   * @return the combined Memo
   */
  public <S, R> Memo<R> combine(
      Memo<S> that,
      Combiner<? extends R, ? super T, ? super S> combiner) {
    return Memo.<R>from(() -> combiner.combine(this.get(), that.get()));
  }

  /**
   * Transform the value in memo using the provided immutator
   *
   * @param <R> generic type
   * @param immutator the provided immutator
   * @return the new transformed Memo
   */
  @Override
  public <R> Memo<R> transform(Immutator<? extends R, ? super T> immutator) {
    return Memo.<R>from(() -> immutator.invoke(this.get())); 
  }

  /**
   * Return the next Memo using the provided immutator
   *
   * @param <R> generic type
   * @param immutator the provided immutator
   * @return the new memo
   */
  @Override
  public <R> Memo<R> next(Immutator<? extends Lazy<? extends R>, ? super T> immutator) {
    return Memo.<R>from(() -> immutator.invoke(this.get()).get());
  }

  /**
   * @return string representation of this Memo
   */
  @Override
  public String toString() {
    Actually<String> returnVal = (this.value.<String>next(
          t -> Actually.ok(String.valueOf(t))));
    return returnVal.<String>unless("?");
  }
}





