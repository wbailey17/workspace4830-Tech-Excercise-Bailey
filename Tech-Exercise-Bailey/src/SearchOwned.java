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
 * Servlet implementation class SearchOwned
 */
@WebServlet("/SearchOwned")
public class SearchOwned extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchOwned() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("title");
        search(keyword, response);
     }

     void search(String keyword, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String title = "Database Result";
        String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
              "transitional//en\">\n"; //
        out.println(docType + //
              "<html>\n" + //
              "<head><title>" + title + "</title></head>\n" + //
              "<body bgcolor=\"#f0f0f0\">\n" + //
              "<h1 align=\"center\">" + title + "</h1>\n");

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
           DBConnection.getDBConnection();
           connection = DBConnection.connection;

           if (keyword.isEmpty()) {
              String selectSQL = "SELECT * FROM ownedBooks";
              preparedStatement = connection.prepareStatement(selectSQL);
           } else {
              String selectSQL = "SELECT * FROM ownedBooks WHERE MYUSER LIKE ?";
              String theUserName = keyword + "%";
              preparedStatement = connection.prepareStatement(selectSQL);
              preparedStatement.setString(1, theUserName);
           }
           ResultSet rs = preparedStatement.executeQuery();

           while (rs.next()) {
              rs.getInt("id");
              String bookTitle = rs.getString("title").trim();
              String author = rs.getString("author").trim();
              String genre = rs.getString("genre").trim();

              if (keyword.isEmpty() || bookTitle.contains(keyword)) {
                 out.println("Title: " + bookTitle + ", ");
                 out.println("Author: " + author + ", ");
                 out.println("Phone: " + genre + "<br>");
              }
           }
           out.println("<a href=\\Tech-Exercise-Bailey\\selectList.html>Select List</a> <br>");
           out.println("</body></html>");
           rs.close();
           preparedStatement.close();
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

     protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
     }

  }
