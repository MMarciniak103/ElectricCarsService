package app.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public final class  DialogUtils {

    public static void invalidUsernameDialog(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Invalid Username");
        alert.setContentText("This username is already taken, please choose different one");
        alert.showAndWait();
    }

    public static void differentPasswordsDialog(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Uncorrect password");
        alert.setContentText("Passwords values are different. Please oonfirm your password!");
        alert.showAndWait();
    }

    public static Optional<ButtonType> confirmationDialog(){
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Exit");
        confirmationDialog.setHeaderText("Are you sure you want to exit?");
        Optional<ButtonType> result = confirmationDialog.showAndWait();
        return  result;
    }
}
