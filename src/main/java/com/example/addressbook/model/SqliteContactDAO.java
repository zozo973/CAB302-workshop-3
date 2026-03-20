package com.example.addressbook.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteContactDAO implements IContactDAO {
    Connection connection;

    public SqliteContactDAO() {
        this.connection = SqliteConnection.getInstance();
        createTable();
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

    @Override
    public void addContact(Contact contact) {
        try {
            PreparedStatement addContact = connection.prepareStatement(
                    "INSERT INTO contacts (firstName, lastName, email, phone) VALUES (?, ?, ?, ?)"
            );
            addContact.setString(1,contact.getFirstName());
            addContact.setString(2,contact.getLastName());
            addContact.setString(3,contact.getEmail());
            addContact.setString(4,contact.getPhone());
            addContact.executeUpdate();
            // Set id of the new contact
            ResultSet generatedKeys = addContact.getGeneratedKeys();
            if (generatedKeys.next()) {
                contact.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateContact(Contact contact) {
        try {
            PreparedStatement updateContact = connection.prepareStatement(
                    "UPDATE contacts SET firstName = ?, lastName = ?, email = ?, phone = ? WHERE id = ?"
            );
            updateContact.setString(1,contact.getFirstName());
            updateContact.setString(2,contact.getLastName());
            updateContact.setString(3,contact.getEmail());
            updateContact.setString(4,contact.getPhone());
            updateContact.setInt(5,contact.getId());
            updateContact.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error in update contact");
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
