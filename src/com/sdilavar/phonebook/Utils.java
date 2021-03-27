package com.sdilavar.phonebook;

import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;

public class Utils {
    public static void alertUser(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(e.getMessage());
        System.out.println(e.getMessage());

        ScrollPane scroll = new ScrollPane();
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            sb.append(stackTraceElement.toString()).append("\n");
        }
        scroll.setContent(new TextArea(sb.toString()));

        alert.getDialogPane().setContent(scroll);
        alert.showAndWait();
    }
}
