import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class CalcMathUtils {
    // 对算术表达式进行计算
    public static String calcString(String str) {
        // 对 π 进行替换
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == 'π') {
                sb.append(Math.PI);
                continue;
            }
            sb.append(str.charAt(i));
        }
        str = sb.toString();
        // 对 ( ) 进行处理
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
     *  第一层级 + - 运算
     */
    private static String calcLevel1(String str) {

        str = str.replace("--", "+");
        str = str.replace("+-", "-");
        str = str.replace("×+", "×");
        str = str.replace("/+", "÷");
        str = str.replace("^+", "^");

        if (str.contains("√-")) {
            return "error:√-";
        }
        str = str.replace("--", "+");
        str = str.replace("+-", "-");

        StringBuffer sb = new StringBuffer(str);
        int commandCount = 0;// 符号数量

        int j = 0;// 计数器
        // 计算有+ -个运算符，就有n+1个数字
        for (j = 0; j < sb.length() - 1; j++) {
            // 对第一个数符号进行处理
            if (j == 0 && sb.charAt(j) == '-') {
                continue;
            }
            if (j == 0 && sb.charAt(j) == '+') {
                continue;
            }
            String flag = "" + str.charAt(j) + str.charAt(j + 1);
            if (flag.equals("×-") || flag.equals("÷-") || flag.equals("^-")) {
                j++;
                continue;
            }
            if (sb.charAt(j) == '+' || sb.charAt(j) == '-')
                commandCount++;
        }

        // 初始化符号数组
        char[] command = new char[commandCount];
        // 初始化数字数组（用字符串表示）
        String[] num = new String[commandCount + 1];
        for (j = 0; j < num.length; j++) {
            num[j] = "";
        }

        // 遍历一遍，把每个数字存进数字数组，每个符号存进符号数组
        int k = 0;
        for (j = 0; j < sb.length(); j++) {
            // 对第一个数是否为负数进行处理
            if (j == 0 && sb.charAt(j) == '-') {
                num[k] += sb.charAt(j);
                continue;
            }
            if (j == 0 && sb.charAt(j) == '+') {
                continue;
            }
            // 对 *- /- ^- 进行处理
            if (j + 1 < sb.length()) {
                String flag = "" + sb.charAt(j) + sb.charAt(j + 1);
                if (flag.equals("×-") || flag.equals("÷-") || flag.equals("^-")) {
                    num[k] += flag;
                    j++;
                    continue;
                }
            }
            //对 + - 运算符进行保存
            if (sb.charAt(j) == '+' || sb.charAt(j) == '-') {
                command[k] = sb.charAt(j);
                k++;
                continue;
            }
            // 将数字及更高级运算保存在num[k]中
            num[k] += sb.charAt(j);
        }
        // 当num[i]中有更高级运算时对num[i]传入到calcLevel2(num[i])方法中进行解决
        for (int i = 0; i < num.length; i++) {
            if (num[i].contains("×") || num[i].contains("÷") || num[i].contains("^") || num[i].contains("!")
                    || num[i].contains("√")) {
                if(calcLevel2(num[i])==null){
                    return null;
                }
                num[i] = calcLevel2(num[i]);
            }
        }
        // 如果只包含一个数 返回这个数的值
        if (num.length == 1) {
            return num[0];
        }
        double result = 0;
        for (int i = 0; i < commandCount; i++) {
            // 取前两个数，和第一个操作符，运算
            double num1 = 0;
            double num2 = 0;
            try {
                num1 = Double.parseDouble(num[i]);
                num2 = Double.parseDouble(num[i + 1]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            //取+ - 运算符进行运算
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
     *  第二层级 * / 运算
     */
    private static String calcLevel2(String str) {
        StringBuffer sb = new StringBuffer(str);
        int commandCount = 0;// 符号数量

        int j = 0;// 计数器
        // 计算有多少个运算符，就有n+1个数字
        for (j = 0; j < sb.length(); j++) {
            if (sb.charAt(j) == '×' || sb.charAt(j) == '÷')
                commandCount++;
        }

        // 初始化符号数组
        char[] command = new char[commandCount];
        // 初始化数字数组（用字符串表示）
        String[] num = new String[commandCount + 1];
        for (j = 0; j < num.length; j++) {
            num[j] = "";
        }

        // 遍历一遍，把每个数字存进数字数组，* /符号存进符号数组
        int k = 0;
        for (j = 0; j < sb.length(); j++) {
            if (sb.charAt(j) == '×' || sb.charAt(j) == '÷') {
                command[k] = sb.charAt(j);
                k++;
                continue;
            }
            // 将数字及更高级运算保存在num[k]中
            num[k] += sb.charAt(j);
        }
        // 当num[i]中有更高级运算时对num[i]传入到calcLevel3(num[i])方法中进行解决
        for (int i = 0; i < num.length; i++) {
            if (num[i].contains("^") || num[i].contains("!") || num[i].contains("√")) {
                num[i] = calcLevel3(num[i]);
            }
        }
        // 如果只包含一个数 返回这个数的值
        if (num.length == 1) {
            return num[0];
        }
        double result = 0;
        for (int i = 0; i < commandCount; i++) {
            // 取前两个数，和第一个操作符，运算
            double num1 = 0;
            double num2 = 0;
            try {
                num1 = Double.parseDouble(num[i]);
                num2 = Double.parseDouble(num[i + 1]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            char cc = command[i];
            if (cc == '×') {
                result = num1 * num2;
            } else if (cc == '÷') {
                if(num2==0){
                    JOptionPane.showMessageDialog(null, "除数不能为0!", "", JOptionPane.DEFAULT_OPTION);
                    return null;
                }
                result = num1 / num2;
            }
            num[i + 1] = String.valueOf(result);
        }
        return String.valueOf(result);
    }

    /*
     *  第三层级 ^ 运算
     */
    private static String calcLevel3(String str) {
        StringBuffer sb = new StringBuffer(str);
        int commandCount = 0;// 符号数量

        int j = 0;// 计数器
        // 计算有多少个运算符，就有n+1个数字
        for (j = 0; j < sb.length(); j++) {
            if (sb.charAt(j) == '^')
                commandCount++;
        }

        // 初始化数字数组（用字符串表示）
        String[] num = new String[commandCount + 1];
        for (j = 0; j < num.length; j++) {
            num[j] = "";
        }

        // 遍历一遍，吧每个数字存进数字数组，^符号存进符号数组
        int k = 0;
        for (j = 0; j < sb.length(); j++) {
            if (sb.charAt(j) == '^') {
                k++;
                continue;
            }
            num[k] += sb.charAt(j);
        }
        for (int i = 0; i < num.length; i++) {
            if (num[i].contains("!") || num[i].contains("√")) {
                num[i] = calcLevel4(num[i]);
            }
        }
        // 如果只包含一个数 返回这个数的值
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
     *  第四层级 ! √ 运算
     *  若 嵌套使用建议采用 ()
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
        if (str.contains("√")) {
            try {
                num = String.valueOf(Math.sqrt(Double.parseDouble(num)));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return num;
    }

    // 求n的阶乘 n!
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
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }
}
