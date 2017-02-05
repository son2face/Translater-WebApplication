
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;
import java.sql.*;

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

public class HelloWorld2 extends HttpServlet {

    private boolean isMultipart;
    private String filePath;
    private int maxFileSize = 1000000000;
    private int maxMemSize = 1000000000;
    private File file;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {
        // Check that we have a file upload request
        BufferedReader reader = request.getReader();
        String line = reader.readLine();
        response.setContentType("text/html");
        File f = new File("rev.txt");
        try (FileWriter fw = new FileWriter(f)) {
            fw.write(line);
        }
        java.io.PrintWriter out = response.getWriter();
        out.println(line);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {
        response.setContentType("text/html");
        java.io.PrintWriter out = response.getWriter();
        out.println("[  \n"
                + "   {  \n"
                + "      \"id\":1417207904666,\n"
                + "      \"title\":\"Sõ võ\",\n"
                + "      \"content\":\"I want to visit the seven wonders of the ancient world!\\n\\n* The Colossus of Rhodes\\n* The Great Pyramid of Giza\\n* The Hanging Gardens of Babylon\\n* The Lighthouse of Alexandria\\n* The Mausoleum at Halicarnassus\\n* The Statue of Zeus at Olympia\\n* The Temple of Artemis at Ephesus\",\n"
                + "      \"date\":\"2014-11-23T21:10:51.974Z\"\n"
                + "   },\n"
                + "   {  \n"
                + "      \"id\":1417204504666,\n"
                + "      \"title\":\"Grocery List\",\n"
                + "      \"content\":\"### Food items\\n\\n* Milk\\n* Eggs\\n* Cookies\\n* Salad\\n* Flour\\n\\n### Home items\\n\\n* Paper towels\\n* Aspirin\\n* Dish soap\",\n"
                + "      \"date\":\"2014-11-23T21:40:04.089Z\"\n"
                + "   },\n"
                + "   {  \n"
                + "      \"id\":1417107904666,\n"
                + "      \"title\":\"Cookie Recipe\",\n"
                + "      \"content\":\"Makes 90-100 cookies.\\n\\n#### Ingredients\\n* 2 1/2 cups butter, softened\\n* 2 cups sugar\\n* 2 eggs\\n* 1/4 cup milk\\n* 2 teaspoons Vanilla Extract\\n* 8 cups all-purpose flour\\n* 4 teaspoons baking powder\\n* 1 teaspoon salt\\n\\n#### Directions\\n1. In a large bowl, cream the butter and sugar. \\n2. Add eggs, milk and vanilla to the bowl and mix well. \\n3. Combine the flour, baking powder and salt gradually into creamed mixture.\\n2. Drop cookies onto greased baking sheet, with 2 inches between.\\n3. Bake at 375°F for 10-12 minutes or until bottoms are lightly browned.\",\n"
                + "      \"date\":\"2014-11-23T21:46:01.400Z\"\n"
                + "   },\n"
                + "   {  \n"
                + "      \"id\":1417007904666,\n"
                + "      \"title\":\"Holiday List\",\n"
                + "      \"content\":\"Some gift ideas for the family.\\n\\n* John: Mountain bike\\n* Julie: Lego set\\n* Lindsey: Tricycle \\n* Mom: Earrings\\n* Dad: Beer of the month\",\n"
                + "      \"date\":\"2014-11-23T21:58:27.327Z\"\n"
                + "   },\n"
                + "   {  \n"
                + "      \"id\":1416907904666,\n"
                + "      \"title\":\"Meeting Notes\",\n"
                + "      \"content\":\"Production reports being 2 weeks behind schedule. James will make a plan to get back on track.\\n\\nEngineering tested 5 prototypes for the next version, and 2 are currently undergoing the second round of tests. Expect final results next week.\\n\\nSales has secured buyers for the latest production run, and is putting pressure on production to keep up with demand.\",\n"
                + "      \"date\":\"2014-11-23T22:01:12.988Z\"\n"
                + "   }\n"
                + "]");
    }
}
