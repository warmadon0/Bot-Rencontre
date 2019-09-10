package fr.warmadon.dev.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class AppAlert {

    public AppAlert(String text, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Erreur");
        alert.setHeaderText("Une erreur est survenue.");
        alert.setContentText(text);
        alert.show();
    }

    public AppAlert(String title, String text) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Une erreur est survenue.");
        alert.setContentText(title);
        Label label = new Label("L'exception est la suivante:");
        TextArea textArea = new TextArea(text);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(1.7976931348623157E308D);
        textArea.setMaxHeight(1.7976931348623157E308D);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);
        GridPane expContent = new GridPane();
        expContent.setMaxWidth(1.7976931348623157E308D);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);
        alert.getDialogPane().setExpandableContent(expContent);
        alert.showAndWait();
    }

}

