import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {

    public static int priority(String op) {
        if(op == null) return 0;
        switch(op) {
            case "(" :
                return 1;
            case "+" :
            case "-" :
                return 2;
            case "*" :
            case "/" :
                return 3;
            case "^" :
                return 4;
            default :
                break;
        }
        return 0;
    }

    //解析算式并使用栈存储计算
    public static String count(String str) {
        Stack<Double> number = new Stack<Double>();
        Stack<String> operate = new Stack<String>();

        operate.push(null);

        Pattern p = Pattern.compile("(?<!\\d)-?\\d+(\\.\\d+)?|[+\\-*/()^]");
        Matcher m = p.matcher(str);

        while(m.find()) {
            String val = m.group();
            Operate ops = new Operate();	//储存算式符对象

            if(!Global.isNaN(val)) {
                //System.out.println(val);
                //处理括号
                if(val.equals("(")) {
                    operate.push(val);
                }else if(val.equals(")")) {
                    //循环弹栈算法符
                    String op;
                    while(!(op = operate.pop()).equals("(")) {
                        double a = number.pop();
                        double b = number.pop();

                        number.push(ops.getAlgorithm(op).Compute(a, b));
                    }
                }else if(!val.equals("C") && !val.equals("=")){
                    while(priority(val) <= priority(operate.peek())) {
                        double a = number.pop();
                        double b = number.pop();
                        String op = operate.pop();

                        number.push(ops.getAlgorithm(op).Compute(a, b));
                    }
                    operate.push(val);
                    System.out.println("operate " + operate);
                }

            }else {

                number.push(Double.valueOf(val));
                System.out.println("number " + number);
            }
        }

        while(operate.peek() != null) {
            double a = number.pop();
            double b = number.pop();
            String op = operate.pop();
            Operate ops = new Operate();

            number.push(ops.getAlgorithm(op).Compute(a, b));
        }

        return number.pop() + "";
    }

    public static void main(String[] args) throws Exception {
        String str = "-1+(2*2)+3+(3/2+(2-1))";
        System.out.println(count(str));
        // String str = "!";
        // System.out.println(isNaN(str));
    }

}