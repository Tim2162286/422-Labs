package edu.asupoly.ser422.service;

import edu.asupoly.ser422.lab3.PhoneEntryResource;

import java.util.List;

public interface PhoneBookService {

    //PhoneBookMethods
    int createPhoneBook(String name);
    List<PhoneEntryResource> getBookEntries(String bookName);

    //PhoneEntryMethods
    List<PhoneEntryResource> getAllEntries();
    List<PhoneEntryResource> getUnlisted();
    PhoneEntryResource getEntry(String number); //1
    int newEntry(PhoneEntryResource entry); //2
    int addToBook(String entry,String book); //3
    int updateEntry(PhoneEntryResource number, PhoneEntryResource update); //4
    int deleteEntry(String number);



}
