package com.example.addressbook.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteContactDAO implements IContactDAO {
    private Connection connection;

    public SqliteContactDAO() {
        this.connection = SqliteConnection.getInstance();
        createTable();
        insertSampleData();
    }

    public void createTable() {
        try {
            Statement createTable = connection.createStatement();
            createTable.execute(
                    "CREATE TABLE IF NOT EXISTS contacts ("
                    + " id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + " firstName VARCHAR NOT NULL, "
                    + " lastName VARCHAR NOT NULL, "
                    + " email VARCHAR NOT NULL, "
                    + "phone VARCHAR NOT NULL"
                    + ")"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertSampleData() {
        try {
            // Clear table before inserting sample data
            Statement clearTable = connection.createStatement();
            clearTable.execute("DELETE FROM contacts");
            // Add sample data to contacts
            Statement insertSampleData = connection.createStatement();
            insertSampleData.execute(
                    "INSERT INTO contacts (firstName, lastName, email, phone) VALUES " +
                            "('John', 'Doe', 'Johndoe@example.com', '0423423423')," +
                            "('Jane', 'Doe', 'Janedoe@example.com', '0423423424')," +
                            "('Jay', 'Doe', 'Jaydoe@example.com', '0423423425')"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void addContact(Contact contact) {
        try {
            PreparedStatement addContact = connection.prepareStatement(
                    "INSERT INTO contacts (firstName, lastName, email, phone) VALUES (?,?,?,?)"
            );
            addContact.setString(1,contact.getFirstName());
            addContact.setString(2,contact.getLastName());
            addContact.setString(3,contact.getEmail());
            addContact.setString(4,contact.getPhone());
            addContact.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateContact(Contact contact) {
        try {
            PreparedStatement updateContact = connection.prepareStatement(
                    "UPDATE contacts SET firstName = ?, lastName = ?, email = ?, phone = ?, WHERE id = ?"
            );
            updateContact.setString(1,contact.getFirstName());
            updateContact.setString(2,contact.getLastName());
            updateContact.setString(3,contact.getEmail());
            updateContact.setString(4,contact.getPhone());
            updateContact.setInt(5,contact.getId());
            updateContact.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteContact(Contact contact) {
        try {
            PreparedStatement deleteContact = connection.prepareStatement(
                    "DELETE FROM contacts WHERE id =? "
            );
            deleteContact.setInt(1,contact.getId());
            deleteContact.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Contact getContact(int id) {
        return null;
    }

    @Override
    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        try {
            Statement getAll = connection.createStatement();
            ResultSet rs = getAll.executeQuery("SELECT * FROM contacts");
            while (rs.next()) {
                Contact contact = new Contact(
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("email"),
                        rs.getString("phone")
                );
                contact.setId(rs.getInt("id"));
                contacts.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contacts;
    }
}
