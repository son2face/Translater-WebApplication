
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;
import java.sql.*;
// Extend HttpServlet class

public class Db {

    String database;
    String request;
    Connection connection;
    Statement statement;

    Db() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost/translater", "root", "root");
        statement = connection.createStatement();
        this.database = "engvie";
    }

    Db(String database) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost/translater", "root", "root");
        statement = connection.createStatement();
        this.database = database;
    }

    public void connectDataBase() {

    }

    public String doRequest(String request) {
        String result = null;

        return result;
    }
}
