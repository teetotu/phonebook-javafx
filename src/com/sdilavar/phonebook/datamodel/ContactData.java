package com.sdilavar.phonebook.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

public class ContactData {

    private static final ContactData instance = new ContactData();

    private ObservableList<Contact> contacts = FXCollections.observableArrayList();
    private final DateTimeFormatter dateTimeFormatter;

    public static ContactData getInstance() {
        return instance;
    }

    private ContactData() {
        dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    public ObservableList<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(ObservableList<Contact> contacts) {
        this.contacts = contacts;
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public void loadContacts(String fileName) throws IOException, DateTimeParseException,
            ArrayIndexOutOfBoundsException {
        Path path = Paths.get(fileName);

        String input;

        try (BufferedReader br = Files.newBufferedReader(path)) {
            while ((input = br.readLine()) != null) {
                String[] entry = input.split(";");
                String lastName = entry[0];
                String firstName = entry[1];
                String patronymic = entry[2];
                PhoneNumber cellNumber = new PhoneNumber(entry[3]);
                PhoneNumber homeNumber = new PhoneNumber(entry[4]);
                String address = entry[5];
                String birthDateString = entry[6];

                LocalDate birthDate = LocalDate.parse(birthDateString, dateTimeFormatter);
                Contact contact = new Contact(lastName, firstName, patronymic,
                        cellNumber, homeNumber, address, birthDate);
                System.out.println(contact);
                contacts.add(contact);
            }
        }
    }

    public void storeContacts(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            for (Contact contact : contacts) {
                bw.write(String.format("%s;%s;%s;%s;%s;%s;%s",
                        contact.getLastName(),
                        contact.getFirstName(),
                        contact.getPatronymic(),
                        contact.getCellNumber(),
                        contact.getHomeNumber(),
                        contact.getAddress(),
                        contact.getBirthdate().format(dateTimeFormatter)
                ));
                bw.newLine();
            }
        }
    }
}
