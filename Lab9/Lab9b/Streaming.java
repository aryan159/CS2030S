import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

public class Streaming {
  public static <T> List<Pair<Integer, T>> encode(Stream<T> stream) {
    return stream
      .reduce(
        new ArrayList<Pair<Integer, T>>(),
        (arr, x) -> {
          ArrayList<Pair<Integer, T>> newArr = new ArrayList<Pair<Integer, T>>();
          newArr.addAll(arr);
          if (!newArr.isEmpty() && newArr.get(newArr.size() - 1).getSnd() == x) {
            Pair<Integer, T> cur = newArr.get(newArr.size() - 1);
            cur.setFst(cur.getFst() + 1);
          }
          else {
            newArr.add(new Pair<Integer, T>(1, x));
          }
          return newArr;
        },
        (arr1, arr2) -> {
          if (!arr1.isEmpty() 
              && !arr2.isEmpty()
              && arr1.get(arr1.size() - 1).getSnd() == arr2.get(0).getSnd()
              ) {
            arr2.get(0).setFst(
                arr2.get(0).getFst()
                + arr1.get(arr1.size() - 1).getFst());
            arr1.remove(arr1.size() - 1);
          }
          arr1.addAll(arr2);
          return arr1;
        }
      );
  }
}
