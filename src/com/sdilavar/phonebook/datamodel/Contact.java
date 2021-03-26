package com.sdilavar.phonebook.datamodel;

import java.time.LocalDate;

public class Contact {
    private String firstName;
    private String lastName;
    private String patronymic;
    private PhoneNumber cellNumber;
    private PhoneNumber homeNumber;
    private String address;
    private LocalDate birthdate;

    public Contact(String firstName, String lastName, String patronymic, PhoneNumber cellNumber, PhoneNumber homeNumber,
                   String address, LocalDate birthdate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.cellNumber = cellNumber;
        this.homeNumber = homeNumber;
        this.address = address;
        this.birthdate = birthdate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public PhoneNumber getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(PhoneNumber cellNumber) {
        this.cellNumber = cellNumber;
    }

    public PhoneNumber getHomeNumber() {
        return homeNumber;
    }

    public void setHomeNumber(PhoneNumber homeNumber) {
        this.homeNumber = homeNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
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
                '}';
    }
}
