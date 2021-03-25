package com.sdilavar.phonebook;

import com.sdilavar.phonebook.datamodel.ContactData;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ImportDialogController {
    @FXML
    private TextField pathTextField;

    @FXML
    public void importData() {
        if (!pathTextField.toString().trim().isEmpty()) {
            try {
                ContactData.getInstance().loadContacts(pathTextField.toString());
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("IOException: the specified path led to an error, check it again");

                ScrollPane scroll = new ScrollPane();
                scroll.setContent(new TextArea(e.getMessage()));

                alert.getDialogPane().setContent(scroll);
                alert.showAndWait();

                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("An unknown error occurred");
            alert.showAndWait();
        }
    }
}
