import java.util.Scanner;

public class remove {

    //检测括号是否可以删除
    public int check(char[] s, int left, int right)
    {
        int i;            //下标
        int leftCount;    //左括号统计
        if(left>0) {
            //处理 ' -(a +|- b) '
            if (s[left - 1] == '-') {
                i = left;
                leftCount = 1;
                while (++i < right) {
                    if (s[i] == '(') {
                        leftCount++;
                    } else if ((s[i] == '+' || s[i] == '-') && leftCount == 1) {
                        return 0;
                    }
                }
            }

            //处理 ' /(a +|-|*|/ b) '
            if (s[left - 1] == '/') {
                return 0;
            }

            //处理 ' +(a +|-|*|/ b) +|- '
            if (s[left - 1] != '*' && s[left - 1] != '/' &&
                    s[right + 1] != '*' && s[right + 1] != '/') {
                return 1;
            }
        }else {
            if(right==s.length-1)
                return 1;
        }
        //处理 ' *(a *|/ b) +|-|*|/ '
        i = left;
        leftCount = 1;
        while (++i < right) {
            if (s[i] == '(')
            {
                leftCount++;
            }
            else if ((s[i] == '*' || s[i] == '/' ) && leftCount == 1)
            {
                return 1;
            }
        }
        return 0;
    }

    //删除多余的括号
    public int delExcessBrackets(char[] s, int index)
    {
        int left, right;
        while (index<s.length) {
            if (s[index] == ')')     //如果为右括号，返回下标
            {
                return index;
            }
            if (s[index] == '(')     //如果为左括号，找到右括号的下标
            {
                left = index;
                index = right =  delExcessBrackets(s, index+1);

                if (check(s, left, right)==1)    //若检测结果为可以删除，那么把括号位置换成空
                {
                    s[left] = s[right] = ' ';
                }
            }
            index++;
        }
        return -1;
    }

    public String run(String s)
    {
        char[] exp=s.toCharArray();
        String res="";

        delExcessBrackets(exp, 0);

        int i = 0;
        while (i<exp.length) {
            if (exp[i] != ' ')
            {
                res+=exp[i];
            }
            i++;
        }

        return res;
    }
}
