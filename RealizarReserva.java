import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class RealizarReserva extends HttpServlet {
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

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();
        toClient.println("<!DOCTYPE HTML>");
        toClient.println("<html>");
        toClient.println("<head><title>Tecar</title></head>");
        toClient.println("<body>");
        toClient.println("<a href=\"menu.html\">Home</A>");
        toClient.println("<h2>Trips</h2>");
        
        HttpSession session = req.getSession(false);
        if (session != null) {
            String name = (String)session.getAttribute("UserName");
            if (name != null) {
                toClient.println("<h2>UserName: " + name + "</h2>");
            }
        }

       toClient.print("<form action=\"MostrarViajeCheck\" method=GET>");
        toClient.println("<table border='1'>");
        
        String sql = "Select ID_Trip, UserName, Origin, Destination, Price, Date  FROM Trips";
        System.out.println(sql);
         try {
            Statement statement=connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while(result.next()) {
                toClient.println("<tr>");
                String ID_TripStr = result.getString("ID_Trip");
                toClient.println("<td><input type=\"radio\" name=\"ID_Trip" +
                    "\" value=\"" + ID_TripStr + "\"></td>");
                toClient.println("<td>" + ID_TripStr + "</td>");
                toClient.println("<td>" + result.getString("Origin") + "</td>");
				toClient.println("<td>" + result.getString("Destination") + "</td>");
				toClient.println("<td>" + result.getString("Price") + "</td>");
				toClient.println("<td>" + result.getString("Date") + "</td>");
				toClient.println("</tr>");
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Resulset: " + sql + " Exception: " + e);
        }
        toClient.println("</table>");
       /* toClient.println("<textarea rows=\"8\" cols=\"60\" name=\"comment\"></textarea><BR>")*/;
        toClient.println("<input type=submit>");
        toClient.println("</form>");
        toClient.println("</body>");
        toClient.println("</html>");
        toClient.close();
    }
}