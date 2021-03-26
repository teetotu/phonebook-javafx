package com.sdilavar.phonebook;

import com.sdilavar.phonebook.datamodel.ContactData;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class ExportDialogController {
    @FXML
    private TextField pathTextField;

    @FXML
    public void exportData() {
        if (!pathTextField.getText().trim().isEmpty()) {
            try {
                ContactData.getInstance().storeContacts(pathTextField.getText());
            } catch (Exception e) {
                Utils.alertUser(e);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Enter a path to export data to");
            alert.showAndWait();
        }
    }
}
