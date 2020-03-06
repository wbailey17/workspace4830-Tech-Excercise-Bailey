

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddOwned
 */
@WebServlet("/AddOwned")
public class AddOwned extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddOwned() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String genre = request.getParameter("genre");

        Connection connection = null;
        String insertSql = " INSERT INTO ownedBooks (id, TITLE, AUTHOR, GENRE) values (default, ?, ?, ?)";

        try {
           DBConnection.getDBConnection();
           connection = DBConnection.connection;
           PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
           preparedStmt.setString(1, title);
           preparedStmt.setString(2, author);
           preparedStmt.setString(3, genre);
           preparedStmt.execute();
           connection.close();
        } catch (Exception e) {
           e.printStackTrace();
        }

        // Set response content type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String header = "Book Added to List";
        String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
        out.println(docType + //
              "<html>\n" + //
              "<head>\n"
              + "<style>\n"
              + "header {\n" + 
              "    background-color:black;\n" + 
              "    color:white;\n" + 
              "    text-align:center;\n" + 
              "    padding:5px;\n" + 
              "}"
              + "</style>\n"
              + "<header>\n" + 
              "	<h1>" + header + "</h1>\n" + 
              "</header>\n"
              + "<title>" + header + "</title></head>\n" + //
              "<body>\n" + //
              "<ul>\n" + //

              "  <li><b>Title</b>: " + title + "\n" + //
              "  <li><b>Author</b>: " + author + "\n" + //
              "  <li><b>Genre</b>: " + genre + "\n" + //

              "</ul>\n");

        out.println("<br><a href=\\Tech-Exercise-Bailey\\addOwnedBook.html>Add Another Book</a> <br>");
        out.println("<a href=\\Tech-Exercise-Bailey\\selectList.html>Select List</a> <br>");
        out.println("</body></html>");
     }

     protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
     }

  }
