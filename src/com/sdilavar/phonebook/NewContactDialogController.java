package com.sdilavar.phonebook;

import com.sdilavar.phonebook.datamodel.Contact;
import com.sdilavar.phonebook.datamodel.PhoneNumber;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NewContactDialogController {
    @FXML
    public TextField addressField;
    @FXML
    public TextField homeNumberField;
    @FXML
    public TextField cellNumberField;
    @FXML
    public TextField patronymicField;
    @FXML
    public DatePicker birthDateDatePicker;
    @FXML
    public TextField lastNameField;
    @FXML
    public TextField firstNameField;
    @FXML
    public TextArea commentTextArea;

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
