
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
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

public class ServerEngine extends HttpServlet {

    String request;                                 // Yêu c?u t? client
    java.io.PrintWriter out;                        // Lýu l?i lu?ng tr? v? d? li?u cho client

    /**
     * Th?c hi?n khi client yêu c?u m?t PostMethod
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws java.io.IOException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        this.out = response.getWriter();
        this.request = request.getParameter("cmd");
        if ("search".equals(this.request)) {
            search(request);
        }
        if ("autocomplete".equals(this.request)) {
            autocomplete(request);
        }
        if ("insert".equals(this.request)) {
            insert(request);
        }
        if ("edit".equals(this.request)) {
            edit(request);
        }
        if ("sound".equals(this.request)) {
            searchSound(request);
        }
    }

    /**
     * Hàm x? lí yêu c?u thêm 1 t? c?a client
     *
     * @param request: thông s? t? phía client
     */
    private void insert(HttpServletRequest request) {
        String search = request.getParameter("search");
        String meaning = request.getParameter("meaning");
        if ("".equals(meaning)) {
        } else {
            String mean = request.getParameter("mean");
            try {
                DB test = new DB();
                if ("vie".equals(mean)) {
                    test.insertElement("vie", "eng", search, meaning);

                } else {
                    test.insertElement("eng", "vie", search, meaning);
                }
                this.out.print("[{\"label\": \"success\"}");
            } catch (SQLException se) {
                this.out.println(se.toString());
            } catch (ClassNotFoundException se) {
                this.out.println(se.toString());
            }
        }
    }

    /**
     * t?m Ðý?ng d?n Sound âm thanh
     *
     * @param request : yêu c?u
     * @throws MalformedURLException
     * @throws java.io.IOException
     * @throws UnsupportedEncodingException
     */
    private void searchSound(HttpServletRequest request) throws MalformedURLException, java.io.IOException, UnsupportedEncodingException {
        String search = request.getParameter("search");
        String mean = request.getParameter("mean");
        String idWeb = request.getParameter("idWeb");
        if ("eng".equals(mean)) {
            if ("1".equals(idWeb)) {
                searchSoundWeb1("A-V", search);
            } else if ("2".equals(idWeb)) {
                searchSoundWeb2("en_vn", search);
            } else if ("3".equals(idWeb)) {
                searchSoundWeb3("en_vi", search);
            }
        } else if ("1".equals(idWeb)) {
            searchSoundWeb1("V-A", search);
        } else if ("2".equals(idWeb)) {
            searchSoundWeb2("vn_en", search);
        } else if ("3".equals(idWeb)) {
            searchSoundWeb3("vi_en", search);
        }
    }

    /**
     * Hàm ch?nh s?a, n?u meaning = null th? týõng ðýõng v?i xóa
     *
     * @param request: thông s? yêu c?u
     */
    private void edit(HttpServletRequest request) {
        String search = request.getParameter("search");
        String meaning = request.getParameter("meaning");
        String oldMean = request.getParameter("oldMean");
        String mean = request.getParameter("mean");
        try {
            DB test = new DB();
            if ("vie".equals(mean)) {
                test.deleteElement("vie", "eng", search, oldMean);
                if ("".equals(meaning)) {

                } else {
                    test.insertElement("vie", "eng", search, meaning);
                }
            } else {
                test.deleteElement("eng", "vie", search, oldMean);
                if ("".equals(meaning)) {

                } else {
                    test.insertElement("eng", "vie", search, meaning);
                }
            }
            this.out.print("[{\"label\": \"success\"}");
        } catch (SQLException se) {
            this.out.println(se.toString());
        } catch (ClassNotFoundException se) {
            this.out.println(se.toString());
        }
    }

