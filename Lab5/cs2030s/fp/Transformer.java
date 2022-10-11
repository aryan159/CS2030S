package cs2030s.fp;

public abstract class Transformer<R, P> implements Immutator<R, P> {
  public <N> Transformer<R, N> after (Transformer<P, N> transformer) {
    Transformer<R, P> cur = this;
    class Combined extends Transformer<R, N> {
      public R invoke(N n) {
        return cur.invoke(transformer.invoke(n));
      }
    }
    return new Combined();
  }

  public <T> Transformer<T, P> before (Transformer<T, R> transformer) {
    Transformer<R, P> cur = this;
    class Combined extends Transformer<T, P> {
      public T invoke(P p) {
        return transformer.invoke(cur.invoke(p));
      }
    }
    return new Combined();
  }
}
