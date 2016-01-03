package fortune.rule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lingda on 2016/1/3.
 */
public class Combinator {

    List<List<Integer>> combinationList = new ArrayList<>();

    public void combine(int[] a, int n) {

        if (null == a || a.length == 0 || n <= 0 || n > a.length)
            return;

        int[] b = new int[n];//辅助空间，保存待输出组合数
        getCombination(a, n, 0, b, 0);
    }

    private void getCombination(int[] a, int n, int begin, int[] b, int index) {

        if (n == 0) {//如果够n个数了，输出b数组
            List list = new ArrayList<>();
            for (int i = 0; i < index; i++) {
                System.out.print(b[i] + " ");
                list.add(b[i]);
            }
            combinationList.add(list);
            System.out.println();
            return;
        }

        for (int i = begin; i < a.length; i++) {

            b[index] = a[i];
            getCombination(a, n - 1, i + 1, b, index + 1);
        }

    }

    public static void main(String[] args) {

        Combinator robot = new Combinator();

        int[] a = {1, 2, 3, 4};
        int n = 2;
        robot.combine(a, n);

        System.out.println(robot.combinationList);
    }

    public static List<List<Integer>> getCombinationList(int[] array, int n) {
        Combinator combinator = new Combinator();

        combinator.combine(array, n);

        return combinator.combinationList;
    }

}

