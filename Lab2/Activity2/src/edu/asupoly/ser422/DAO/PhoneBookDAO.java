package edu.asupoly.ser422.DAO;

import edu.asupoly.ser422.model.PhoneEntry;

public interface PhoneBookDAO {
    public void addEntry(String fname, String lname, String phone);
    public PhoneEntry removeEntry(String phone);
    public String[] listEntries();
    public void editEntry(String fname, String lname, String phone);
}
