<%
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import javax.imageio.ImageIO;

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
// Extend HttpServlet class

public class HelloWorld3 extends HttpServlet {

    String request;
    java.io.PrintWriter out;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        this.out = response.getWriter();
        this.request = request.getParameter("cmd");
        if (this.request == "search") {
            search(request);
        }
    }

    private void search(HttpServletRequest request) {
        String search = request.getParameter("search");
        try {
            Db test = new Db();
            ResultSet result = test.search("eng", "vie", search);
            while (result.next()) {
                this.out.println(" + " + result.getString(1));
            }
        } catch (SQLException se) {
            this.out.println(se.toString());
        } catch (ClassNotFoundException se) {
            this.out.println(se.toString());
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {
        String search = request.getParameter("search");
        response.setContentType("text/html");
        java.io.PrintWriter out = response.getWriter();
        String t = "dэЮ liктu рaЮ coм";
        byte[] strBytes = t.getBytes("utf8");
        t = new String(strBytes, "utf8");
        out.println(t);
    }
}
%>