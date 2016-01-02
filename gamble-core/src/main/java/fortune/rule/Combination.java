package fortune.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lingda on 2016/1/2.
 */
public class Combination {

    AtomicInteger size = new AtomicInteger(0);

    public <T> int combination(T[] array, int n) {
        return combination(array, new int[n], 0, n);
    }

    public <T> int combination(T[] array, int[] indexes, int start, int n) {
        if (n == 1) {
            String prefix = generatePrefix(array, indexes);
            for (int i = start; i < array.length; i++) {
                System.out.print(prefix);
                System.out.print(array[i]);
                System.out.println(']');
                size.incrementAndGet();
            }
        } else {
            for (int i = start; i <= array.length - n; i++) {
                indexes[indexes.length - n] = i;
                combination(array, indexes, i + 1, n - 1);
            }
        }
        return size.intValue();
    }

    private <T> String generatePrefix(T[] array, int[] indexes) {
        StringBuilder prefixBuilder = new StringBuilder("[");
        for (int i = 0; i < indexes.length - 1; i++) {
            prefixBuilder.append(array[indexes[i]]).append(", ");
        }
        return prefixBuilder.toString();
    }

    public static int getCombinationSize(int n, int m) {
        Combination c = new Combination();
        int size = c.combination(new Integer[n], m);
        return size;
    }

    public static void main(String[] args) {
        Combination c = new Combination();
        System.out.println(c.combination(new Integer[]{1, 2, 3, 4, 5, 6}, 3));
        getCombinationSize(2, 2);
    }

}
