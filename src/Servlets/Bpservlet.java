package Servlets;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Javas.bp1;

import org.joone.engine.FullSynapse;
import org.joone.engine.Monitor;
import org.joone.engine.NeuralNetEvent;
import org.joone.engine.NeuralNetListener;
import org.joone.engine.SigmoidLayer;
import org.joone.engine.learning.TeachingSynapse;
import org.joone.io.FileInputSynapse;
import org.joone.io.FileOutputSynapse;

/**
 * Servlet implementation class UGrouphServlet
 */
@WebServlet("/Bpservlet")
public class Bpservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Bpservlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String data = request.getParameter("data");

        //data.split("")
        data = data.replace("[\"", "");
        data = data.replace("\"]", "");
        String [] datas = data.split("\",\"");
        
        

        File file = new File("F:\\SYS.txt");
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        
        //2：准备输出流
        Writer out = new FileWriter(file);
        int[] a=new int[datas.length];
        for(int i = 0;i<datas.length;i++) {
        	a[i]=Integer.parseInt(datas[i]);
        	String str = String.format("%.3f",(double)(i+1)/1000)+";"+String.format("%.4f", (double)a[i]/10000) + "\r\n";
        	out.write(str);
        	
        }
        
        
        out.close();
        
		//bp1 bp = new bp1();
		bp1.Test();
        response.getWriter().println();
        
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
