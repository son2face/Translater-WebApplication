
import java.io.*;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.*;

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

public class Reader extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {
        // Check that we have a file upload request
        DB daba = null;

        String filePath = "C:\\apache-tomcat-8.5.5\\webapps\\ROOT\\";
        boolean isMultipart;
        File file;
        isMultipart = ServletFileUpload.isMultipartContent(request);
        response.setContentType(
                "text/html");
        java.io.PrintWriter out = response.getWriter();
        try {
            daba = new DB();
        } catch (ClassNotFoundException ex) {
            out.print("<p>FAILED</p>");
        } catch (SQLException ex) {
            out.print("<p>FAILED</p>");
        }
        if (!isMultipart) {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet upload</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<p>No file uploaded</p>");
            out.println("</body>");
            out.println("</html>");
            return;
        }
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // maximum size that will be stored in memory
        // Location to save data that is larger than maxMemSize.
        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
        // maximum file size to be uploaded.
        try {
            // Parse the request to get file items.
            List fileItems = upload.parseRequest(request);
            // Process the uploaded file items
            Iterator i = fileItems.iterator();
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet upload</title>");
            out.println("</head>");
            out.println("<body>");
            while (i.hasNext()) {
                FileItem fi = (FileItem) i.next();
                if (!fi.isFormField()) {
                    // Get the uploaded file parameters
                    String fieldName = fi.getFieldName();
                    String fileName = fi.getName();
                    String contentType = fi.getContentType();
                    boolean isInMemory = fi.isInMemory();
                    long sizeInBytes = fi.getSize();
                    // Write the file
                    if (fileName.lastIndexOf("\\") >= 0) {
                        filePath = filePath + fileName.substring(fileName.lastIndexOf("\\"));
                        file = new File(filePath);
                    } else {
                        filePath = filePath + fileName.substring(fileName.lastIndexOf("\\") + 1);
                        file = new File(filePath);
                    }
                    fi.write(file);
                    out.println("Uploaded Filename: " + fileName + "<br>");
                }
            }
            out.print("<p> PROCESSING...</p>");
            Scanner xx = new Scanner(Paths.get(filePath), "utf-8");
            while (xx.hasNext()) {
                String data = xx.nextLine();

                int length = data.length();
                int j = 0;
                String mean = "";
                String meaning = "";
                while ((data.charAt(j) != '#') && (j < length)) {
                    if (data.charAt(j) == '\'') {
                        mean += '\\';
                    }
                    mean += data.charAt(j);
                    j++;
                }
                j++;
                while ((data.charAt(j) != '#') && (j < length)) {
                    j++;
                }
                j++;
                while ((data.charAt(j) != '#') && (j < length)) {
                    j++;
                }
                j++;
                if (j < length) {
                    for (; j < length; j++) {
                        if (data.charAt(j) == '\'') {
                            meaning += '\\';
                        }
                        meaning += data.charAt(j);
                    }
                }

                if ((meaning.length() < 700) && (mean.length() < 200)) {
                    daba.insertElement("eng", "vie", mean, meaning);
                }
                out.println("<p>FINISHED</p>");
                out.println("</body>");
                out.println("</html>");
            }
        } catch (Exception ex) {
            out.print("<p>FAILED</p>");
            System.out.println(ex);
        }
    }
}
