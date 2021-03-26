package com.sdilavar.phonebook;

import com.sdilavar.phonebook.datamodel.ContactData;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.text.ParseException;

public class ImportDialogController {
    @FXML
    private TextField pathTextField;

    @FXML
    public void importData() {
        if (!pathTextField.getText().trim().isEmpty()) {
            try {
                ContactData.getInstance().loadContacts(pathTextField.getText());
            } catch (Exception e) {
                Utils.alertUser(e);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Enter a path to import data from");
            alert.showAndWait();
        }
    }
}
