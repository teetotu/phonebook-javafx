package com.sdilavar.phonebook;

import com.sdilavar.phonebook.datamodel.Contact;
import com.sdilavar.phonebook.datamodel.ContactData;
import com.sdilavar.phonebook.datamodel.PhoneNumber;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public class Controller {
    @FXML
    private TableView<Contact> tableView;
    @FXML
    private TableColumn<Contact, String> firstName;
    @FXML
    private TableColumn<Contact, String> lastName;
    @FXML
    private TableColumn<Contact, String> patronymic;
    @FXML
    private TableColumn<Contact, PhoneNumber> cellNumber;
    @FXML
    private TableColumn<Contact, PhoneNumber> homeNumber;
    @FXML
    private TableColumn<Contact, String> address;
    @FXML
    private TableColumn<Contact, LocalDate> birthdate;
    @FXML
    private TableColumn<Contact, String> comment;
    @FXML
    private BorderPane mainBorderPane;

    private ObservableList<Contact> contacts;

    @FXML
    public void initialize() {
        try {
            File f = new File("data.txt");
            if (f.exists()) {
                ContactData.getInstance().loadContacts(f);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        firstName.setCellValueFactory(data -> data.getValue().firstNameProperty());
        lastName.setCellValueFactory(data -> data.getValue().lastNameProperty());
        patronymic.setCellValueFactory(data -> data.getValue().patronymicProperty());
        cellNumber.setCellValueFactory(data -> data.getValue().cellNumberProperty());
        homeNumber.setCellValueFactory(data -> data.getValue().homeNumberProperty());
        address.setCellValueFactory(data -> data.getValue().addressProperty());
        birthdate.setCellValueFactory(data -> data.getValue().birthdateProperty());
        comment.setCellValueFactory(data -> data.getValue().commentProperty());

        contacts = FXCollections.observableArrayList();
        contacts.addAll(ContactData.getInstance().getContacts());
        tableView.getItems().setAll(contacts);
    }

    @FXML
    public void exportContactsDialog() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        try {
            if (selectedFile != null) {
                ContactData.getInstance().storeContacts(selectedFile);
            }
        } catch (Exception e) {
            Utils.alertUser(e);
        }
    }

    @FXML
    public void importContactsDialog() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        try {
            if (selectedFile != null) {
                ContactData.getInstance().loadContacts(selectedFile);
            }
        } catch (Exception e) {
            Utils.alertUser(e);
        }
    }

    @FXML
    public void showAboutDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Справка");
        alert.setGraphic(null);
        alert.setContentText("Проект Телефонная книга\n" +
                "Выполнил студент БПИ196\n" +
                "Х. Дилавар А.Ш.");
        alert.showAndWait();
    }

    @FXML
    public void showNewContactDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("newcontactdialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            NewContactDialogController controller = fxmlLoader.getController();
            try {
                Contact newContact = controller.processInput();
                ContactData.getInstance().addContact(newContact);
                contacts.add(newContact);
            } catch (Exception e) {
                System.out.println("error");
                Utils.alertUser(e);
            }
        }
    }


    @FXML
    private void closeAppAction() {
        Stage stage = (Stage) mainBorderPane.getScene().getWindow();
        try {
            ContactData.getInstance().storeContacts(new File("data.txt"));
        } catch (Exception e) {
            Utils.alertUser(e);
        }
        System.out.println("closing");
        stage.close();
    }
}
