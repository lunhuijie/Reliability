package Javas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Jelinski_Moranda {
	public static double P;
	public static int n=136;
	public static int left;
	public static int right;
	public static int root;
	public static int N;
	public static double EX;
	public static double EV;
	public static double sita=0;
	public static int[] t=new int[2000];
	public static void intFileTOData(int length, File file) throws Exception {
       /* ����TXT�ļ� */
       // String pathname = "trainData.txt"; //
       // ����·�������·�������ԣ������Ǿ���·����д���ļ�ʱ��ʾ���·��
       // File filename = new File(pathname); // Ҫ��ȡ����·����input��txt�ļ�
       InputStreamReader reader = new InputStreamReader(new FileInputStream(file)); // ����һ������������reader
       @SuppressWarnings("resource")
	BufferedReader br = new BufferedReader(reader); // ����һ�����������ļ�����ת�ɼ�����ܶ���������
       String line = "";
       String num="";
       line = br.readLine();
       while (line != null) {
    	  line=line.trim();//ȥ����ͷ�ͽ�β�Ŀո�
    	  num=line.substring(4);//��������
    	  int a = Integer.parseInt(num);
    	  String xiabiao = line.substring(0,3);//�����±�
    	  xiabiao=xiabiao.trim();
    	  int i=Integer.parseInt(xiabiao);
    	  t[i]=a;
          line = br.readLine(); // һ�ζ���һ������
       }      
	}

	public static void calculateP() {
		double num=0;
		for(int i=1;i<=n;i++) {
			num+=(i-1)*(t[i]-t[i-1]);
		}
		P=num/t[n];
	}
	public static double fun(int N) {
		double num=0;
		for(int i=1;i<=n;i++) {
			num+=1/(N-i+1);
		}
		num=num-n/(N-P);
		return num;
	}
	public static void stepOne() {
		if(P>(n-1)/2) {
			left=n-1;
			right=n;
			stepTwo();
		}
		else {
			//��ֹ����
		}
		
	}
	public static void stepTwo() {
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
	public static void stepThree() {
		int num=Math.abs(right-left);
		root=(right+left)/2;
		if(num<EX) {
			stepFive();
		}
		else if(num>EX){
			stepFour();
		}
	}
	public static void stepFour() {
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
	public static void stepFive() {
		N=root;
		double num=0;
		for(int i=1;i<=n;i++) {
			num+=(i-1)*(t[i]-t[i-1]);
		}
		sita=n/(N*t[n]-num);
	}
	public static void Test() {
		calculateP();
		EX=0;
		EV=0;
		for(int i=1;i<100;i++) {
			EX++;
			EV++;
			/*System.out.println(n);
			System.out.println(t[n]);*/
			for(int j=0;j<=n;j++) {
				//System.out.println(t[j]);
			}
			stepOne();
			
			System.out.println("EX="+EX+",EV="+EV+",N="+N+",sita="+sita);
		}
	}
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String pathName="C:\\Users\\Administrator\\Documents\\Tencent Files\\853189424\\FileRecv\\作业一（JM模型）\\softwareReliability\\src\\softwareReliability\\Test.txt";
		File filename=new File(pathName);
		intFileTOData(10,filename);
		
		Test();
	}

}
