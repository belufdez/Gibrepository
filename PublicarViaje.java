import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class PublicarViaje extends HttpServlet {
  Connection connection;

  public void init(ServletConfig config) throws ServletException {
    super.init(config);
	        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String url="jdbc:odbc:Tecar";
            connection=DriverManager.getConnection(url); 
        } catch(Exception e) {
            e.printStackTrace();
        }
 
	} 
       
  public void doGet (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
   
	HttpSession session = req.getSession(true);
	String sql = "INSERT INTO Trips (Origin, Destination, Date) VALUES (";	           
        sql +=  "'" + req.getParameter("origin") + "'";
        sql +=  ", '" + req.getParameter("destination") + "'";
		sql +=  ", '" + req.getParameter("date") + "')";
        System.out.println("Insert sql: " + sql);
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Resulset: " + sql + " Exception: " + e);
        }
	
                 
   
    res.setContentType("text/html");
    PrintWriter out = res.getWriter();
   
 
        
    // Se genera el contenido de la página HTML
    out.println("<HTML>");
    out.println("<HEAD>");
    out.println("<TITLE>Valores recogidos en el formulario</TITLE>");
    out.println("</HEAD>");
    out.println("<BODY>");
    out.println("<B><FONT size=+2>Valores recogidos del formulario: </FONT></B>");
	out.println("<BR>");
	out.println("<P><FONT size=+1><B>Origin: </B>" + req.getParameter("origin") + "</FONT>");
    out.println("<BR><FONT size=+1><B>Destination: </B>" +req.getParameter("destination") + "</FONT>");
    out.println("<P><FONT size=+1> <B>Date: </B><I>" + req.getParameter("date") + "</I></FONT>");

	out.println("</BR>");

    out.println("</BODY>");
    out.println("<BR><a href=\"menu.html\">Menu</a>");

    out.println("</HTML>");
       
	
    out.flush();
    out.close();
  }
}