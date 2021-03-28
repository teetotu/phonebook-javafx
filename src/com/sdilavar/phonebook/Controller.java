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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

public class Controller {
    @FXML
    private TextField searchField;
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

    private final ObservableList<Contact> contacts = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        try {
            String s = new File("").getAbsolutePath();
            File f = new File(s + "/data.txt");
            System.out.println(s);
            if (f.exists()) {
                ContactData.getInstance().loadContacts(f);
            }
        } catch (Exception e) {
            String property = "java.io.tmpdir";
            String tempDir = System.getProperty(property);
            File f = new File(tempDir + "/data.txt");
            if (f.exists()) {
                try {
                    ContactData.getInstance().loadContacts(f);
                } catch (IOException ioException) {
                    Utils.alertUser(ioException);
                }
            }
            Utils.alertUser(e);
        }
//        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        searchField.textProperty().addListener((observableValue, s, t1) -> {
            if (searchField.getText().trim().isEmpty()) {
                tableView.getItems().setAll(contacts);
            }
        });

        firstName.setCellValueFactory(column -> column.getValue().firstNameProperty());
        lastName.setCellValueFactory(column -> column.getValue().lastNameProperty());
        patronymic.setCellValueFactory(column -> column.getValue().patronymicProperty());
        cellNumber.setCellValueFactory(column -> column.getValue().cellNumberProperty());
        homeNumber.setCellValueFactory(column -> column.getValue().homeNumberProperty());
        address.setCellValueFactory(column -> column.getValue().addressProperty());
        birthdate.setCellValueFactory(column -> column.getValue().birthdateProperty());
        comment.setCellValueFactory(column -> column.getValue().commentProperty());

        contacts.addAll(ContactData.getInstance().getContacts());
        tableView.getItems().setAll(contacts);
    }

    @FXML
    private void contactExportHandler() {
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
    private void contactImportHandler() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        try {
            if (selectedFile != null) {
                ContactData.getInstance().loadContacts(selectedFile);
            }
        } catch (Exception e) {
            Utils.alertUser(e);
        }

        tableView.getItems().setAll(ContactData.getInstance().getContacts());
    }

    @FXML
    private void showAboutDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Справка");
        alert.setGraphic(null);
        alert.setContentText("Проект Телефонная книга\n" +
                "Выполнил студент БПИ196\n" +
                "Х. Дилавар А.Ш.");
        alert.showAndWait();
    }


    @FXML
    private void rowEditHandler() {
        Contact selectedContact = tableView.getSelectionModel().getSelectedItem();

        if (selectedContact != null) {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initOwner(mainBorderPane.getScene().getWindow());
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("newcontactdialog.fxml"));
            try {
                dialog.getDialogPane().setContent(fxmlLoader.load());
            } catch (IOException e) {
                Utils.alertUser(e);
                System.out.println("Couldn't load the dialog");
                e.printStackTrace();
            }

            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            NewContactDialogController controller = fxmlLoader.getController();
            setDialogTextFields(selectedContact, controller);

            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    Contact editedContact = controller.processInput();
                    selectedContact.copyContact(editedContact);
                    tableView.refresh();
                } catch (Exception e) {
                    Utils.alertUser(e);
                }
            }
        }
    }

    private void setDialogTextFields(Contact selectedContact, NewContactDialogController controller) {
        controller.firstNameField.setText(selectedContact.getFirstName());
        controller.lastNameField.setText(selectedContact.getLastName());
        controller.patronymicField.setText(selectedContact.getPatronymic());
        controller.cellNumberField.setText(selectedContact.getCellNumber().toString());
        controller.homeNumberField.setText(selectedContact.getHomeNumber().toString());
        controller.birthDateDatePicker.setValue(selectedContact.getBirthdate());
        controller.addressField.setText(selectedContact.getAddress());
        controller.commentTextArea.setText(selectedContact.getComment());
    }

    @FXML
    private void showNewContactDialog() {
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
                tableView.getItems().add(newContact);
            } catch (Exception e) {
                Utils.alertUser(e);
            }
        }
    }

    @FXML
    private void searchHandler() {
        String[] searchQuery = searchField.getText().trim().toLowerCase(Locale.ROOT).split(" ");
        if (searchQuery.length != 0) {
            List<Contact> res = contacts.stream().filter(contact -> {
                boolean result = true;
                for (String subQuery : searchQuery) {
                    result &= (contact.getFirstName().toLowerCase() +
                            contact.getLastName().toLowerCase() +
                            contact.getPatronymic().toLowerCase()).contains(subQuery);
                }

                return result;
            }).collect(Collectors.toList());
            tableView.setItems(FXCollections.observableArrayList(res));
        }
    }

    @FXML
    private void rowDeletionHandler() {
        tableView.getItems().remove(tableView.getSelectionModel().getSelectedItem());
        ContactData.getInstance().getContacts().remove(tableView.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void applicationClosingHandler() {
        Stage stage = (Stage) mainBorderPane.getScene().getWindow();
        try {
            String s = new File("").getAbsolutePath();
            ContactData.getInstance().storeContacts(new File(s + "/data.txt"));
        } catch (Exception e) {
            String property = "java.io.tmpdir";
            String tempDir = System.getProperty(property);
            try {
                ContactData.getInstance().storeContacts(new File(tempDir + "/data.txt"));
            } catch (IOException ioException) {
                Utils.alertUser(ioException);
            }
            Utils.alertUser(e);
        }
        System.out.println("closing");
        stage.close();
    }
}
