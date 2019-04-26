package edu.asupoly.ser422.DAO;


import com.mysql.cj.jdbc.MysqlDataSource;
import edu.asupoly.ser422.model.PhoneEntry;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PhoneBookMySqlDAO implements PhoneBookDAO {
    Connection conn;
    String path;
    public PhoneBookMySqlDAO(MysqlDataSource ds, String path){
        this.path = path;

        try {
            ds.setCreateDatabaseIfNotExist(true);
            conn = ds.getConnection();
            //System.out.println(conn.getSchema());
            Statement s = conn.createStatement();
            System.out.println();
            try {
                s.execute("SELECT * FROM `listings`");
            } catch (SQLException sqlE){
                File sqlInit = new File(path+"WEB-INF/sql/MySqlInit.sql");
                StringBuilder sqlStmt = new StringBuilder();
                BufferedReader br = new BufferedReader(new FileReader(sqlInit));
                String line;
                while ((line=br.readLine())!=null){
                    sqlStmt.append(line);
                }
                String[] stmts = sqlStmt.toString().split(";");
                for(String str:stmts){
                    System.out.println(str+";");
                    s.execute(str);
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void addEntry(String fname, String lname, String phone) {
        try {
            Statement stmt = conn.createStatement();
            String insertStmt = "INSERT INTO listings (`PHONE`,`FIRST`, `LAST`) VALUES ('"+phone+"', '"+fname+"', '"+lname+"')";
            System.out.println(stmt.execute(insertStmt));
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public PhoneEntry removeEntry(String phone) {
        PhoneEntry removed = null;
        try {
            Statement stmt = conn.createStatement();
            String findStmt = "SELECT * from `listings` WHERE PHONE = '"+phone+"'";
            if (stmt.execute(findStmt)){
                ResultSet rs = stmt.getResultSet();
                if(rs.first()) {
                    removed = new PhoneEntry(rs.getString("FIRST"),
                            rs.getString("LAST"), rs.getString("PHONE"));
                    String insertStmt = "DELETE FROM `listings` WHERE PHONE = '"+phone+"'";
                    stmt.execute(insertStmt);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return removed;
    }

    @Override
    public String[] listEntries() {
        ArrayList<String> listings = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            String listStmt = "SELECT * FROM `listings`";
            ResultSet rs = stmt.executeQuery(listStmt);
            if(rs.first()){
                String entry;
                String first;
                String last;
                String phone;
                do {
                    first = rs.getString("FIRST");
                    last = rs.getString("LAST");
                    phone = rs.getString("PHONE");
                    entry = first+" "+last+" "+phone;
                    listings.add(entry);
                }while (rs.next());
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return listings.toArray(new String[listings.size()]);
    }

    @Override
    public void editEntry(String fname, String lname, String phone) {

    }
}
