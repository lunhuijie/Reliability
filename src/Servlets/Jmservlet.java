package Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import Javas.JM;

/**
 * Servlet implementation class Jmservlet
 */
@WebServlet("/Jmservlet")
public class Jmservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Jmservlet() {
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
        String ev = request.getParameter("ev");
        String ex = request.getParameter("ex");
        String n = request.getParameter("n");
        System.out.println(request);
        //data.split("")
        data = data.replace("[\"", "");
        data = data.replace("\"]", "");
        String [] datas = data.split("\",\"");
        
        int[] a=new int[datas.length];
        for(int i = 0;i<datas.length;i++) {
        	a[i]=Integer.parseInt(datas[i]);
        }

        double evt = Double.parseDouble(ev);
        double ext = Double.parseDouble(ex);
        int nt = a.length;
        
        
        JM k=new JM();
       String s=k.Test(a,evt,ext,nt);
        response.getWriter().println(s);
        
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
