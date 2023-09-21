import java.util.Scanner;

public class remove {

    //��������Ƿ����ɾ��
    public int check(char[] s, int left, int right)
    {
        int i;            //�±�
        int leftCount;    //������ͳ��
        if(left>0) {
            //���� ' -(a +|- b) '
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

            //���� ' /(a +|-|*|/ b) '
            if (s[left - 1] == '/') {
                return 0;
            }

            //���� ' +(a +|-|*|/ b) +|- '
            if (s[left - 1] != '*' && s[left - 1] != '/' &&
                    s[right + 1] != '*' && s[right + 1] != '/') {
                return 1;
            }
        }else {
            if(right==s.length-1)
                return 1;
        }
        //���� ' *(a *|/ b) +|-|*|/ '
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

    //ɾ�����������
    public int delExcessBrackets(char[] s, int index)
    {
        int left, right;
        while (index<s.length) {
            if (s[index] == ')')     //���Ϊ�����ţ������±�
            {
                return index;
            }
            if (s[index] == '(')     //���Ϊ�����ţ��ҵ������ŵ��±�
            {
                left = index;
                index = right =  delExcessBrackets(s, index+1);

                if (check(s, left, right)==1)    //�������Ϊ����ɾ������ô������λ�û��ɿ�
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
