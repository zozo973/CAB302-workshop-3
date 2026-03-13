package com.example.addressbook;

public class Contact {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    public Contact(String firstName, String lastName, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    public int getId() { return this.id; }

    public void setId(int id) { this.id = id; }

    public String getFirstName() { return this.firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return this.lastName; }

    public void setLastName(String firstName) { this.lastName = lastName; }

    public String getEmail() { return this.email; }

    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return this.phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}
