package edu.asupoly.ser422.lab3;

import javax.xml.bind.annotation.*;

@XmlRootElement()
public class PhoneEntryResource {
	public PhoneEntryResource(){}

    public PhoneEntryResource(String name, String lname, String phone) {
	this.firstname = name;
	this.lastname = lname;
	this.phone = phone;
    }
	@Override
	public String toString() {
		return ("Phone "+phone+", First "+firstname+", Last "+lastname);
	}
	public boolean invalid(){
		return (firstname==null||lastname==null||phone==null);
	}
	public String firstname;
	public String lastname;
	public String phone;
	//public String phonebook;
}



