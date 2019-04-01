package Javas;


import org.apache.commons.math3.distribution.NormalDistribution;
//import org.apache.velocity.exception.MathException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static jdk.nashorn.internal.objects.Global.Infinity;


public class uy {

   public static List<Integer>  t = new ArrayList<Integer>();//储存从文件读取的数据
    public static List<Double>  u = new ArrayList<Double>();
    public static List<Double>  x = new ArrayList<Double>();
    public static List<Double>  y = new ArrayList<Double>();

   public static String Test(int []data) throws IOException {
        for(int i = 0;i<data.length;i++)
        {
        	t.add(data[i]);
        }
        Cal_Ui();
        Arrays.sort(new List[]{u});

        Cal_Yi();
        String str = "";
        return str;
        
        
   }

    /*public static void GetData() throws IOException {
        File file = new File("C:\\Users\\lenovo\\Desktop\\可靠性\\161630120段浩波(JM)\\SYS1(failue_count).txt");
        Reader reader = new BufferedReader(new FileReader(file));
        String tempString = null;
        int i = 0;
        while((tempString = ((BufferedReader) reader).readLine())!=null){
            t.add(i, Integer.parseInt(tempString.split("\\s+")[1]));
            i ++ ;
        }
        reader.close();
    }*/


    public static void Cal_Ui(){//计算ui序列
        NormalDistribution nor;
        nor = new NormalDistribution(0,1);
        for (int i = 0;i < t.size(); i++){
            double res = 0;
            
                res = nor.cumulativeProbability(t.size()-i+1);
          
            u.add(i,1-Math.exp(-res*t.get(i)));
        }
    }

    public static void Cal_Yi(){ //计算Yi序列
       double temp;
        for (int i=0;i<u.size();i++){
            temp = -Math.log(1-u.get(i));
            if (temp==Infinity) temp = 0;
            x.add(i,temp); //xi=ln(1-ui)

        }

        double sumx=0,sumt=0;
        for (int i = 0;i<u.size();i++){
            sumt = 0;
            sumx = 0;
            for (int j=0;j<=i;j++){
                sumt+= t.get(j);
                sumx+= x.get(j);
            }
            y.add(i,sumx/sumt); //计算yi
        }
    }


    public static double Cal_PL(){
        double pl = 1;
        NormalDistribution nor = new NormalDistribution(0,1);
        for (int i = 0;i < t.size(); i++){
            double res = 0;
           
                res = nor.cumulativeProbability(t.size()-i+1);
           
            pl*=(res*Math.exp(-res*t.get(i)));
        }
        return pl;
    }


}