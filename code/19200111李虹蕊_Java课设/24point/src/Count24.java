
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Count24 {

    private final int N = 4;
    private final int res = 24;
    private int[] A; // 输入的四个数
    private Map<Integer, Set<Elem>> map; // 用来进行动规的map
    private Set<String> answers; // 答案集

    public Count24(int[] a) {
        A = a;
        map = new HashMap<Integer, Set<Elem>>();
        answers = new HashSet<String>();
    }

    public void run()  {
        // 用二进制数来表示集合和子集的概念，包含4个元素的集合共有16个子集0-15
        for (int i = 0; i < (1 << N); i++) {
            Set<Elem> set = new HashSet<Elem>();
            map.put(i, set);
        }
        for (int i = 0; i < N; i++) {
            Elem e = new Elem(A[i], A[i] + "");
            Set<Elem> set = new HashSet<Elem>();
            set.add(e);
            map.put(1 << i, set);
        }
        // 递归生成动规结果
        for (int i = 1; i < (1 << N); i++) {
            dynamic(i);
        }
        // 开始1111 表示四个数
        Set<Elem> mSet = map.get((1 << N) - 1);
        for (Elem e : mSet) {
            if (e.res == res) {  // 若式子结果等于24，则去除不必要括号后添加到不重复的结果集
                answers.add(new remove().run(e.info));
            }
        }
        // 输出结果
        if (answers.size() == 0) {
            System.out.println("无解");
        } else {
            for (String s : answers)
                System.out.println(s);
            System.out.println("共有" + answers.size() + "个解");
        }

    }

    public Set<Elem> dynamic(int m) {
        Set<Elem> mSet = map.get(m);
        if (mSet.size() > 0) {
        }
        else {
            for (int x = 1; x <= m; x++) {
                if ((x & m) == x) { // 如果x在集合m内
                    Set<Elem> s1 = dynamic(x);
                    Set<Elem> s2 = dynamic(m - x);
                    // 得到两个子集合的笛卡尔积，并对结果集合的元素对进行6种运算
                    for (Elem e1 : s1) {
                        for (Elem e2 : s2) {

                            String str = "(" + e1.info + "+" + e2.info + ")";
                            mSet.add(new Elem(e1.res + e2.res, str));

                            str = "(" + e1.info + "-" + e2.info + ")";
                            mSet.add(new Elem(e1.res - e2.res, str));

                            str = "(" + e2.info + "-" + e1.info + ")";
                            mSet.add(new Elem(e2.res - e1.res, str));

                            str = "(" + e1.info + "*" + e2.info + ")";
                            mSet.add(new Elem(e1.res * e2.res, str));

                            if (e1.res != 0) {
                                str = "(" + e2.info + "/" + e1.info + ")";
                                mSet.add(new Elem(e2.res / e1.res, str));
                            }
                            if (e2.res != 0) {
                                str = "(" + e1.info + "/" + e2.info + ")";
                                mSet.add(new Elem(e1.res / e2.res, str));
                            }
                        }
                    }
                }
            }
        }
        return mSet;
    }
}
