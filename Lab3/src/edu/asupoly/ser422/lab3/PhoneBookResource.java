package edu.asupoly.ser422.lab3;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class PhoneBookResource {
    private String name;
    public List<PhoneEntryResource> listings;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PhoneBookResource(String name, List<PhoneEntryResource> listings) {
        this.name = name;
        this.listings = listings;
    }
}
