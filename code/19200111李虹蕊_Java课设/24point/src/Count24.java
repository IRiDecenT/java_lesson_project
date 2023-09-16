
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Count24 {

    private final int N = 4;
    private final int res = 24;
    private int[] A; // ������ĸ���
    private Map<Integer, Set<Elem>> map; // �������ж����map
    private Set<String> answers; // �𰸼�

    public Count24(int[] a) {
        A = a;
        map = new HashMap<Integer, Set<Elem>>();
        answers = new HashSet<String>();
    }

    public void run()  {
        // �ö�����������ʾ���Ϻ��Ӽ��ĸ������4��Ԫ�صļ��Ϲ���16���Ӽ�0-15
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
        // �ݹ����ɶ�����
        for (int i = 1; i < (1 << N); i++) {
            dynamic(i);
        }
        // ��ʼ1111 ��ʾ�ĸ���
        Set<Elem> mSet = map.get((1 << N) - 1);
        for (Elem e : mSet) {
            if (e.res == res) {  // ��ʽ�ӽ������24����ȥ������Ҫ���ź���ӵ����ظ��Ľ����
                answers.add(new remove().run(e.info));
            }
        }
        // ������
        if (answers.size() == 0) {
            System.out.println("�޽�");
        } else {
            for (String s : answers)
                System.out.println(s);
            System.out.println("����" + answers.size() + "����");
        }

    }

    public Set<Elem> dynamic(int m) {
        Set<Elem> mSet = map.get(m);
        if (mSet.size() > 0) {
        }
        else {
            for (int x = 1; x <= m; x++) {
                if ((x & m) == x) { // ���x�ڼ���m��
                    Set<Elem> s1 = dynamic(x);
                    Set<Elem> s2 = dynamic(m - x);
                    // �õ������Ӽ��ϵĵѿ����������Խ�����ϵ�Ԫ�ضԽ���6������
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
