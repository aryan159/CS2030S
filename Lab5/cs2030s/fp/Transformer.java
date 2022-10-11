package cs2030s.fp;

public abstract class Transformer<R, P> implements Immutator<R, P> {
  public <N> Transformer<R, N> after (Transformer<P, N> transformer) {
    
  }
}
