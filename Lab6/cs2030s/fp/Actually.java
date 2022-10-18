package cs2030s.fp;

public abstract class Actually<T> implements Immutatorable<T> {

  public static <T> Actually<T> ok(T res) {
    return new Success<T>(res);
  }

  @SuppressWarnings("unchecked")
  public static <T> Actually<T> err(Exception exc) {
    return (Actually<T>) new Failure(exc);
  }

  public abstract T unwrap() throws Exception;

  public abstract <U extends T> T except(Constant<? extends U> constant);

  public abstract <T> void finish(Action<? super T>  action);

  public abstract <S extends T> T unless(S s);

  public abstract <R> Actually<R> next(Immutator<? extends Actually<? extends R>, ? super T> immutator);

  /*
   *    * Nested Class Failure
   *       */
  static class Failure extends Actually<Object> {
    private Exception exc;

    public Failure(Exception exc) {
      this.exc = exc;
    }

    @Override
    public Object unwrap() throws Exception {
      throw this.exc;
    }

    @Override
    public <U extends Object> Object except(Constant<? extends U> constant) {
      return constant.init();
    }

    @Override
    public <T> void finish(Action<? super T> action) {
    }

    @Override
    public Object unless(Object obj) {
      return obj;
    }

    @Override
    public <T> Actually<T> transform(Immutator<? extends T, ? super Object> immutator) {
      return Actually.err(exc);
    }

    @Override
    public <R> Actually<R> next(Immutator<? extends Actually<? extends R>, ? super Object> immutator) {
      return Actually.err(exc);
    }

    @Override
    public boolean equals(Object obj) {
      if (obj instanceof Failure) {
        Failure other = (Failure) obj;
        if (this.exc.getMessage() == null || other.exc.getMessage() == null) {
          return false;
        }
        if (this.exc == null || other.exc == null) {
          return false;
        }
        if (this.exc.getMessage() == other.exc.getMessage()) {
          return true;
        }
        return this.exc.getMessage().equals(other.exc.getMessage());
      }
      return false;
    }

    @Override
    public String toString() {
      return "[" + this.exc.getClass().getCanonicalName() + "] " + this.exc.getMessage(); 
    }
  }

  static class Success<T> extends Actually<T> {
    private T res;

    public Success(T res) {
      this.res = res;  
    }

    @Override
    public T unwrap() {
      return this.res;
    }

    @Override
    public <U extends T> T except(Constant<? extends U> constant) {
      return this.res;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <S> void finish(Action<? super S> action) {
      action.call((S) this.res);
    }

    @Override
    public <S extends T> T unless(S s) {
      return this.res;
    }

    @Override
    public <S> Actually<S> transform(Immutator<? extends S, ? super T> immutator) {
      try {
        return Actually.<S>ok(immutator.invoke(this.res));
      } catch (Exception e) {
        return Actually.err(e);
      }
    }

    @Override
    public <R> Actually<R> next(Immutator<? extends Actually<? extends R>, ? super T> immutator) {
      try {
        @SuppressWarnings("unchecked")
        Actually<R> returnVal = (Actually<R>) immutator.invoke(Success.this.res);
        return returnVal;
      }
      catch (Exception e) {
        return Actually.err(e);
      }
    }

    @Override
    public boolean equals(Object obj) {
      if (obj instanceof Success<?>) {
        Success<?> other = (Success<?>) obj;
        if (this.res == other.res) {
          return true;
        }
        if (this.res == null || other.res == null) {
          return false;
        }
        return this.res.equals(other.res);
      }
      return false;
    }

    @Override
    public String toString() {
      return "<" + this.res + ">";
    }
  }

}
