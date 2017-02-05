
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;
import java.sql.*;
// Extend HttpServlet class

/**
 * CLASS LI�N K?T V?I C� S? D? LI?U SQL
 *
 * @author SON
 */
public class DB {

    String database;
    String request;
    Connection connection;
    Statement statement;

    /**
     * Kh?i t?o kh�ng tham s? k?t n?i v?i c� s? d? li?u c� t�n l� translater
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    DB() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost/translater?useUnicode=true&characterEncoding=UTF-8", "root", "root");
        statement = connection.createStatement();
        statement.execute("set character_set_results=utf8");
    }

    /**
     * Th�m 1 ph?n t? v�o b?ng v?i t? v� ngh?a t��ng ?ng v?i lo?i ng�n ng?
     *
     * @param lang1: ng�n ng? 1
     * @param lang2: ng�n ng? 2
     * @param mean1: T? kh�a
     * @param mean2: Ngh?a
     * @throws SQLException
     */
    public void insertElement(String lang1, String lang2, String mean1, String mean2) throws SQLException {
        this.request = "insert into engvie(" + lang1 + "," + lang2 + ") values ('" + mean1 + "','" + mean2 + "');";
        statement.executeUpdate(this.request);
    }

    /**
     * X�a 1 ph?n t?
     *
     * @param lang1
     * @param lang2
     * @param mean1
     * @param mean2
     * @throws SQLException
     */
    public void deleteElement(String lang1, String lang2, String mean1, String mean2) throws SQLException {
        this.request = "delete from engvie where " + lang1 + "= '" + mean1 + "' and " + lang2 + "= '" + mean2 + "';";
        statement.executeUpdate(this.request);
    }

    /**
     * Th�m m?ng v�o db
     *
     * @param lang1
     * @param lang2
     * @param mean1
     * @param mean2
     * @param length
     */
    public void insertArray(String lang1, String lang2, String[] mean1, String[] mean2, int length) {
        try {
            this.request = "insert into engvie(" + lang1 + "," + lang2 + ") values ";
            int i;
            for (i = 0; i < length; i++) {
                this.request += "('" + mean1[i] + "','" + mean2[i] + "'),";
            };
            this.request += "('" + mean1[i] + "','" + mean2[i] + "');";
            statement.executeUpdate(this.request);
        } catch (SQLException se) {
            System.out.println("D�� li��u �a� co�");
        }
    }

    /**
     * T?m ki?m t? b?ng ph?c v? cho autocomplete
     *
     * @param lang1
     * @param lang2
     * @param mean
     * @return
     * @throws SQLException
     */
//    public ResultSet search(String lang1, String lang2, String mean) throws SQLException {
//        this.request = "select " + lang2 + " from engvie where " + lang1 + " = '" + mean + "';";
//        ResultSet result = statement.executeQuery(this.request);
//        return result;
//    }

    /**
     * T?m ki?m t? b?ng ph?c v? cho vi?c t?m ki?m
     *
     * @param lang1
     * @param mean
     * @return
     * @throws SQLException
     */
    public ResultSet search1(String lang1, String mean) throws SQLException {
        this.request = "select * from engvie where " + lang1 + " = '" + mean + "';";
        ResultSet result = statement.executeQuery(this.request);
        return result;
    }

    /**
     * T?m ki?m t? b?ng ph?c v? cho autocomplete
     *
     * @param lang1
     * @param mean
     * @return
     * @throws SQLException
     */
    public ResultSet autocomplete(String lang1, String mean) throws SQLException {
        this.request = "select distinct " + lang1 + " from engvie where substring(" + lang1 + ",1," + mean.length() + ") = '" + mean + "' limit 20;";
        ResultSet result = statement.executeQuery(this.request);
        return result;
    }

    /**
     * H�m test db
     * @param args
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
//    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//        DB test = new DB();
//        test.insertElement("eng", "vie", "hello", "xin cha�o");
//        String[] arr1 = new String[100];
//        String[] arr2 = new String[100];
//        arr1[0] = "result";
//        arr2[0] = "ket qua";
//        arr1[1] = "market";
//        arr2[1] = "cho";
//        test.insertArray("eng", "vie", arr1, arr2, 2);
//        ResultSet result = test.search("eng", "vie", "hello");
//        while (result.next()) {
//            System.out.println(" + " + result.getString(1));
//        }
//        Scanner input = new Scanner(System.in);
//        String t = input.next();
//        result = test.search("vie", "eng", t);
//        while (result.next()) {
//            System.out.println(" + " + result.getString(1));
//        }
//    }

}