    /**
     * Hàm tr? v? các k?t qu? t? ð?ng
     *
     * @param request: thông s? yêu c?u
     */
    private void autocomplete(HttpServletRequest request) {
        String search = request.getParameter("search");
        String mean = request.getParameter("mean");
        try {
            DB test = new DB();
            ResultSet result = test.autocomplete(mean, search);
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

    /**
     * Hàm t?m ki?m t?
     *
     * @param request
     * @throws MalformedURLException
     * @throws java.io.IOException
     * @throws UnsupportedEncodingException
     */
    private void search(HttpServletRequest request) throws MalformedURLException, java.io.IOException, UnsupportedEncodingException {
        String search = request.getParameter("search");
        String mean = request.getParameter("mean");
        String idWeb = request.getParameter("idWeb");
        try {
            DB test = new DB();
            if ("vie".equals(mean)) {
                ResultSet result = test.search1("vie", search);
                if (result.next()) {
                    this.out.print("[{\"label\": \"" + result.getString(2) + "\",");
                    this.out.print("\"mean\": \"" + result.getString(1) + "\"}");
                    while (result.next()) {
                        this.out.print(",{\"label\": \"" + result.getString(2) + "\",");
                        this.out.print("\"mean\": \"" + result.getString(1) + "\"}");
                    }
                    this.out.print("]");
                } else if ("1".equals(idWeb)) {
                    searchFromWeb("V-A", search);
                } else if ("2".equals(idWeb)) {
                    searchFromWeb1("vn_en", search);
                } else if ("3".equals(idWeb)) {
                    searchFromWeb2("vi_en", search);
                }
            } else {
                ResultSet result = test.search1("eng", search);
                if (result.next()) {
                    this.out.print("[{\"label\": \"" + result.getString(1) + "\",");
                    this.out.print("\"mean\": \"" + result.getString(2) + "\"}");
                    while (result.next()) {
                        this.out.print(",{\"label\": \"" + result.getString(1) + "\",");
                        this.out.print("\"mean\": \"" + result.getString(2) + "\"}");
                    }
                    this.out.print("]");
                } else if ("1".equals(idWeb)) {
                    searchFromWeb("A-V", search);
                } else if ("2".equals(idWeb)) {
                    searchFromWeb1("en_vn", search);
                } else if ("3".equals(idWeb)) {
                    searchFromWeb2("en_vi", search);
                }
            }
        } catch (SQLException se) {
            this.out.println(se.toString());
        } catch (ClassNotFoundException se) {
            this.out.println(se.toString());
        }
    }

    /**
     * T?m ki?m t? tên mi?n: tratu.coviet.vn
     *
     * @param mean
     * @param request
     * @throws MalformedURLException
     * @throws java.io.IOException
     * @throws UnsupportedEncodingException
     */
    private void searchFromWeb(String mean, String request) throws MalformedURLException, java.io.IOException, UnsupportedEncodingException {
        request = request.replace(" ", "%20");
        URL oracle = new URL("http://tratu.coviet.vn/hoc-tieng-anh/tu-dien/lac-viet/" + mean + "/" + request + ".html");
        URLConnection yc = oracle.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
        String inputLine;
        String key = "<div class=\"m\"><span>";
        int nKey = 15;
        String[] result = new String[100];
        int kq = 0;
        while ((inputLine = in.readLine()) != null) {
            for (int i = 0; i < inputLine.length(); i++) {
                if (inputLine.charAt(i) == key.charAt(0)) {
                    int check = 1;
                    for (int j = 1; j < nKey; j++) {
                        if (inputLine.charAt(i + j) != key.charAt(j)) {
                            check = 0;
                            break;
                        }
                    }
                    if (check == 1) {
                        i += 21;
                        result[kq] = "";
                        while (inputLine.charAt(i) != '<') {
                            result[kq] += inputLine.charAt(i);
                            i++;
                        }
                        if ("(".equals(result[kq])) {
                            result[kq] = "";
                            continue;
                        }
                        kq++;
                    }
                }
            }
        }
        if (kq != 0) {
            this.out.print("[{\"label\": \"" + request + "\",");
            this.out.print("\"mean\": \"" + result[0] + "\"}");
            for (int i = 1; i < kq; i++) {
                this.out.print(",{\"label\": \"" + request + "\",");
                this.out.print("\"mean\": \"" + result[i] + "\"}");
            }
            this.out.print("]");
        } else {
            this.out.print("[{\"label\": \"" + request + "\",");
            this.out.print("\"mean\": \"not found\"}]");
        }
        in.close();
    }

    /**
     * Tim kiem am thanh tu ten mien: tratu.coviet.vn
     *
     * @param mean
     * @param request
     * @throws MalformedURLException
     * @throws java.io.IOException
     * @throws UnsupportedEncodingException
     */
    private void searchSoundWeb1(String mean, String request) throws MalformedURLException, java.io.IOException, UnsupportedEncodingException {
        request = request.replace(" ", "%20");
        URL oracle = new URL("http://tratu.coviet.vn/hoc-tieng-anh/tu-dien/lac-viet/" + mean + "/" + request + ".html");
        URLConnection yc = oracle.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
        String inputLine;
        String key = "file=";
        int nKey = 5;
        String result = null;
        int kq = 0;
        while ((inputLine = in.readLine()) != null) {
            for (int i = 0; i < inputLine.length(); i++) {
                if (inputLine.charAt(i) == key.charAt(0)) {
                    int check = 1;
                    for (int j = 1; j < nKey; j++) {
                        if (inputLine.charAt(i + j) != key.charAt(j)) {
                            check = 0;
                            break;
                        }
                    }
                    if (check == 1) {
                        i += 5;
                        result = "";
                        while (inputLine.charAt(i) != '&') {
                            result += inputLine.charAt(i);
                            i++;
                        }
                        kq++;
                        break;
                    }
                }
            }
        }
        if (kq != 0) {
            this.out.print("{\"label\": \"" + result + "\"}");
        } else {
            this.out.print("{\"label\": \"notfound\"}");
        }
        in.close();
    }

    /**
     * Tim kiem tu ten mien : vndic.net
     *
     * @param mean
     * @param request
     * @throws MalformedURLException
     * @throws java.io.IOException
     * @throws UnsupportedEncodingException
     */
    private void searchFromWeb2(String mean, String request) throws MalformedURLException, java.io.IOException, UnsupportedEncodingException {
        request = URLEncoder.encode(request, "UTF-8");
        URL myURL = new URL("http://vndic.net/index.php?word=" + request + "&dict=" + mean);
        HttpURLConnection oracle = (HttpURLConnection) myURL.openConnection();
        oracle.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) coc_coc_browser/58.3.134 Chrome/52.3.2743.134 Safari/537.36");
        BufferedReader in = new BufferedReader(new InputStreamReader(oracle.getInputStream(), "UTF-8"));
        String inputLine;
        String key = "<img border=\"0\" src=\"images/green.png\">";
        String[] result = new String[100];
        int nKey = 39;
        int kq = 0;
        while ((inputLine = in.readLine()) != null) {
            for (int i = 0; i < inputLine.length(); i++) {
                if (inputLine.charAt(i) == key.charAt(0)) {
                    int check = 1;
                    for (int j = 1; j < nKey; j++) {
                        if (inputLine.charAt(i + j) != key.charAt(j)) {
                            check = 0;
                            break;
                        }
                    }
                    if (check == 1) {
                        i += 40;
                        result[kq] = "";
                        while (inputLine.charAt(i) != '<') {
                            if (inputLine.charAt(i) == '"') {
                                result[kq] += "\\\"";
                                i++;
                            } else {
                                result[kq] += inputLine.charAt(i);
                                i++;
                            }
                        }
                        result[kq] = result[kq].substring(0, result[kq].length() - 1);
                        kq++;
                    }
                }
            }

        }
        if (kq != 0) {
            this.out.print("[{\"label\": \"" + request + "\",");
            this.out.print("\"mean\": \"" + result[0] + "\"}");
            for (int i = 1; i < kq; i++) {
                this.out.print(",{\"label\": \"" + request + "\",");
                this.out.print("\"mean\": \"" + result[i] + "\"}");
            }
            this.out.print("]");
        } else {
            this.out.print("[{\"label\": \"" + request + "\",");
            this.out.print("\"mean\": \"not found\"}]");
        }
        in.close();
    }

    /**
     * Báo l?i v?i phýõng th?c GET
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws java.io.IOException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {
        String search = request.getParameter("search");
        response.setContentType("text/html");
        java.io.PrintWriter out = response.getWriter();
        String t = "wrong access!";
        byte[] strBytes = t.getBytes("utf8");
        t = new String(strBytes, "utf8");
        out.println(t);
    }

    /**
     * T?m ki?m t? tên mi?n: tratu.soha.vn
     *
     * @param mean
     * @param request
     * @throws MalformedURLException
     * @throws java.io.IOException
     * @throws UnsupportedEncodingException
     */
    private void searchFromWeb1(String mean, String request) throws MalformedURLException, java.io.IOException, UnsupportedEncodingException {
        request = request.replace(" ", "_");
        URL oracle = new URL("http://tratu.soha.vn/dict/" + mean + "/" + request);
        URLConnection yc = oracle.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
        String inputLine;
        String key = "<div id=\"content-5\"";
        int nKey = 19;
        String[] result = new String[100];
        int kq = 0;
        while ((inputLine = in.readLine()) != null) {
            for (int i = 0; i < inputLine.length(); i++) {
                if (inputLine.charAt(i) == key.charAt(0)) {
                    int check = 1;
                    for (int j = 1; j < nKey; j++) {
                        if (inputLine.charAt(i + j) != key.charAt(j)) {
                            check = 0;
                            break;
                        }
                    }
                    if (check == 1) {
                        i += 70;
                        if (inputLine.charAt(i) == '/') {
                            continue;
                        }
                        result[kq] = "";
                        while (inputLine.charAt(i) != '<') {
                            result[kq] += inputLine.charAt(i);
                            i++;
                        }
                        if ("(".equals(result[kq])) {
                            result[kq] = "";
                            continue;
                        }
                        if (" ".equals(result[kq])) {
                            break;
                        }
                        if ("".equals(result[kq])) {
                            break;
                        }
                        kq++;
                    }
                }
            }
        }
        if (kq != 0) {
            this.out.print("[{\"label\": \"" + request + "\",");
            this.out.print("\"mean\": \"" + result[0] + "\"}");
            for (int i = 1; i < kq; i++) {
                this.out.print(",{\"label\": \"" + request + "\",");
                this.out.print("\"mean\": \"" + result[i] + "\"}");
            }
            this.out.print("]");
        } else {
            this.out.print("[{\"label\": \"" + request + "\",");
            this.out.print("\"mean\": \"not found\"}]");
        }
        in.close();
    }

    /**
     * T?m ki?m âm thanh t? tên mi?n: tratu.soha.vn
     *
     * @param mean
     * @param request
     * @throws MalformedURLException
     * @throws java.io.IOException
     * @throws UnsupportedEncodingException
     */
    private void searchSoundWeb2(String mean, String request) throws MalformedURLException, java.io.IOException, UnsupportedEncodingException {
        request = request.replace(" ", "_");
        URL oracle = new URL("http://tratu.soha.vn/dict/" + mean + "/" + request);
        URLConnection yc = oracle.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
        String inputLine;
        String key = "file=";
        int nKey = 5;
        String result = null;
        int kq = 0;
        while ((inputLine = in.readLine()) != null) {
            for (int i = 0; i < inputLine.length(); i++) {
                if (inputLine.charAt(i) == key.charAt(0)) {
                    int check = 1;
                    for (int j = 1; j < nKey; j++) {
                        if (inputLine.charAt(i + j) != key.charAt(j)) {
                            check = 0;
                            break;
                        }
                    }
                    if (check == 1) {
                        i += 5;
                        result = "";
                        while (inputLine.charAt(i) != '&') {
                            result += inputLine.charAt(i);
                            i++;
                        }
                        kq++;
                        break;
                    }
                }
            }
        }
        if (kq != 0) {
            this.out.print("{\"label\": \"" + result + "\"}");
        } else {
            this.out.print("{\"label\": \"notfound\"}");
        }
        in.close();
    }

    /**
     * T?m ki?m âm thanh t? tên mi?n vndic.net
     *
     * @param mean
     * @param request
     * @throws MalformedURLException
     * @throws java.io.IOException
     * @throws UnsupportedEncodingException
     */
    private void searchSoundWeb3(String mean, String request) throws MalformedURLException, java.io.IOException, UnsupportedEncodingException {
        System.out.println(URLEncoder.encode(request, "UTF-8"));
        URL myURL = new URL("http://vndic.net/index.php?word=" + request + "&dict=" + mean);
        HttpURLConnection oracle = (HttpURLConnection) myURL.openConnection();
        oracle.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) coc_coc_browser/58.3.134 Chrome/52.3.2743.134 Safari/537.36");
        BufferedReader in = new BufferedReader(new InputStreamReader(oracle.getInputStream(), "UTF-8"));
        String inputLine;
        String key = "soundManager.play";
        int nKey = 17;
        String result = null;
        int kq = 0;
        while ((inputLine = in.readLine()) != null) {
            for (int i = 0; i < inputLine.length(); i++) {
                if (inputLine.charAt(i) == key.charAt(0)) {
                    int check = 1;
                    for (int j = 1; j < nKey; j++) {
                        if (inputLine.charAt(i + j) != key.charAt(j)) {
                            check = 0;
                            break;
                        }
                    }
                    if (check == 1) {
                        i += 19;
                        result = "http://vndic.net/audio/";
                        while (inputLine.charAt(i) != '\'') {
                            result += inputLine.charAt(i);
                            i++;
                        }
                        result += ".mp3";
                        kq++;
                        break;
                    }
                }
            }
        }
        if (kq != 0) {
            this.out.print("{\"label\": \"" + result + "\"}");
        } else {
            this.out.print("{\"label\": \"notfound\"}");
        }
        in.close();
    }
}
