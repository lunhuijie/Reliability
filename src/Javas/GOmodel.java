package Javas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GOmodel {
	
	public static double getD(ArrayList<String> list) {
		double result = 0;
		int n = list.size();
		
		for (String ti : list) {
			result += Double.parseDouble(ti);
		}
		
		result /= n * Double.parseDouble(list.get(n - 1));
		return result;
	}
	
	static double function(ArrayList<String> list, double xm) {
		double result = 0;
		double D = getD(list);
		
		result = (1 - D * xm) * Math.exp(xm) + (D - 1) * xm - 1;

		return result;
	}
	
	static double evaluate(ArrayList<String> list, double ey) {
		double D = getD(list);
		double xl, xr, xm;
		int n = list.size();
		
		if ((D > 0) && (D < 0.5)) {
			xl = (1 - 2 * D) / 2;
			xr = 1 / D;
		}
		else {
			return -1;
		}
		
		while (true)
		{
			xm = (xr + xl) / 2;
			if (Math.abs(xr - xl) <= ey)
				break;
			if (function(list, xm) > ey)
				xl = xm;
			else if (function(list, xm) < ey)
				xr = xm;
		}
		
		return xm / Double.parseDouble(list.get(n - 1));
	}
	public String Test(String[] data,double ey){
		ArrayList<String> list= new ArrayList<String>();
		for(int i=0;i<data.length;i++)
		{
			list.add(data[i]);
		}
		double b = evaluate(list, ey);
		int n = list.size();
		double a = n / (1 - Math.exp((-b) * Double.parseDouble(list.get(n - 1))));
		String str="{a:"+a+",b:"+b+"}";
		return str;
	}

	/*public static void main(String arg[]) throws IOException {
		File file = new File("Data/SYS1(failue_count).txt");
		FileReader fr = new FileReader(file);
		BufferedReader reader = new BufferedReader(fr);
		String line = new String();
		ArrayList<String> list = new ArrayList<String>();
        while ((line = reader.readLine()) != null) {
        	String s[] = line.split("\\s+");
        	list.add(s[1]);
        }
        double ey = 100;
		double b = evaluate(list, ey);
		if (b == -1) {
			System.out.println("Error!");
		}
		int n = list.size();
		
		double a = n / (1 - Math.exp((-b) * Double.parseDouble(list.get(n - 1))));

        System.out.println("\na = " + a);
        System.out.println("b = " + b);
        reader.close();
        fr.close();
	}*/
}
