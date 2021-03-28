package com.sdilavar.phonebook.datamodel;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Contact {
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleStringProperty patronymic;
    private SimpleObjectProperty<PhoneNumber> cellNumber;
    private SimpleObjectProperty<PhoneNumber> homeNumber;

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public SimpleStringProperty patronymicProperty() {
        return patronymic;
    }

    public SimpleObjectProperty<PhoneNumber> cellNumberProperty() {
        return cellNumber;
    }

    public SimpleObjectProperty<PhoneNumber> homeNumberProperty() {
        return homeNumber;
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public SimpleObjectProperty<LocalDate> birthdateProperty() {
        return birthdate;
    }

    public SimpleStringProperty commentProperty() {
        return comment;
    }

    private SimpleStringProperty address;
    private SimpleObjectProperty<LocalDate> birthdate;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private SimpleStringProperty comment;

    public Contact(String firstName, String lastName, String patronymic, PhoneNumber cellNumber, PhoneNumber homeNumber,
                   String address, LocalDate birthdate, String comment) {
        if (firstName.trim().isEmpty() || lastName.trim().isEmpty() ||
                (!cellNumber.isSpecified() && !homeNumber.isSpecified())) {
            throw new NullPointerException("Not enough data | missing important information");
        }
        this.firstName = new SimpleStringProperty(firstName.trim());
        this.lastName = new SimpleStringProperty(lastName.trim());
        this.patronymic = new SimpleStringProperty(patronymic.trim().isEmpty() ? "" : patronymic);
        this.cellNumber = new SimpleObjectProperty<>(cellNumber);
        this.homeNumber = new SimpleObjectProperty<>(homeNumber);
        this.address = new SimpleStringProperty(address.trim());
        this.birthdate = new SimpleObjectProperty<>(birthdate);
        this.comment = new SimpleStringProperty(comment.trim());
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName = new SimpleStringProperty(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName = new SimpleStringProperty(lastName);
    }

    public String getPatronymic() {
        return patronymic.get();
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = new SimpleStringProperty(patronymic);
    }

    public PhoneNumber getCellNumber() {
        return cellNumber.get();
    }

    public void setCellNumber(PhoneNumber cellNumber) {
        this.cellNumber = new SimpleObjectProperty<>(cellNumber);
    }

    public PhoneNumber getHomeNumber() {
        return homeNumber.get();
    }

    public void setHomeNumber(PhoneNumber homeNumber) {
        this.homeNumber = new SimpleObjectProperty<>(homeNumber);
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address = new SimpleStringProperty(address);
    }

    public LocalDate getBirthdate() {
        return birthdate.get();
    }

    public String getBirthdateString() {
        return birthdate.get() != null ? birthdate.get().format(dateTimeFormatter) : "";
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = new SimpleObjectProperty<>(birthdate);
    }

    public String getComment() {
        return comment.get();
    }

    public void setComment(String comment) {
        this.comment = new SimpleStringProperty(comment);
    }

    public void copyContact(Contact contact) {
        this.firstName = new SimpleStringProperty(contact.getFirstName().trim());
        this.lastName = new SimpleStringProperty(contact.getLastName().trim());
        this.patronymic =
                new SimpleStringProperty(contact.getPatronymic().trim().isEmpty() ? "" : contact.getPatronymic());
        this.cellNumber = new SimpleObjectProperty<>(contact.getCellNumber());
        this.homeNumber = new SimpleObjectProperty<>(contact.getHomeNumber());
        this.address = new SimpleStringProperty(contact.getAddress().trim());
        this.birthdate = new SimpleObjectProperty<>(contact.getBirthdate());
        this.comment = new SimpleStringProperty(contact.getComment().trim());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Contact contact = (Contact) other;
        return firstName.get().equals(contact.firstName.get()) && lastName.get().equals(contact.lastName.get()) &&
                patronymic.get().equals(contact.patronymic.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName.get(), lastName.get(), patronymic.get());
    }

    @Override
    public String toString() {
        return "Contact{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", cellNumber=" + cellNumber +
                ", homeNumber=" + homeNumber +
                ", address='" + address + '\'' +
                ", birthdate=" + birthdate +
                ", comment='" + comment + '\'' +
                '}';
    }
}
