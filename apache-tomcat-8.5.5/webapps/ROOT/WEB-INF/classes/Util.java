import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.output.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;
import java.sql.*;
// Extend HttpServlet class

public class Util {

    String request;
    java.io.PrintWriter out;

    Util(java.io.PrintWriter out) throws ClassNotFoundException, SQLException {
        this.out = out;
    }

    public void search(HttpServletRequest request) {
        String search = request.getParameter("search");
        try {
            Db test = new Db();
            ResultSet result = test.search("eng", "vie", search);
            if (result.next()) {
                this.out.print("[{\"label\": \"" + result.getString(1) + "\"}");
                while (result.next()) {
                    this.out.print(",{\"label\": \"" + result.getString(1) + "\"}");
                }
                this.out.print("]");
            }
        } catch (SQLException se) {
            this.out.println(se.toString());
        } catch (ClassNotFoundException se) {
            this.out.println(se.toString());
        }
    }
    
    public void search(HttpServletRequest request) {
        String search = request.getParameter("search");
        try {
            Db test = new Db();
            ResultSet result = test.search("eng", "vie", search);
            if (result.next()) {
                this.out.print("[{\"label\": \"" + result.getString(1) + "\"}");
                while (result.next()) {
                    this.out.print(",{\"label\": \"" + result.getString(1) + "\"}");
                }
                this.out.print("]");
            }
        } catch (SQLException se) {
            this.out.println(se.toString());
        } catch (ClassNotFoundException se) {
            this.out.println(se.toString());
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

    }

}
