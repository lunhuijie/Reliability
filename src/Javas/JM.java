package Javas;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
public class JM {
	public  double P;
	public  int n;
	public  int left;
	public  int right;
	public  int root;
	public  int N;
	public  double EX;
	public  double EV;
	public  double sita;
	public  int[] t=new int[2000];
	/*public   void intFileTOData(int length, File file) throws Exception {
       InputStreamReader reader = new InputStreamReader(new FileInputStream(file)); 
       @SuppressWarnings("resource")
	BufferedReader br = new BufferedReader(reader); 
       String line = "";
       String num="";
       line = br.readLine();
       while (line != null) {
    	  line=line.trim();
    	  num=line.substring(4);
    	  int a = Integer.parseInt(num);
    	  String xiabiao = line.substring(0,3);
    	  xiabiao=xiabiao.trim();
    	  int i=Integer.parseInt(xiabiao);
    	  t[i]=a;
          line = br.readLine();
       }      
	}*/

	public   void calculateP() {
		double num=0;
		for(int i=1;i<=n;i++) {
			num+=(i-1)*(t[i]-t[i-1]);
		}
		System.out.println("asfda");
		System.out.println(t[n]);
		P=num/t[n];
	}
	public   double fun(int N) {
		double num=0;
		for(int i=1;i<=n;i++) {
			num+=1/(N-i+1);
		}
		num=num-n/(N-P);
		return num;
	}
	public   void stepOne() {
		if(P>(n-1)/2) {
			left=n-1;
			right=n;
			stepTwo();
		}
		else {
			//��ֹ����
		}
		
	}
	public   void stepTwo() {
		double num=fun(right);
		double NEX=0-EX;
		if(num>EV) {
			left=right;
			right++;
			stepTwo();
		}
		else if(num>=NEX && num<=EV) {
			root=right;
			stepFive();
		}
		else {
			stepThree();
		}
	}
	public   void stepThree() {
		int num=Math.abs(right-left);
		root=(right+left)/2;
		if(num<EX) {
			stepFive();
		}
		else if(num>EX){
			stepFour();
		}
	}
	public   void stepFour() {
		double num=fun(root);
		double NEV=0-EV;
		if(num>EV) {
			left=root;
			stepThree();
		}
		else if(num>=NEV && num<=EV) {
			stepFive();
		}
		else {
			right=root;
			stepThree();
		}
	}
	public   void stepFive() {
		N=root;
		double num=0;
		for(int i=1;i<=n;i++) {
			num+=(i-1)*(t[i]-t[i-1]);
		}
		sita=n/(N*t[n]-num);
	}

	public   String Test(int[] b,double ev,double ex, int tn) {
		
		EX=ex;
		EV=ev;
		n = tn;
		for(int k = 1;k<=n;k++) {
			t[k] = b[k -1];
        	//System.out.println(t[k]);
        }
		calculateP();
		System.out.println(P);
		System.out.println(EX);
        System.out.println(EV);
        System.out.println(n);
		
        
		//for(int i=1;i<100;i++) {
			//EX++;
			//EV++;
			stepOne();
			System.out.println(sita);
			String str = "{sita:"+sita + ",N:" + N + "}"; 
			
			return str;
			//System.out.println("EX="+EX+",EV="+EV+",N="+N+",sita="+sita);
		//}
	}
	/*public   void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String pathName="src/test.txt";
		File filename=new File(pathName);
		intFileTOData(10,filename);		
		Test();
	}*/

}
