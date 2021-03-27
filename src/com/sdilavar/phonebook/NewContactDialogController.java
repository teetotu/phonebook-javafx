package com.sdilavar.phonebook;

import com.sdilavar.phonebook.datamodel.Contact;
import com.sdilavar.phonebook.datamodel.PhoneNumber;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NewContactDialogController {
    @FXML
    private TextField addressField;
    @FXML
    private TextField homeNumberField;
    @FXML
    private TextField cellNumberField;
    @FXML
    private TextField patronymicField;
    @FXML
    private DatePicker birthDateDatePicker;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextArea commentTextArea;

    @FXML
    public Contact processInput() {
        return new Contact(firstNameField.getText(),
                lastNameField.getText(),
                patronymicField.getText(),
                new PhoneNumber(cellNumberField.getText()),
                new PhoneNumber(homeNumberField.getText()),
                addressField.getText(),
                birthDateDatePicker.getValue(),
                commentTextArea.getText());
    }
}
