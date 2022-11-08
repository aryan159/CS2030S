package cs2030s.fp;

import java.util.ArrayList;
import java.util.List;

/**
 * A container class for a lazily-evaluated infinite list.
 *
 * @author Aryan Jain
 */
public class InfiniteList<T> {
  /**
   * The first element.
   */
  private Memo<Actually<T>> head;
  /**
   * The tail representing the rest of the InfiniteList.
   */
  private Memo<InfiniteList<T>> tail;
  /**
   * The cached instance of End.
   */
  private static final InfiniteList<Object> END = new End();

  /**
   * Private constructor.
   *
   * @param head the head
   * @param tail the tail representing the rest of the InfiniteList
   */
  private InfiniteList(Memo<Actually<T>> head, Memo<InfiniteList<T>> tail) {
    this.head = head;
    this.tail = tail;
  }

  /**
   * Create an InfiniteList using a producer.
   *
   * @param <T> generic type
   * @param prod the producer
   * @return the new InfiniteList
   */
  public static <T> InfiniteList<T> generate(Constant<? extends T> prod) {
    return new InfiniteList<T>(
        Memo.<Actually<T>>from(() -> Actually.ok(prod.init())),
        Memo.<InfiniteList<T>>from(() -> generate(prod))
        );
  }

  /**
   * Create an InfiniteList using a func and a seed of the form s, f(s), f(f(s)), etc.
   *
   * @param <T> generic type
   * @param seed the seed
   * @param func the function
   * @return the new InfiniteList
   */
  public static <T> InfiniteList<T> iterate(T seed, Immutator<? extends T, ? super T> func) {
    return new InfiniteList<T>(
        Memo.<Actually<T>>from(Actually.ok(seed)),
        Memo.<InfiniteList<T>>from(() -> iterate(func.invoke(seed), func))
        );
  }

  /**
   * Returns the next non-failure head.
   *
   * @return the value
   */
  public T head() {
    return this.head.get().except(() -> this.tail.get().head());
  }

  /**
   * Returns the tail corresponding to the next non-failure head.
   *
   * @return the tail (InfiniteList)
   */
  public InfiniteList<T> tail() {
    return this.head.get().transform(x -> this.tail.get()).except(() -> this.tail.get().tail());
  }

  /**
   * Returns a new InfiniteList with each element mapped using the provided Immutator.
   *
   * @param <R> generic return type
   * @param f the Immutator
   * @return the nre InfiniteList
   */
  public <R> InfiniteList<R> map(Immutator<? extends R, ? super T> f) {
    return new InfiniteList<R>(
        Memo.<Actually<R>>from(() -> Actually.ok(f.invoke(this.head()))),
        Memo.<InfiniteList<R>>from(() -> this.tail().map(f))
        );
  }

  /**
   * Returns a new InfiniteList which has been filteres using the given Immutator.
   *
   * @param pred the Immutator
   * @return the new InfiniteList
   */
  public InfiniteList<T> filter(Immutator<Boolean, ? super T> pred) {
    return new InfiniteList<T>(
        Memo.<Actually<T>>from(() -> this.head.get().check(pred)),
        Memo.<InfiniteList<T>>from(() -> this.tail.get().filter(pred))
        );
  }

  /**
   * Returns a new InfiniteList which has been limited in length.
   *
   * @param n the size of the returned InfiniteList
   * @return the new (In)finiteList
   */
  public InfiniteList<T> limit(long n) {
    if (n <= 0) {
      return InfiniteList.<T>end();
    }
    return new InfiniteList<T>(
        this.head,
        Memo.<InfiniteList<T>>from(() ->
            this.head.get().transform(x -> this.tail.get().limit(n - 1))
                .except(() -> this.tail.get().limit(n)))
        );
  }
  
  /**
   * Returns a new InfiniteList that stops at the first instance of the pred failing.
   *
   * @param pred The predicate
   * @return the new InfiniteList
   */
  public InfiniteList<T> takeWhile(Immutator<Boolean, ? super T> pred) {
    Memo<Actually<T>> curHead = Memo.<Actually<T>>from(() -> 
        Actually.<T>ok(this.head()).check(pred));
    return new InfiniteList<T>(
        curHead,
        Memo.<InfiniteList<T>>from(() -> curHead.get().transform(x -> this.tail().takeWhile(pred))
            .unless(InfiniteList.<T>end()))
        );
  }

  /**
   * Evaluates the InfiniteList and returns it as a List.
   *
   * @return the new List
   */
  public List<T> toList() {
    ArrayList<T> list = new ArrayList<T>();
    this.head.get().finish(x -> list.add(x));
    list.addAll(this.tail.get().toList());
    return list;
  }


  /**
   * Applies the acc function on the elements iteratively and returns the final result.
   *
   * @param <U> genetic type
   * @param acc the combiner
   * @param id the initial element
   * @return the final value
   */
  public <U> U reduce(U id, Combiner<U, U, ? super T> acc) {
    return this.head.get().transform(x -> 
        acc.combine(this.tail.get().reduce(id, acc), this.head()))
        .except(() -> this.tail.get().<U>reduce(id, acc));
  }


  /**
   * Returns the number of elements in the InfiniteList.
   *
   * @return the number of elements
   */
  public long count() {
    return this.toList().size();
  }

  /**
   * Returns the string representation of this InfiniteList.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return "[" + this.head + " " + this.tail + "]";
  }

  /**
   * Returns a boolean describing if the current element is of instance "End".
   *
   * @return the boolean
   */
  public boolean isEnd() {
    return false;
  }

  /**
   * Returns the cached instance of End.
   *
   * @param <R> generic type
   * @return the instance of end
   */
  public static <R> InfiniteList<R> end() {
    @SuppressWarnings("unchecked")
    InfiniteList<R> ans = (InfiniteList<R>) InfiniteList.END;
    return ans;
  }


  // Add your End class here...

  /**
   * Private nested class that represents the end of an InfiniteList.
   */
  private static class End extends InfiniteList<Object> {
    /**
     * Protected constructor.
     */
    protected End() {
      super(null, null);
    }

    @Override
    public Object head() {
      throw new java.util.NoSuchElementException();
    }

    @Override
    public InfiniteList<Object> tail() {
      throw new java.util.NoSuchElementException();
    }

    @Override
    public <R> InfiniteList<R> map(Immutator<? extends R, ? super Object> f) {
      return super.<R>end();
    }

    @Override
    public InfiniteList<Object> filter(Immutator<Boolean, ? super Object> pred) {
      return super.<Object>end();
    }

    @Override
    public InfiniteList<Object> limit(long n) {
      return super.<Object>end();
    }

    @Override
    public InfiniteList<Object> takeWhile(Immutator<Boolean, ? super Object> pred) {
      return super.<Object>end();
    }

    @Override
    public List<Object> toList() {
      return new ArrayList<Object>();
    }

    @Override
    public <U> U reduce(U id, Combiner<U, U, ? super Object> acc) {
      return id;
    }

    @Override 
    public boolean isEnd() {
      return true;
    }

    @Override
    public String toString() {
      return "-";
    }
  }
}
