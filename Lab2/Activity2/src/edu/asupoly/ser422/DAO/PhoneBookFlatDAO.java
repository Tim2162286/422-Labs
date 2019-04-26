package edu.asupoly.ser422.DAO;

import edu.asupoly.ser422.model.PhoneEntry;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PhoneBookFlatDAO implements PhoneBookDAO {
    private Map<String, PhoneEntry> _pbook = new HashMap<>();
    File file;
    public PhoneBookFlatDAO(String filePath){
        file = new File(filePath);
        try {
            if(!file.exists())
                file.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
        String name = null;
        String lname = null;
        String phone = null;

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String nextLine = null;
            while ( (nextLine=br.readLine()) != null)
            {
                name  = nextLine;
                lname = br.readLine();
                phone = br.readLine();
                _pbook.put(phone,new PhoneEntry(name, lname, phone));
            }
            br.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Error process phonebook");
            //throw new IOException("Could not process phonebook file");
        }

    }
    @Override
    public void addEntry(String fname, String lname, String phone) {
        _pbook.put(phone,new PhoneEntry(fname,lname,phone));
        savePhoneBook();
    }

    @Override
    public PhoneEntry removeEntry(String phone) {
        return _pbook.remove(phone);
    }

    @Override
    public String[] listEntries() {
        String[] rval = new String[_pbook.size()];
        int i = 0;
        PhoneEntry nextEntry = null;
        for (Iterator<PhoneEntry> iter = _pbook.values().iterator(); iter.hasNext();) {
            nextEntry = iter.next();
            rval[i++] = nextEntry.toString();
        }
        return rval;
    }

    @Override
    public void editEntry(String fname, String lname, String phone) {
        PhoneEntry pentry = _pbook.get(phone);
        pentry.changeName(fname, lname);
        savePhoneBook();
    }

    public void savePhoneBook() {
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(file));
            String[] entries = listEntries();
            for (int i = 0; i < entries.length; i++)
                pw.println(entries[i]);
            pw.close();
        }
        catch (Exception exc) {
            exc.printStackTrace();
            System.out.println("Error saving phone book");
        }
    }
}
