package edu.asupoly.ser422.service;

import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

public class PhoneBookFactory {

    private static PhoneBookService pBook;
    public static PhoneBookService getInstance(){return pBook;}

    static {
        try {
            Properties dbProperties = new Properties();
            Class<?> initClass = null;
            InputStream is = PhoneBookFactory.class.getClassLoader().getResourceAsStream("../properties/configuration.properties");
            Enumeration<URL> e = PhoneBookFactory.class.getClassLoader().getResources("../properties");
            URL url;
            while(e.hasMoreElements()){
                url = e.nextElement();
                System.out.println(url);
            }
            System.out.println(is);
            dbProperties.load(is);

            String serviceImpl = dbProperties.getProperty("datastore.type");
            if (serviceImpl.equals("mySQL")) {
                initClass = Class.forName("edu.asupoly.ser422.service.impl.MySQL_PhoneBookService");
            } else {
                initClass = Class.forName("edu.asupoly.ser422.service.impl.SimplePhoneBookService");
            }
            pBook = (PhoneBookService) initClass.newInstance();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
        }
    }
}
