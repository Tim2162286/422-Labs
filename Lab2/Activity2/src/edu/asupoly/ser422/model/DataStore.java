package edu.asupoly.ser422.model;

import com.mysql.cj.jdbc.MysqlDataSource;
import edu.asupoly.ser422.DAO.PhoneBookDAO;
import edu.asupoly.ser422.DAO.PhoneBookFlatDAO;
import edu.asupoly.ser422.DAO.PhoneBookMySqlDAO;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.WatchService;
import java.util.Properties;
import java.util.logging.Logger;

public class DataStore {
    private PhoneBookDAO pbook;
    private static DataStore dataStore = null;
    private String path;
    private static Logger log = Logger.getLogger(DataStore.class.getName());

    public static DataStore getDataStore(){
        return dataStore;
    }
    public static DataStore createDataStore(String applicationPath){
        dataStore = new DataStore(applicationPath);
        return dataStore;
    }

    public static boolean isInitilized(){return (dataStore!=null);}

    private DataStore(String applicationPath){
        path = applicationPath;
        loadDataStore();
}
     public PhoneBookDAO getPbook(){return pbook;}

     public void loadDataStore(){
         Properties config = new Properties();
         try {
             FileInputStream is = new FileInputStream(path+"WEB-INF/properties/configuration.properties");
             config.load(is);
             new File(path+"WEB-INF/properties/configuration.properties");
         } catch (Exception e){
             e.printStackTrace();
             config.setProperty("datastore.type","flatFile");
             config.setProperty("file.name","phonebook.txt");
             config.setProperty("file.relPath","WEB-INF/data/");
         }
         String storeType = config.getProperty("datastore.type");
         if(storeType.equals("mySQL")){
             log.info("Initializing MySQL database connection");
             String url = config.getProperty("db.url");
             String user = config.getProperty("db.username");
             String pass = config.getProperty("db.password");
             MysqlDataSource ds = new MysqlDataSource();
             ds.setURL(url);
             ds.setUser(user);
             ds.setPassword(pass);
             pbook = new PhoneBookMySqlDAO(ds, path);
         }else{
             log.info("Initializing Flat File store");
             String filePath = config.getProperty("file.relPath");
             String fileName = config.getProperty("file.name");
             pbook = new PhoneBookFlatDAO(path+filePath+fileName);
         }
     }
}
