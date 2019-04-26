package edu.asupoly.ser422.service.impl;

import edu.asupoly.ser422.lab3.PhoneEntryResource;
import edu.asupoly.ser422.service.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MySQL_PhoneBookService implements PhoneBookService {

    private static Properties __dbProperties;
    private static String __jdbcUrl;
    private static String __jdbcUser;
    private static String __jdbcPasswd;
    private static String __jdbcDriver;


    @Override
    public int createPhoneBook(String name) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int ret = 0;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(__dbProperties.getProperty("sql.createBook"));
            stmt.setString(1,name);
            stmt.execute();
            ret = 1;
        }catch(Exception e){
            e.printStackTrace();
            ret = -1;
        } finally {
            closeConnection(conn,stmt,null);
        }
        return ret;
    }

    @Override
    public List<PhoneEntryResource> getBookEntries(String bookName) {
        List<PhoneEntryResource> listings = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            conn = getConnection();
            stmt = conn.prepareStatement(__dbProperties.getProperty("sql.getBook"));
            stmt.setString(1,bookName);
            stmt.execute();
            rs = stmt.getResultSet();
            String bookname;
            if(rs.first()){
                bookname = rs.getString("book");
                stmt = conn.prepareStatement(__dbProperties.getProperty("sql.getPhonesFromBook"));
                stmt.setString(1,bookname);
                stmt.execute();
                rs = stmt.getResultSet();
                if(rs.first()){
                    listings = new ArrayList<PhoneEntryResource>();
                    String number;
                    String first;
                    String last;
                    do{
                        number = rs.getString("phone");
                        first = rs.getString("first");
                        last = rs.getString("last");
                        listings.add(new PhoneEntryResource(first,last,number));
                    }while (rs.next());
                }

            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            closeConnection(conn,stmt,rs);
        }
        return listings;
    }

    @Override
    public List<PhoneEntryResource> getAllEntries() {
        return null;
    }

    @Override
    public List<PhoneEntryResource> getUnlisted() {
        List<PhoneEntryResource> entries = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(__dbProperties.getProperty("sql.getPhones"));
            stmt.execute();
            rs = stmt.getResultSet();
            entries = new ArrayList<>();
            if(rs.first()){
                String fName;
                String lName;
                String phone;
                String phoneBook;
                do {
                    fName = rs.getString("first");
                    lName = rs.getString("last");
                    phone = rs.getString("phone");
                    phoneBook = rs.getString("phonebook");
                    if(phoneBook==null)
                        entries.add(new PhoneEntryResource(fName,lName,phone));
                } while (rs.next());
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeConnection(conn,stmt,rs);
        }
        return entries;
    }

    @Override
    public PhoneEntryResource getEntry(String number) {
        PhoneEntryResource entry = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(__dbProperties.getProperty("sql.getEntry"));
            stmt.setString(1, number);
            stmt.execute();
            rs = stmt.getResultSet();
            if (rs.first()) {
                String phone = rs.getString("phone");
                String first = rs.getString("first");
                String last = rs.getString("last");

                entry = new PhoneEntryResource(first, last, phone);
                System.out.println("Entry: " + entry);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            closeConnection(conn,stmt,rs);
        }
        return entry;
    }

    @Override
    public int newEntry(PhoneEntryResource entry) {
        int res = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(__dbProperties.getProperty("sql.createEntry"));
            stmt.setString(1,entry.phone);
            stmt.setString(2,entry.firstname);
            stmt.setString(3,entry.lastname);
            stmt.execute();
            res = 1;
        }catch (Exception e){
            res = -1;
        } finally {
            closeConnection(conn,stmt,null);
        }


        return res;
    }

    @Override
    public int addToBook(String entry, String book) {
        int res = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(__dbProperties.getProperty("sql.getEntry"));
            stmt.setString(1,entry);
            stmt.execute();
            rs = stmt.getResultSet();
            if(rs.first()){
                String pbook = rs.getString("phonebook");
                System.out.println("Book: "+pbook);
                if(pbook!=null)
                    res = -3;
                else {
                    stmt = conn.prepareStatement(__dbProperties.getProperty("sql.addToBook"));
                    stmt.setString(1,book);
                    stmt.setString(2,entry);
                    stmt.execute();
                    res = 1;
                }
            }else
                res = -2;
        }catch (Exception e){
            e.printStackTrace();
            res = -1;
        } finally {
            closeConnection(conn,stmt,rs);
        }
        return res;
    }

    @Override
    public int updateEntry(PhoneEntryResource entry, PhoneEntryResource update) {
        int updated = 0;
        if(update.firstname!=null)
            entry.firstname = update.firstname;
        if(update.lastname!=null)
            entry.lastname = update.lastname;
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(__dbProperties.getProperty("sql.updateEntry"));
            stmt.setString(1,entry.firstname);
            stmt.setString(2,entry.lastname);
            stmt.setString(3,entry.phone);
            stmt.execute();
            updated = 1;
        }catch (Exception e){
            e.printStackTrace();
            updated = -1;
        }finally {
            closeConnection(conn,stmt,null);
        }
        return updated;
    }

    private Connection getConnection() throws Exception {
        try {
            Class.forName(__jdbcDriver);
            //System.out.println("Driver: "+driver);
            return DriverManager.getConnection(__jdbcUrl, __jdbcUser, __jdbcPasswd);
        } catch (Exception exc) {
            throw exc;
        }
    }

    @Override
    public int deleteEntry(String number) {
        int res = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn=getConnection();
            stmt = conn.prepareStatement(__dbProperties.getProperty("sql.deleteEntry"));
            stmt.setString(1,number);
            stmt.execute();
            res = 1;
        }catch (Exception e){
            e.printStackTrace();
            res = -1;
        }finally {
            closeConnection(conn,stmt,null);
        }
        return res;
    }

    private void closeConnection(Connection conn, Statement stmt, ResultSet rs){
        try{
            if(rs!= null)
                rs.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        try{
            if(stmt!= null)
                stmt.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        try{
            if(conn!= null)
                conn.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    static {
        try {
            __dbProperties = new Properties();
            __dbProperties.load(MySQL_PhoneBookService.class.getClassLoader().getResourceAsStream("../properties/rdbm.properties"));
            __jdbcUrl    = __dbProperties.getProperty("db.url");
            __jdbcUser   = __dbProperties.getProperty("db.username");
            __jdbcPasswd = __dbProperties.getProperty("db.password");
            __jdbcDriver = __dbProperties.getProperty("db.driver");
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
        }
    }
}
