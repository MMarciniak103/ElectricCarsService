package app.util;

import app.MainApp;
import app.controllers.PopupController;
import javafx.animation.PauseTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Optional;

public final class  DialogUtils {

    private static final String DIALOG_PANE_CSS = "/css/dialogPane.css";

    public static void invalidUsernameDialog(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(MainApp.class.getResource(DIALOG_PANE_CSS).toExternalForm());
        alert.setHeaderText("Invalid Username");
        alert.setContentText("This username is already taken, please choose different one");
        alert.showAndWait();
    }

    public static void differentPasswordsDialog(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(MainApp.class.getResource(DIALOG_PANE_CSS).toExternalForm());
        alert.setHeaderText("Uncorrect password");
        alert.setContentText("Passwords values are different. Please oonfirm your password!");
        alert.showAndWait();
    }

    public static Optional<ButtonType> confirmationDialog(){
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        DialogPane dialogPane = confirmationDialog.getDialogPane();
        dialogPane.getStylesheets().add(MainApp.class.getResource(DIALOG_PANE_CSS).toExternalForm());
        confirmationDialog.setTitle("Exit");
        confirmationDialog.setHeaderText("Are you sure you want to exit?");
        Optional<ButtonType> result = confirmationDialog.showAndWait();
        return  result;
    }

    public static void popupWindow(String message,int duration){

        try{
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/popup.fxml"));
            Parent root = loader.load();
            PopupController popupController = loader.getController();
            popupController.setPopupText(message);

            Scene scene  = new Scene(root);
            Stage popup = new Stage();
            popup.setScene(scene);

            PauseTransition delay = new PauseTransition(Duration.seconds(duration));
            delay.setOnFinished(e->popup.hide());
            popup.show();
            delay.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
