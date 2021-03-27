package com.sdilavar.phonebook;

import com.sdilavar.phonebook.datamodel.ContactData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("mainwindow.fxml"));
        primaryStage.setTitle("Phone book");
        primaryStage.setScene(new Scene(root, 960, 540));
        primaryStage.setMinWidth(880);
        primaryStage.setMinHeight(400);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() {
        try {
            ContactData.getInstance().storeContacts(new File("data.txt"));
        } catch (Exception e) {
            Utils.alertUser(e);
        }
        System.out.println("closing");
    }
}
