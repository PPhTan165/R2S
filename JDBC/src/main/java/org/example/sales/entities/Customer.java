package org.example.sales.entities;

public class Customer {
    private int id;
    private String name;
    private String contact;
    private String address;
    private String city;
    private String postalCode;
    private String country;

    public Customer() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPostCode(String postCode) {
        this.postalCode = postCode;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Name: %s, Contact: %s, Address: %s, City: %s, PostalCode: %s, Country: %s",
         getId(), getName(), getContact(), getAddress(), getCity(), getPostalCode(), getCountry());
    }
}
