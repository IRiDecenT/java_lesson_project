package calculator;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class MathUtils {
    // ���������ʽ���м���
    public static String calcString(String str) {
        // �� �� �����滻
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '��') {
                sb.append(Math.PI);
                continue;
            }
            sb.append(str.charAt(i));
        }
        str = sb.toString();
        // �� ( ) ���д���
        List<StringBuffer> list = new ArrayList<StringBuffer>();
        int level = 0;
        list.add(new StringBuffer());
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '(') {
                list.add(new StringBuffer());
                level++;
                continue;
            }
            if (str.charAt(i) == ')') {
                list.get(level - 1).append(String.valueOf(calcString(list.get(level).toString())));
                list.remove(level);
                level--;
                continue;
            }
            list.get(level).append(str.charAt(i));
        }
        if(calcLevel1(list.get(level).toString())==null)
            return null;
        return subZeroAndDot(calcLevel1(list.get(level).toString()));
    }

    /*
     *  ��һ�㼶 + - ����
     */
    private static String calcLevel1(String str) {

        str = str.replace("--", "+");
        str = str.replace("+-", "-");
        str = str.replace("��+", "��");
        str = str.replace("/+", "��");
        str = str.replace("^+", "^");

        if (str.contains("��-")) {
            return "error:��-";
        }
        str = str.replace("--", "+");
        str = str.replace("+-", "-");

        StringBuffer sb = new StringBuffer(str);
        int commandCount = 0;// ��������

        int j = 0;// ������
        // ������+ -�������������n+1������
        for (j = 0; j < sb.length() - 1; j++) {
            // �Ե�һ�������Ž��д���
            if (j == 0 && sb.charAt(j) == '-') {
                continue;
            }
            if (j == 0 && sb.charAt(j) == '+') {
                continue;
            }
            String flag = "" + str.charAt(j) + str.charAt(j + 1);
            if (flag.equals("��-") || flag.equals("��-") || flag.equals("^-")) {
                j++;
                continue;
            }
            if (sb.charAt(j) == '+' || sb.charAt(j) == '-')
                commandCount++;
        }

        // ��ʼ����������
        char[] command = new char[commandCount];
        // ��ʼ���������飨���ַ�����ʾ��
        String[] num = new String[commandCount + 1];
        for (j = 0; j < num.length; j++) {
            num[j] = "";
        }

        // ����һ�飬��ÿ�����ִ���������飬ÿ�����Ŵ����������
        int k = 0;
        for (j = 0; j < sb.length(); j++) {
            // �Ե�һ�����Ƿ�Ϊ�������д���
            if (j == 0 && sb.charAt(j) == '-') {
                num[k] += sb.charAt(j);
                continue;
            }
            if (j == 0 && sb.charAt(j) == '+') {
                continue;
            }
            // �� *- /- ^- ���д���
            if (j + 1 < sb.length()) {
                String flag = "" + sb.charAt(j) + sb.charAt(j + 1);
                if (flag.equals("��-") || flag.equals("��-") || flag.equals("^-")) {
                    num[k] += flag;
                    j++;
                    continue;
                }
            }
            //�� + - ��������б���
            if (sb.charAt(j) == '+' || sb.charAt(j) == '-') {
                command[k] = sb.charAt(j);
                k++;
                continue;
            }
            // �����ּ����߼����㱣����num[k]��
            num[k] += sb.charAt(j);
        }
        // ��num[i]���и��߼�����ʱ��num[i]���뵽calcLevel2(num[i])�����н��н��
        for (int i = 0; i < num.length; i++) {
            if (num[i].contains("��") || num[i].contains("��") || num[i].contains("^") || num[i].contains("!")
                    || num[i].contains("��")) {
                if(calcLevel2(num[i])==null){
                    return null;
                }
                num[i] = calcLevel2(num[i]);
            }
        }
        // ���ֻ����һ���� �����������ֵ
        if (num.length == 1) {
            return num[0];
        }
        double result = 0;
        for (int i = 0; i < commandCount; i++) {
            // ȡǰ���������͵�һ��������������
            double num1 = 0;
            double num2 = 0;
            try {
                num1 = Double.parseDouble(num[i]);
                num2 = Double.parseDouble(num[i + 1]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            //ȡ+ - �������������
            char cc = command[i];
            if (cc == '+') {
                result = num1 + num2;
            } else if (cc == '-') {
                result = num1 - num2;
            }
            num[i + 1] = String.valueOf(result);
        }
        return String.valueOf(result);
    }

    /*
     *  �ڶ��㼶 * / ����
     */
    private static String calcLevel2(String str) {
        StringBuffer sb = new StringBuffer(str);
        int commandCount = 0;// ��������

        int j = 0;// ������
        // �����ж��ٸ������������n+1������
        for (j = 0; j < sb.length(); j++) {
            if (sb.charAt(j) == '��' || sb.charAt(j) == '��')
                commandCount++;
        }

        // ��ʼ����������
        char[] command = new char[commandCount];
        // ��ʼ���������飨���ַ�����ʾ��
        String[] num = new String[commandCount + 1];
        for (j = 0; j < num.length; j++) {
            num[j] = "";
        }

        // ����һ�飬��ÿ�����ִ���������飬* /���Ŵ����������
        int k = 0;
        for (j = 0; j < sb.length(); j++) {
            if (sb.charAt(j) == '��' || sb.charAt(j) == '��') {
                command[k] = sb.charAt(j);
                k++;
                continue;
            }
            // �����ּ����߼����㱣����num[k]��
            num[k] += sb.charAt(j);
        }
        // ��num[i]���и��߼�����ʱ��num[i]���뵽calcLevel3(num[i])�����н��н��
        for (int i = 0; i < num.length; i++) {
            if (num[i].contains("^") || num[i].contains("!") || num[i].contains("��")) {
                num[i] = calcLevel3(num[i]);
            }
        }
        // ���ֻ����һ���� �����������ֵ
        if (num.length == 1) {
            return num[0];
        }
        double result = 0;
        for (int i = 0; i < commandCount; i++) {
            // ȡǰ���������͵�һ��������������
            double num1 = 0;
            double num2 = 0;
            try {
                num1 = Double.parseDouble(num[i]);
                num2 = Double.parseDouble(num[i + 1]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            char cc = command[i];
            if (cc == '��') {
                result = num1 * num2;
            } else if (cc == '��') {
                if(num2==0){
                    JOptionPane.showMessageDialog(null, "��������Ϊ0!", "", JOptionPane.DEFAULT_OPTION);
                    return null;
                }
                result = num1 / num2;
            }
            num[i + 1] = String.valueOf(result);
        }
        return String.valueOf(result);
    }

    /*
     *  �����㼶 ^ ����
     */
    private static String calcLevel3(String str) {
        StringBuffer sb = new StringBuffer(str);
        int commandCount = 0;// ��������

        int j = 0;// ������
        // �����ж��ٸ������������n+1������
        for (j = 0; j < sb.length(); j++) {
            if (sb.charAt(j) == '^')
                commandCount++;
        }

        // ��ʼ���������飨���ַ�����ʾ��
        String[] num = new String[commandCount + 1];
        for (j = 0; j < num.length; j++) {
            num[j] = "";
        }

        // ����һ�飬��ÿ�����ִ���������飬^���Ŵ����������
        int k = 0;
        for (j = 0; j < sb.length(); j++) {
            if (sb.charAt(j) == '^') {
                k++;
                continue;
            }
            num[k] += sb.charAt(j);
        }
        for (int i = 0; i < num.length; i++) {
            if (num[i].contains("!") || num[i].contains("��")) {
                num[i] = calcLevel4(num[i]);
            }
        }
        // ���ֻ����һ���� �����������ֵ
        if (num.length == 1) {
            return num[0];
        }
        double result = 1;
        for (int i = 1; i < num.length; i++) {
            result *= Double.parseDouble(num[i]);
        }
        result = Math.pow(Double.parseDouble(num[0]), result);
        return String.valueOf(result);
    }

    /*
     *  ���Ĳ㼶 ! �� ����
     *  �� Ƕ��ʹ�ý������ ()
     */
    private static String calcLevel4(String str) {
        StringBuffer sb = new StringBuffer(str);
        String num = "";
        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) <= '9' && sb.charAt(i) >= '0' || sb.charAt(i) == '.') {
                num += sb.charAt(i);
            }
        }
        if (str.contains("!")) {
            try {
                num = String.valueOf(factorial(Integer.parseInt(num)));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        if (str.contains("��")) {
            try {
                num = String.valueOf(Math.sqrt(Double.parseDouble(num)));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return num;
    }

    // ��n�Ľ׳� n!
    public static int factorial(int n) {
        if (n == 0) {
            return 1;
        }
        int num = n;
        for (int i = n; i > 1; i--) {
            num *= (i - 1);
        }
        return num;
    }

    private static String subZeroAndDot(String s){
        if(s.indexOf(".") > 0){
            s = s.replaceAll("0+?$", "");//ȥ�������0
            s = s.replaceAll("[.]$", "");//�����һλ��.��ȥ��
        }
        return s;
    }
}
