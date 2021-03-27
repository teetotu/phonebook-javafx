package com.sdilavar.phonebook.datamodel;

import com.sdilavar.phonebook.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedHashSet;
import java.util.Optional;

public class ContactData {

    private static final ContactData instance = new ContactData();

    private ObservableSet<Contact> contacts = FXCollections.observableSet(new LinkedHashSet<>());
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static ContactData getInstance() {
        return instance;
    }

    private ContactData() {
    }

    public ObservableSet<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(ObservableSet<Contact> contacts) {
        this.contacts = contacts;
    }

    public void addContact(Contact contact) {
        boolean isAlreadyInList = false;
        boolean shouldAdd = true;
        String firstName = "";
        while (contacts.contains(contact)) {
            firstName = contact.getFirstName();
            contact.setFirstName(contact.getFirstName() + "(copy)");
            isAlreadyInList = true;
        }
        if (isAlreadyInList) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(String.format("Повторный контакт %s %s", firstName, contact.getLastName()));

            ScrollPane scroll = new ScrollPane();
            scroll.setContent(new TextArea("Встречен повторный контакт, чтобы пропустить нажмите Отмена,\n " +
                    "если вы хотите добавить его как '" + contact.getFirstName() + "' Нажмтие Ок"));
            alert.getDialogPane().setContent(scroll);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.CANCEL) {
                shouldAdd = false;
            }
        }
        if (shouldAdd) {
            contacts.add(contact);
        }
    }

    public void loadContacts(File file) throws IOException, DateTimeParseException,
            ArrayIndexOutOfBoundsException {

        String input;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((input = br.readLine()) != null && !input.isEmpty()) {
                String[] entry = input.split(";");
                String lastName = entry[0].trim();
                String firstName = entry[1].trim();
                String patronymic = entry[2].trim();
                PhoneNumber cellNumber = new PhoneNumber(entry[3].trim());
                PhoneNumber homeNumber = new PhoneNumber(entry[4].trim());
                String address = entry[5].trim();
                String birthDateString = entry[6].trim();
                String comment = " ";
                if (entry.length == 8) {
                    comment = entry[7].trim();
                }
                LocalDate birthDate = null;
                if (!birthDateString.isEmpty()) {
                    birthDate = LocalDate.parse(birthDateString, dateTimeFormatter);
                }

                Contact newContact = new Contact(firstName, lastName, patronymic,
                        cellNumber, homeNumber, address, birthDate, comment);
                addContact(newContact);
            }
        }
    }

    public void storeContacts(File file) throws IOException {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (Contact contact : contacts) {
                try {
                    bw.write(String.format("%s ;%s ;%s ;%s ;%s ;%s ;%s ;%s ",
                            contact.getLastName(),
                            contact.getFirstName(),
                            contact.getPatronymic(),
                            contact.getCellNumber(),
                            contact.getHomeNumber(),
                            contact.getAddress(),
                            contact.getBirthdateString(),
                            contact.getComment()
                    ));
                } catch (Exception e) {
                    Utils.alertUser(e);
                }
                bw.newLine();
            }
        }

    }
}
