import java.util.Scanner;

public class main {
	  public static void main(String[] args) {
	        int[] a = new int[4];
	        boolean flag = true;
	        Scanner sc = new Scanner(System.in);
	        for (int i = 0; i < 4; i++) {
	            a[i] = sc.nextInt();
	            if(a[i]>13 || a[i]<=0)
	                flag = false;
	        }
	        if(!flag) {
	            System.out.println("ÊäÈë´íÎó£¡");
	            System.exit(1);
	        }
	        Count24 cal = new Count24(a);
	        cal.run();
	        sc.close();
	    }
}
