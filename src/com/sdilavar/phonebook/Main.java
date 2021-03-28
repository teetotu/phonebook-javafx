package com.sdilavar.phonebook;

import com.sdilavar.phonebook.datamodel.ContactData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    }
}
