

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RemoveOwned
 */
@WebServlet("/RemoveOwned")
public class RemoveOwned extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveOwned() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String keyword = request.getParameter("title");
        remove(keyword, response);
	}
	
    void remove(String keyword, HttpServletResponse response) throws IOException {
    	response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String title = "Remove Owned Book";
        String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
              "transitional//en\">\n"; //
        out.println(docType + //
        		"<html>\n" + //
        		"<head>\n"
        		+ "<style>\n"
        		+ "table, th, td {\n"
        		+ "border: 1px solid black;\n"
        		+ "border-collapse: collapse;\n}"
        		+ "header {\n" + 
        		"    background-color:black;\n" + 
        		"    color:white;\n" + 
        		"    text-align:center;\n" + 
        		"    padding:5px;\n" + 
        		"}"
        		+ "</style>\n"
        		+ "<header>\n" + 
        		"	<h1>" + title + "</h1>\n" + 
        		"</header>"
        		+ "<title>" + title + "</title></head><br>\n" + //
        		"<body>\n" + //
        		"<h2>Books Removed</h2>\n" +
        		"<table style=\"width:100%\">\n" + 
              			"<tr style=\"background-color: #f0f0f0\">\n" + 
              			"<th>Title</th>\n" + 
              			"<th>Author</th>\n" + 
              			"<th>Genre</th>\n" + 
              			"</tr>");

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        if (!keyword.isEmpty()) {
        	try {
        		DBConnection.getDBConnection();
        		connection = DBConnection.connection;
        		
        		String selectSQL = "SELECT * FROM ownedBooks WHERE TITLE = ?";
	            String theTitle = keyword;
	            preparedStatement = connection.prepareStatement(selectSQL);
	            preparedStatement.setString(1, theTitle);

	            ResultSet rs = preparedStatement.executeQuery();

	
	            while (rs.next()) {
	            	rs.getInt("id");
	            	String bookTitle = rs.getString("title").trim();
	            	String author = rs.getString("author").trim();
	            	String genre = rs.getString("genre").trim();
	
	            	if (keyword.isEmpty() || bookTitle.contains(keyword)) {
	            		out.println("<tr>\r\n" + 
	            				"    <td>"+ bookTitle + "</td>\n" + 
	            				"    <td>"+ author + "</td>\n" + 
	            				"    <td>" + genre + "</td>\n" + 
	            				"  </tr>");
	            		}
	            	}

	              	rs.close();
	              	preparedStatement.close();
	              	
		            selectSQL = "DELETE from ownedBooks where TITLE = ?";
		            preparedStatement = connection.prepareStatement(selectSQL);
		            preparedStatement.setString(1, theTitle);
		            preparedStatement.execute();

	              	connection.close();
        	} catch (SQLException se) {
        		se.printStackTrace();
        	} catch (Exception e) {
        		e.printStackTrace();
        	} finally {
        		try {
        			if (preparedStatement != null)
        				preparedStatement.close();
        		} catch (SQLException se2) {
        		}
        		try {
        			if (connection != null)
        				connection.close();
        		} catch (SQLException se) {
        			se.printStackTrace();
        		}
        	}
        }
      	out.println("</table>");
      	out.println("<a href=\\Tech-Exercise-Bailey\\selectList.html>Select List</a> <br>");
      	out.println("<a href=\\Tech-Exercise-Bailey\\removeOwned.html>Remove Another Book</a> <br>");
      	out.println("</body></html>");
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
