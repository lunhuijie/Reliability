package Javas;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List; 
import net.sf.json.JSON; 
import net.sf.json.JSONArray; 
import net.sf.json.JSONObject; 
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter; 
public class XY  {
    public double x;
    public double y;
    public double getx (double t,double Q,double N,int n){//n是一个定值，N是一个定值，Q是一个定值，t是传入的t
        return (1 - Math.exp(-Q* (N - n + 1) * t));
    }
    public String Test(int []array) {
    	int length = array.length;
    	int nn = array.length;
    	double[] ux = new double[nn];
    	double[] uy = new double[nn];
    	double[] yy = new double[nn];
    	double[] yx = new double[nn];
    	double[] c=new double[nn];
        for(int i=0;i<nn;i++) {
            int t=i;
            c[i]=getx(array[i],0.0000314274,137,i+1);
        }
        
        double eu=(double)1/(nn+1);
        for(int i=0;i<length;i++)
            for(int j=0;j<length-i-1;j++)
            {
                if(c[j]>c[j+1])
                {
                    double d=c[j];
                    c[j]=c[j+1];
                    c[j+1]=d;
                }
            }
        double[] r =new double[length];
        for(int i=0;i<length;i++)
        {
            ux[i] = c[i];
            uy[i] = i*eu;
            if((i+1)!=length)
            {
            	ux[i] = c[i+1];
                uy[i] = i*eu;
            }
            if((i*eu-c[i])<0)
            {
                r[i]=c[i]-i*eu;
            }
            else r[i]=i*eu-c[i];
        }

            for(int j=0;j<length-1;j++) {
                if (r[j] > r[j + 1]) {
                    double d = r[j];
                    r[j] = r[j + 1];
                    r[j + 1] = d;
                }
            }
            double ks=r[length-1];
            
           //Y tu
            double[] Y=new double[length];
            double add=0;
            for(int i=0;i<length;i++)
            {
                Y[i]=-Math.log(1-c[i]) / Math.log(Math.exp(1));
                add=add+Y[i];
            }
            double ad=0;
            double[] Y1=new double[length];
            for(int i=0;i<length;i++)
            {
                ad=ad+Y[i];
                Y1[i]=ad/add;
            }
            double ey=(double)1/(nn);
            for(int i=0;i<length;i++)
            {
                if(Y1[i]<1&&Y1[i]>0)
                {
                	yx[i] = Y1[i];
                	yy[i] = i*ey;
                }
                if((i+1)!=length)
                {
                	yx[i] = Y1[i+1];
                	yy[i] = i*ey;
                }

                if((i*eu-c[i])<0)
                {
                    r[i]=Y1[i]-i*ey;
                }
                else r[i]=i*ey-Y1[i];
            }
            for(int j=0;j<length-1;j++) {
                if (r[j] > r[j + 1]) {
                    double d = r[j];
                    r[j] = r[j + 1];
                    r[j + 1] = d;
                }
            }
            double ksy=r[length-1];
            
            String str = "";
            JSONArray uux = JSONArray.fromObject(ux);
            JSONArray uuy = JSONArray.fromObject(uy);
            JSONArray uyx = JSONArray.fromObject(yx);
            JSONArray uyy = JSONArray.fromObject(yy);
            JSONObject obj = new JSONObject();
            obj.put("ux", uux);
            obj.put("uy", uuy);
            obj.put("yx", uyx);
            obj.put("yy", uyy);
            obj.put("ksu",ks);
            obj.put("ksy",ksy);
            str = obj.toString();
            return str;
    }
}
